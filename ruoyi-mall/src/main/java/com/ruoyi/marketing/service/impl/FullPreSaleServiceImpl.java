package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.PreSale;
import com.ruoyi.marketing.mapper.PreSaleMapper;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全款预售服务接口
 */
@Service("fullPreSaleService")
public class FullPreSaleServiceImpl extends MarketingTemplate {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(FullPreSaleServiceImpl.class);

    /**
     * 注入定金预售数据库接口
     */
    @Autowired
    private PreSaleMapper preSaleMapper;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    /**
     * 新增全款预售信息
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */
    @Override
    public int addMarketingDetail(Marketing marketing) {

        logger.debug("addMarketingDetail and marketing:{}", marketing);

        if (!validateParams(marketing)) {
            logger.error("addMarketingDetail fail due to params is error..");
            return 0;
        }

        // 新增预售促销详情表
        this.addPreSaleList(marketing.getPreSaleList());

        return 1;

    }


    /**
     * 更新全款预售信息
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */
    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {

        logger.debug("updateMarketingDetail and marketing:{}", marketing);

        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to params is error..");
            return 0;
        }

        // 获取原有的需要修改预售列表
        List<PreSale> updatePreSaleList = marketing.getPreSaleList().stream().filter(PreSale::hasId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(updatePreSaleList)) {

            // 遍历修改预售详情表

        }

        // 获取原有的预售列表
        Map<String, Object> map = new HashMap<>();
        map.put("marketingId", marketing.getId());
        List<PreSale> originalPreSaleList = preSaleMapper.queryPreSale(map);

        // 获取原有的需要修改预售id列表
        List<Long> needUpdatePreSaleIdList = updatePreSaleList.stream().map(PreSale::getId).collect(Collectors.toList());

        // 获取需要删除的预售id列表（原有的预售id列表和原有的需要修改预售id列表差集）
        List<Long> needDeletePreSaleIdList = originalPreSaleList.stream().map(PreSale::getId).collect(Collectors.toList());
        needDeletePreSaleIdList.removeAll(needUpdatePreSaleIdList);

        // 删除预售促销详情
        if (!CollectionUtils.isEmpty(needDeletePreSaleIdList)) {
            Map<String, Object> params = new HashMap<>();
            params.put("marketingId", marketing.getId());
            params.put("preSaleIdList", needDeletePreSaleIdList);
            preSaleMapper.deletePreSaleByIds(params);
        }

        // 获取新增加的预售列表
        List<PreSale> addPreSaleList = marketing.getPreSaleList().stream().filter(PreSale::notHasId).collect(Collectors.toList());

        // 新增预售详情表
        if (!CollectionUtils.isEmpty(addPreSaleList)) {
            addPreSaleList(addPreSaleList);
        }

        return 1;
    }

    @Override
    public void setMarketingDetail(Marketing marketing) {


        if (Objects.isNull(marketing) || !marketing.isFullPreSaleMarkting()) {
            logger.error("setMarketingDetail fail due to params is error....");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingId", marketing.getId());
        if (Objects.nonNull(marketing.getSku())) {
            params.put("skuId", marketing.getSku().getId());
        }

        List<PreSale> preSales = preSaleMapper.queryPreSale(params);

        if (!CollectionUtils.isEmpty(preSales)) {
            marketing.setPreSale(preSales.get(0));
            marketing.setPreSaleList(preSales.stream().peek(preSale -> preSale.setSku(skuService.querySkuByIdWithSpecs(preSale.getSkuId(), marketing.getStoreId()))).collect(Collectors.toList()));
        }
    }

    /**
     * 新增预售促销详情表
     *
     * @param preSaleList 预售促销列表
     */
    private void addPreSaleList(List<PreSale> preSaleList) {
        logger.debug("addPreSaleList and preSaleList :{}", preSaleList);

        if (CollectionUtils.isEmpty(preSaleList)) {
            logger.info("addPreSaleList fail due to preSaleList is empty");
            return;
        }

        // 遍历新增预售详情表
        preSaleList.forEach(preSale -> {

            // 新增预售详情表
            preSaleMapper.addPreSale(preSale.convertDepositPre().addType(CommonConstant.FULL_PRESALE_TYPE));

        });

    }

    /**
     * 判断当前促销是否正确
     *
     * @param marketing 促销
     * @return 正确返回true  不正确返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getPreSaleList()) && marketing.isFullPreSaleMarkting();
    }

}
