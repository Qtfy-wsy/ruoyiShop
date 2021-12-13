package com.ruoyi.marketing.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.goods.domain.PmsGoods;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsGoodsAttributeValueService;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.TryMarketing;
import com.ruoyi.marketing.mapper.TryMapper;
import com.ruoyi.marketing.service.TryMarketingService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 18/1/16.
 * 试用促销接口实现
 */
@Service("tryService")
public class TryServiceImpl extends MarketingTemplate implements TryMarketingService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(TryServiceImpl.class);

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入商品服务
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 注入商品属性值服务
     */
    @Autowired
    private IPmsGoodsAttributeValueService spuAttributeValueService;

    /**
     * 注入试用促销数据库接口
     */
    @Autowired
    private TryMapper tryMapper;

    @Transactional
    @Override
    public int addMarketingDetail(Marketing marketing) {

        logger.debug("addMarketingDetail and marketing:{}", marketing);

        // 校验参数
        if (!validateParams(marketing)) {
            logger.error("addTryMarketing fail deu to validate params fail....");
            return 0;
        }

        this.addTryMarketingList(marketing.getTryMarketingList());

        return 1;
    }

    @Transactional
    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {
        logger.debug("updateMarketingDetail and marketing:{}", marketing);

        // 校验参数是否合法
        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to marketing is error...");
            return 0;
        }

        //只能修改还未开始的试用促销
        if (!LocalDateTime.now().isBefore(oldMarketing.getStartTime())) {
            logger.error("updateMarketingDetail fail due to marketing is alerdy start....");
            throw new ServiceException("updateMarketingDetail fail due to marketing is alerdy start....");
        }

        // 获取原有的需要修改试用列表
        List<TryMarketing> updateTryMarketingList = marketing.getTryMarketingList().stream().filter(TryMarketing::hasId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(updateTryMarketingList)) {

            // 遍历修改试用详情表
            updateTryMarketingList.forEach(tryMarketing -> {

                PmsGoods spu = null;
                PmsSku sku = skuService.setSkuDetail(skuService.querySkuById(tryMarketing.getSkuId()), PmsSkuItem.SPEC, PmsSkuItem.IMAGE);
                if (!ObjectUtils.isEmpty(sku)) {
                    spu = spuService.querySimpleSpu(sku.getSpuId(), CommonConstant.QUERY_WITH_NO_STORE);
                    if (!ObjectUtils.isEmpty(spu)) {
                        // 查询商品属性值
                        spu.setSpuAttributeValues(spuAttributeValueService.queryBySpuId(spu.getId()));
                    }
                }

                tryMapper.updateTryMarketing(tryMarketing.buildSkuInfo(sku, spu));
            });

        }

        // 获取原有的试用列表
        Map<String, Object> map = new HashMap<>();
        map.put("marketingId", marketing.getId());
        List<TryMarketing> originalTryMarketingList = tryMapper.queryTryMarketing(map);

        // 获取原有的需要修改试用id列表
        List<Long> needUpdateTryMarketingIdList = updateTryMarketingList.stream().map(TryMarketing::getId).collect(Collectors.toList());

        // 获取需要删除的试用id列表（原有的试用id列表和原有的需要修改试用id列表差集）
        List<Long> needDeleteTryMarketingIdList = originalTryMarketingList.stream().map(TryMarketing::getId).collect(Collectors.toList());
        needDeleteTryMarketingIdList.removeAll(needUpdateTryMarketingIdList);

        // 删除试用促销详情
        if (!CollectionUtils.isEmpty(needDeleteTryMarketingIdList)) {
            Map<String, Object> params = new HashMap<>();
            params.put("marketingId", marketing.getId());
            params.put("tryMarketingIdList", needDeleteTryMarketingIdList);
            tryMapper.deleteTryMarketingByIds(params);
        }

        // 获取新增加的试用列表
        List<TryMarketing> addTryMarketingList = marketing.getTryMarketingList().stream().filter(TryMarketing::notHasId).collect(Collectors.toList());

        // 新增试用详情表
        if (!CollectionUtils.isEmpty(addTryMarketingList)) {
            addTryMarketingList(addTryMarketingList);
        }

        return 1;
    }


    @Override
    public void setMarketingDetail(Marketing marketing) {


        if (Objects.isNull(marketing) || !marketing.isTryMarketing()) {
            logger.error("setMarketingDetail fail ...due to marketing is error....");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("marketingId", marketing.getId());
        if (Objects.nonNull(marketing.getSku())) {
            params.put("skuId", marketing.getSku().getId());
        }

        List<TryMarketing> tryMarketings = tryMapper.queryTryMarketing(params);

        if (!CollectionUtils.isEmpty(tryMarketings)) {
            marketing.setTryMarkting(tryMarketings.get(0));
            marketing.setTryMarketingList(tryMarketings.stream().peek(tryMarketing -> tryMarketing.convertJsonToObject("")).collect(Collectors.toList()));
        }
    }

    @Override
    public int updateAlreadyApplyNum(TryMarketing tryMarketing) {
        // 校验参数
        if (Objects.isNull(tryMarketing)) {
            logger.error("updateAlreadyApplyNum fail due to validate params fail....");
            return 0;
        }
        return tryMapper.updateAlreadyApplyNum(tryMarketing.getId());
    }

    @Override
    public int updateAuditStatus(TryMarketing tryMarketing) {
        // 校验参数
        if (Objects.isNull(tryMarketing)) {
            logger.error("updateAlreadyApplyNum fail due to validate params fail....");
            return 0;
        }
        return tryMapper.updateAuditStatus(tryMarketing.getId());
    }

    @Override
    public PageHelper<TryMarketing> queryTryMarketingList(PageHelper<TryMarketing> pageHelper, String name, String skuNo, long storeId) {
        logger.debug("queryTryMarketingList and pageHelper :{} \r\n name :{} \r\n skuNo :{} \r\n storeId :{}", pageHelper, name, skuNo, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("skuNo", skuNo);
        params.put("storeId", storeId);
        return pageHelper.setListDates(tryMapper.queryTryMarketingList(pageHelper.getQueryParams(params, tryMapper.queryTryMarketingListCount(params)))
                .stream().peek(tryMarketing -> setOtherInfo(tryMarketing, storeId)).collect(Collectors.toList()));
    }

    @Override
    public TryMarketing queryTryMarketingById(long tryId, long storeId) {
        logger.debug("queryTryMarketingById and tryId :{] \r\n storeId :{}", tryId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", tryId);
        params.put("storeId", storeId);
        return setOtherInfo(tryMapper.queryTryMarketingById(params), storeId);
    }

    @Override
    public List<TryMarketing> queryTimeOutAndUnAuditTryMarketingList() {
        logger.debug("queryTimeOutAndUnAuditTryMarketingList.......");
        return tryMapper.queryTimeOutAndUnAuditTryMarketingList();
    }

    /**
     * 设置其他信息
     *
     * @param tryMarketing 试用实体
     */
    private TryMarketing setOtherInfo(TryMarketing tryMarketing, long storeId) {
        logger.debug("setOtherInfo and tryMarketing :{} \r\n storeId :{}", tryMarketing, storeId);


        if (Objects.nonNull(tryMarketing)) {

            // 设置单品信息
            tryMarketing.convertJsonToObject("");
        }

        return tryMarketing;
    }


    /**
     * 新增试用促销详情表
     *
     * @param tryMarketingList 试用促销列表
     */
    private void addTryMarketingList(List<TryMarketing> tryMarketingList) {
        logger.debug("addTryMarketingList and tryMarketingList :{}", tryMarketingList);

        if (CollectionUtils.isEmpty(tryMarketingList)) {
            logger.info("addTryMarketingList fail due to tryMarketingList is empty");
            return;
        }

        // 遍历新增试用详情表
        tryMarketingList.forEach(tryMarketing -> {

            PmsGoods spu = null;
            PmsSku sku = skuService.setSkuDetail(skuService.querySkuById(tryMarketing.getSkuId()), PmsSkuItem.SPEC, PmsSkuItem.IMAGE);
            if (!ObjectUtils.isEmpty(sku)) {
                spu = spuService.querySimpleSpu(sku.getSpuId(), CommonConstant.QUERY_WITH_NO_STORE);
                if (!ObjectUtils.isEmpty(spu)) {
                    // 查询商品属性值
                    spu.setSpuAttributeValues(spuAttributeValueService.queryBySpuId(spu.getId()));
                }
            }

            // 新增试用详情表
            tryMapper.addTryMarketing(tryMarketing.buildSkuInfo(sku, spu));

        });

    }

    /**
     * 校验当前促销是否正确
     *
     * @param marketing 促销信息
     * @return 正确返回 true 失败返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getTryMarketingList()) && marketing.isTryMarketing();
    }

}
