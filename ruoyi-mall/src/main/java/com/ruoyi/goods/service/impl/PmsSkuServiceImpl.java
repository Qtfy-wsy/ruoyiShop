package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.domain.PmsSkuSpecValue;
import com.ruoyi.goods.mapper.PmsSkuMapper;
import com.ruoyi.goods.service.*;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 单品Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsSkuServiceImpl implements IPmsSkuService {
    private static final Logger logger = LoggerFactory.getLogger(PmsSkuServiceImpl.class);

    @Autowired
    private PmsSkuMapper pmsSkuMapper;

    /**
     * 注入单品图片服务接口
     */
    @Autowired
    private IPmsSkuImageService skuImageService;

    /**
     * 注入单品会员价服务接口
     */
    @Autowired
    private IPmsSkuMemberPriceService skuMemberPriceServicce;

    /**
     * 注入数据库接口
     */
    @Autowired
    private PmsSkuMapper skuMapper;

    /**
     * 注入单品规格值服务接口
     */
    @Autowired
    private IPmsSkuSpecValueService skuSpecValueService;

    /**
     * 注入单品批发规则服务
     */
    @Autowired
    private IPmsSkuBatchService skuBatchService;

    @Override
    public void addSkus(List<PmsSku> skus) {

        logger.debug("addSku and skus:{}", skus);

        if (CollectionUtils.isEmpty(skus)) {
            logger.error("addSku fail due to skus is null..");
            return;
        }

        // 设置单品的主键id
        IntStream.range(0, skus.size()).forEach(index -> skus.get(index).setCustomId(index));

        // 并行新增单品
        skus.parallelStream().forEach(this::addSku);
    }


    @Override
    public void deleteBySpuId(long spuId, long storeId) {
        logger.debug("deleteBySpuId and spuId:{} \rn storeId:{}", spuId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("storeId", storeId);

        if (skuMapper.deleteBySpuId(params) == 0) {
            logger.error("deleteBySpuId fail...");
            return;
        }

        // 删除单品图片
        skuImageService.deleteBySpuId(spuId);

        // 删除单品会员价
        skuMemberPriceServicce.deleteBySpuId(spuId);

        // 删除单品规格值
        skuSpecValueService.deleteBySpuId(spuId);

        // 删除单品批发规则
        skuBatchService.deleteSkuBatchBySpuId(spuId);

    }

    /**
     * 查询单品
     *
     * @param id 单品ID
     * @return 单品
     */
    @Override
    public PmsSku selectPmsSkuById(String id) {
        return pmsSkuMapper.selectPmsSkuById(id);
    }

    /**
     * 查询单品列表
     *
     * @param pmsSku 单品
     * @return 单品
     */
    @Override
    public List<PmsSku> selectPmsSkuList(PmsSku pmsSku) {
        return pmsSkuMapper.selectPmsSkuList(pmsSku);
    }

    /**
     * 新增单品
     *
     * @param pmsSku 单品
     * @return 结果
     */
    @Override
    public int insertPmsSku(PmsSku pmsSku) {
        pmsSku.setCreateTime(DateUtils.getNowDate());
        return pmsSkuMapper.insertPmsSku(pmsSku);
    }

    /**
     * 修改单品
     *
     * @param pmsSku 单品
     * @return 结果
     */
    @Override
    public int updatePmsSku(PmsSku pmsSku) {
        return pmsSkuMapper.updatePmsSku(pmsSku);
    }

    /**
     * 批量删除单品
     *
     * @param ids 需要删除的单品ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuByIds(String[] ids) {
        return pmsSkuMapper.deletePmsSkuByIds(ids);
    }

    /**
     * 删除单品信息
     *
     * @param id 单品ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuById(String id) {
        return pmsSkuMapper.deletePmsSkuById(id);
    }

    @Override
    public List<PmsSku> querySkuBySpuId(long spuId, long storeId, PmsSkuItem... skuItems) {
        logger.debug("querySkuBySpuId and spuId:{} \r\n storeId:{} and skuItems:{}", spuId, storeId, skuItems);

        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("storeId", storeId);

        // 商品信息
        List<PmsSku> skus = pmsSkuMapper.querySkuBySpuId(params);

        if (CollectionUtils.isEmpty(skus)) {
            logger.error("querySkuBySpuId no result....");
            return skus;
        }

        //  根据条件设置单品详情
        skus.stream().forEach(sku -> this.setSkuDetail(sku, skuItems));

        return skus;
    }

    @Override
    public int updateShelvesStatus(List<Long> spuIds, String status, long storeId) {
        logger.debug("updateShelvesStatus and spuIds:{} \r\n status:{} \r\n storeId:{}", spuIds, status, storeId);

        if (CollectionUtils.isEmpty(spuIds)) {
            logger.error("updateShelvesStatus fail due to spuIds is null...");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("spuIds", spuIds);
        params.put("status", status);
        params.put("storeId", storeId);
        params.put("upTime", LocalDateTime.now());
        return skuMapper.updateShelvesStatus(params);
    }

    @Transactional
    @Override
    public void updateSkus(List<PmsSku> skus, long spuId, long storeId, BigDecimal commissionRate) {

        logger.debug("updateSkus and skus:{}\r\n spuId:{} \r\n storeId:{}", skus, spuId, storeId);

        // 根据商品id删除单品信息(物理删除)
        if (deleteBySpuIdPhysical(spuId, storeId) == 0) {
            logger.error("updateSkus fail...");
            return;
        }

        if (CollectionUtils.isEmpty(skus)) {
            logger.warn("not need to updateSkus");
            return;
        }

        // 设置单品的主键id
        IntStream.range(0, skus.size()).forEach(index -> skus.get(index).setCustomId(index));

        // 设置单品的佣金比例
        IntStream.range(0, skus.size()).forEach(index -> skus.get(index).setCommissionRate(commissionRate));

        // 新增单品
        skus.stream().forEach(this::addSku);
    }

    @Override
    public PageHelper<PmsSku> querySkusWithSpecs(PageHelper<PmsSku> pageHelper, long storeId, String name, String id, PmsSkuItem... skuItems) {
        logger.debug("querySkusWithSpecs and storeId:{} \r\n pageHelper:{} \r\n name:{} \r\n id:{}", storeId, pageHelper, name, id);

        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("name", name);
        params.put("id", id);

        List<PmsSku> skus = skuMapper.querySkus(pageHelper.getQueryParams(params, skuMapper.querySkuCount(params)));

        if (CollectionUtils.isEmpty(skus)) {
            return pageHelper.setListDates(skus);
        }

        return pageHelper.setListDates(skus.parallelStream().map(sku -> {
            // 设置单品规格值信息
            sku.setSkuSpecValues(skuSpecValueService.queryBySkuId(sku.getId()));
            return setSkuDetail(sku, skuItems);
        }).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<PmsSku> queryCommissionSkuList(PageHelper<PmsSku> pageHelper) {
        logger.debug("queryCommissionSkuList and pageHelper:{}");
        Map<String, Object> params = new HashMap<>();
        params.put("commissionRate", 0);
        return pageHelper.setListDates(skuMapper.querySkus(pageHelper.getQueryParams(params, skuMapper.querySkuCount(params)))
                .stream().peek(sku -> setSkuDetail(sku, PmsSkuItem.BATCH)).collect(Collectors.toList()));
    }

    @Override
    public PmsSku querySkuByIdWithSpecs(String skuId, long storeId) {
        logger.debug("querySkuByIdWithSpecs and skuId:{} storeId:{}", skuId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", skuId);
        params.put("storeId", storeId);

        PmsSku sku = skuMapper.selectPmsSkuById(skuId);

        if (Objects.isNull(sku)) {
            logger.error("querySkuByIdWithSpecs fail ....");
            return sku;
        }

        // 设置单品规格值信息
        sku.setSkuSpecValues(skuSpecValueService.queryBySkuId(sku.getId()));

        return sku;
    }


    @Override
    public int auditPass(long spuId) {
        logger.debug("auditPass and spuId:{}", spuId);
        skuMapper.auditPass(spuId);
        return 1;
    }

    @Override
    public int auditRefuse(String reason, long spuId) {

        logger.debug("auditRefuse and spuId:{} \r\n reason:{}", spuId, reason);

        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("reason", reason);
        skuMapper.auditRefuse(params);

        return 1;
    }

    /**
     * 根据店铺id查询前五条数据
     *
     * @param storeId 店铺id
     * @return 单品集合
     */
    @Override
    public List<PmsSku> queryFiveDataForAttentionStore(long storeId) {
        return skuMapper.queryFiveDataForAttentionStore(storeId).stream().peek(sku -> setSkuDetail(sku, PmsSkuItem.BATCH)).collect(Collectors.toList());
    }

    @Override
    public PmsSku querySkuById(String skuId) {
        logger.debug("querySkuById and skuId:{}", skuId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", skuId);
        params.put("storeId", CommonConstant.QUERY_WITH_NO_STORE);
        return skuMapper.selectPmsSkuById(skuId);
    }

    @Override
    public PmsSku setSkuDetail(PmsSku sku, PmsSkuItem... skuItems) {

       // logger.debug("setSkuDetail and sku:{} \r\n skuItems:{}", sku, skuItems);
        if (Objects.isNull(sku) || ArrayUtils.isEmpty(skuItems)) {
            return sku;
        }

        // 设置规格值
        if (ArrayUtils.contains(skuItems, PmsSkuItem.SPEC)) {
            sku.setSkuSpecValues(skuSpecValueService.queryBySkuId(sku.getId()));
        }

        // 设置图片
        if (ArrayUtils.contains(skuItems, PmsSkuItem.IMAGE)) {
            sku.setSkuImages(skuImageService.queryBySkuId(sku.getId()));
        }

        // 设置会员价格
        if (ArrayUtils.contains(skuItems, PmsSkuItem.MEMBER_PRICE)) {
            sku.setSkuMemberPrices(skuMemberPriceServicce.queryBySkuId(sku.getId()));
        }

        // 设置批发规则
        if (ArrayUtils.contains(skuItems, PmsSkuItem.BATCH) && sku.isBatchSku()) {
            sku.setSkuBatchList(skuBatchService.querySkuBatchBySkuId(sku.getId()));
        }

        return sku;
    }

    @Override
    public List<PmsSku> queryAllSkuSpecs(long spuId) {
        logger.debug("queryAllSkuSpecs and spuId:{}", spuId);
        return convertToSku(skuSpecValueService.queryBySpuId(spuId));
    }

    @Override
    public List<PmsSku> querySkuByIds(String[] skuIds) {
        logger.debug("qucerySkuByIds and skuIds:{}", Arrays.toString(skuIds));
        if (ArrayUtils.isEmpty(skuIds)) {
            return Collections.emptyList();
        }
        return skuMapper.querySkuByIds(Arrays.asList(skuIds));
    }

    @Override
    public int reduceSkuStock(String skuId, int stock) {
        logger.debug("reduceSkuStock and skuId:{} \r\n stock:{}", skuId, stock);
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("stock", stock);
        return skuMapper.reduceSkuStock(params);
    }

    @Override
    public int increaseSkuStock(String skuId, int stock) {
        logger.debug("increaseSkuStock and skuId:{} \r\n stock:{}", skuId, stock);
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("stock", stock);
        return skuMapper.increaseSkuStock(params);
    }

    @Override
    public int lastUpSkusNum(long storeId) {
        logger.debug("lastUpSkusNum and storeId:{}", storeId);
        return skuMapper.lastUpSkusNum(storeId);
    }

    @Override
    public int marketSkusNum(long storeId) {
        logger.debug("marketSkusNum and storeId:{}", storeId);
        return skuMapper.marketSkusNum(storeId);
    }

    @Override
    public PageHelper<PmsSku> queryLastUpSkus(PageHelper<PmsSku> pageHelper, long storeId) {
        logger.debug("queryLastUpSkus and pageHelper:{} \r\n storeId:{}", pageHelper, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        return pageHelper.setListDates(skuMapper.queryLastUpSkus(pageHelper.getQueryParams(params, skuMapper.lastUpSkusNum(storeId))));
    }

    @Override
    public PageHelper<PmsSku> queryMarketSkus(PageHelper<PmsSku> pageHelper, long storeId) {
        logger.debug("queryMarketSkus and pageHelper:{} \r\n storeId:{}", pageHelper, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);

        return pageHelper.setListDates(skuMapper.queryMarketSkus(pageHelper.getQueryParams(params, skuMapper.marketSkusNum(storeId))));
    }

    @Override
    public List<PmsSku> querySkusBySkuIdsSort(String[] skuIds, PmsSkuItem... skuItems) {
        logger.debug("querySkusBySkuIdsSort and skuIds:{}", Arrays.toString(skuIds));
        Map<String, Object> params = new HashMap();
        params.put("storeId", CommonConstant.QUERY_WITH_NO_STORE);
        if (ArrayUtils.isEmpty(skuIds)) {
            logger.error("querySkusBySkuIdsSort fail :skuIds is empty");
            return Collections.emptyList();
        }
        List<PmsSku> finalList = new ArrayList<>();
        Arrays.asList(skuIds).forEach(s -> {
            params.put("id", s);
            finalList.add(setSkuDetail(skuMapper.selectPmsSkuById(s), skuItems));
        });
        return finalList;
    }

    @Override
    public int updateCommission(long spuId, BigDecimal commissionRate, BigDecimal sCommissionRate, long storeId) {
        logger.debug("updateCommission and spuId :{} \r\n commissionRate :{} \r\n sCommissionRate :{} \r\n storeId", spuId, commissionRate, sCommissionRate, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("commissionRate", commissionRate);
        params.put("sCommissionRate", sCommissionRate);
        params.put("storeId", storeId);
        return skuMapper.updateCommission(params);
    }

    @Override
    public int updateShelvesStatusByStoreIds(String status, List<Long> storeIds) {
        logger.debug("updateShelvesStatusByStoreIds and status:{} \r\n storeIds:{}", status, storeIds);
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("storeIds", storeIds);
        return skuMapper.updateShelvesStatusByStoreIds(params);
    }

    @Override
    public int querySkuCountByStoreId(long storeId) {
        logger.debug("querySkuCount and storeId:{}", storeId);
        return skuMapper.querySkuCountByStoreId(storeId);
    }

    @Override
    public int updateSkuToAudit(List<Long> spuIds, long storeId) {
        logger.debug("updateSkuToAudit and spuIds:{} \r\n storeId:{}");
        Map<String, Object> params = new HashMap<>();
        params.put("spuIds", spuIds);
        params.put("status", "2");
        params.put("storeId", storeId);
        return skuMapper.updateSkuToAudit(params);
    }

    /**
     * 修改单品物流模版id
     *
     * @param logisticsTemplateId        物流模版id
     * @param defaultLogisticsTemplateId 默认物流模版id
     * @param storeId                    店铺id
     * @return 成功>0 否则失败
     */
    @Override
    public int updateSkuLogisticsTemplateId(long logisticsTemplateId, long defaultLogisticsTemplateId, long storeId) {
        logger.debug("updateSpuLogisticsTemplateId and logisticsTemplateId:{} \r\n defaultLogisticsTemplateId :{} \r\n storeId:{}", logisticsTemplateId, defaultLogisticsTemplateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("logisticsTemplateId", logisticsTemplateId);
        params.put("defaultLogisticsTemplateId", defaultLogisticsTemplateId);
        params.put("storeId", storeId);
        return skuMapper.updateSkuLogisticsTemplateId(params);
    }

    /**
     * 将单品规格值转化成单品
     *
     * @param skuSpecValues 单品规格值
     * @return 返回单品
     */
    private List<PmsSku> convertToSku(List<PmsSkuSpecValue> skuSpecValues) {

        List<PmsSku> skus = new ArrayList<>();
        if (CollectionUtils.isEmpty(skuSpecValues)) {
            return skus;
        }

        skuSpecValues.stream().forEach(skuSpecValue -> {
            if (skus.stream().filter(sku -> sku.getId().equals(skuSpecValue.getSkuId())).count() == 0) {
                PmsSku sku1 = new PmsSku();
                sku1.setId(skuSpecValue.getSkuId());
                sku1.setSkuSpecValues(new ArrayList<>());
                skus.add(sku1);
            }
            skus.stream().filter(sku -> sku.getId().equals(skuSpecValue.getSkuId())).findFirst().ifPresent(sku1 -> sku1.getSkuSpecValues().add(skuSpecValue));
        });

        return skus;
    }


    /**
     * 设置单品的详细信息
     *
     * @param sku 单品
     * @return 返回单品信息包含(单品图片, 单品会员价格, 单品规格值)
     */
    private PmsSku setSkuDetail(PmsSku sku) {
        // 设置单品图片
        sku.setSkuImages(skuImageService.queryBySkuId(sku.getId()));

        // 设置单品会员价
        sku.setSkuMemberPrices(skuMemberPriceServicce.queryBySkuId(sku.getId()));

        // 设置单品规格值信息
        sku.setSkuSpecValues(skuSpecValueService.queryBySkuId(sku.getId()));

        return sku;
    }


    /**
     * 新增单品
     *
     * @param sku 单品信息
     */
    private void addSku(PmsSku sku) {

        logger.debug("addSku and sku :{}", sku);

        if (Objects.isNull(sku)) {
            logger.error("addSku fail due to sku is null...");
            return;
        }

        // 设置默认图片
        sku.setDefaultPic();

        // 新增单品
        skuMapper.insertPmsSku(sku);

        // 设置和单品关联信息的单品id和商品id
        sku.setSkuLinkedSkuIdAndSpuId();

        //处理单品批发规则
        sku.dealSkuBatchList();

        // 新增单品图片
        skuImageService.addSkuImages(sku.getSkuImages());

        // 新增单品会员价格
        skuMemberPriceServicce.addSkuMemberPrices(sku.getSkuMemberPrices());

        // 新增单品规格值
        skuSpecValueService.addSkuSpecValues(sku.getSkuSpecValues());

        // 新增单品批发规则
        skuBatchService.addSkuBatch(sku.getSkuBatchList());
    }

    /**
     * 物理删除单品信息
     *
     * @param spuId 商品id
     */
    private int deleteBySpuIdPhysical(long spuId, long storeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("storeId", storeId);

        // 删除单品信息(物理)
        if (skuMapper.deleteBySpuIdPhysical(params) == 0) {
            return 0;
        }


        // 删除单品图片(物理)
        skuImageService.deleteBySpuIdPhysical(spuId);

        // 删除单品会员价(物理)
        skuMemberPriceServicce.deleteBySpuIdPhysical(spuId);

        // 删除单品规格值(物理)
        skuSpecValueService.deleteBySpuIdPhysical(spuId);

        // 删除单品批发规则(物理)
        skuBatchService.deleteSkuBatchBySpuIdPhysical(spuId);

        return 1;
    }

}
