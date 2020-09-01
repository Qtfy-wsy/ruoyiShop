package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.*;
import com.ruoyi.goods.mapper.PmsGoodsMapper;
import com.ruoyi.goods.service.*;
import com.ruoyi.goods.vo.SpuSearchCondition;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.store.service.ITStoreSkuService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 商品Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsGoodsServiceImpl implements IPmsGoodsService {

    private static final Logger logger = LoggerFactory.getLogger(PmsGoodsServiceImpl.class);

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsServiceSupportService spuServicceSupportService;

    /**
     * 注入商品规格值服务接口
     */
    @Autowired
    private IPmsGoodsSpecValueService spuSpecValueService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入商品属性值服务接口
     */
    @Autowired
    private IPmsGoodsAttributeValueService spuAttributeValueService;

    /**
     * 注入商品图片
     */
    @Autowired
    private IPmsGoodsImageService spuImageService;


    /**
     * 注入品牌服务接口
     */
    @Autowired
    private IPmsBrandService brandService;
    /**
     * 注入商品数据库接口
     */
    @Autowired
    private PmsGoodsMapper spuMapper;
    /**
     * 注入分类服务接口
     */
    @Autowired
    private IPmsCategoryService categoryService;


    /**
     * 注入系统基本设置
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;

    /**
     * 注入门店单品关联服务
     */
    @Autowired
    private ITStoreSkuService storeSkuService;

    @Autowired
    private PmsGoodsMapper pmsGoodsMapper;

    /**
     * 查询商品
     *
     * @param id 商品ID
     * @return 商品
     */
    @Override
    public PmsGoods selectPmsGoodsById(Long id) {
        return pmsGoodsMapper.selectPmsGoodsById(id);
    }

    /**
     * 查询商品列表
     *
     * @param pmsGoods 商品
     * @return 商品
     */
    @Override
    public List<PmsGoods> selectPmsGoodsList(PmsGoods pmsGoods) {
        return pmsGoodsMapper.selectPmsGoodsList(pmsGoods);
    }

    @Override
    public List<PmsGoods> querySpus(PmsGoods pmsGoods) {
        return setVisitUrl(setBrandAndCategorys(pmsGoodsMapper.selectPmsGoodsList(pmsGoods)));
    }

    @Override
    public PageHelper<PmsGoods> querySpus(PageHelper<PmsGoods> pageHelper, SpuSearchCondition spuSearchCondition) {

        logger.debug("querySpus and pageHelper:{} \r\n  spuSearchCondition:{}", pageHelper, spuSearchCondition);

        // 搜索参数
        Map<String, Object> params = spuSearchCondition.getSearchMap();

        return pageHelper.setListDates(setVisitUrl(setBrandAndCategorys(spuMapper.querySpus(pageHelper.getQueryParams(params, spuMapper.querySpuCount(params))))));
    }
    @Override
    public PageHelper<PmsGoods> queryAllStoreSpus(PageHelper<PmsGoods> pageHelper, SpuSearchCondition spuSearchCondition) {

        logger.debug("queryAllStoreSpus and pageHelper:{} \r\n spuSearchCondition:{} ", pageHelper, spuSearchCondition);

        // 搜索参数
        Map<String, Object> params = spuSearchCondition.getSearchMap();
        return pageHelper.setListDates(setBrandAndCategorys(spuMapper.queryAllStoreSpus(pageHelper.getQueryParams(params, spuMapper.queryAllStoreSpusCount(params)))));
    }

    @Override
    public PageHelper<PmsGoods> querySimpleSpus(PageHelper<PmsGoods> pageHelper, SpuSearchCondition spuSearchCondition) {
        logger.debug("querySimpleSpus and and pageHelper:{} \r\n  spuSearchCondition:{}", pageHelper, spuSearchCondition);
        // 搜索参数
        Map<String, Object> params = spuSearchCondition.getSearchMap();
        return pageHelper.setListDates(spuMapper.querySpus(pageHelper.getQueryParams(params, spuMapper.querySpuCount(params))));
    }
    /**
     * 新增商品
     *
     * @param spu 商品
     * @return 结果
     */
    @Override
    public int insertPmsGoods(PmsGoods spu) {
        logger.debug("addSpu and goods :{}", spu);

        if (Objects.isNull(spu)) {
            logger.error("addSpu fail due to goods is null...");
            return 0;
        }

        if (spu.getSkus().stream().anyMatch(PmsSku::hasBatchPriceAndMemberPrice)) {
            logger.error("addSpu fail due to exist sku has member price and batch both");
            return -1;
        }

        // 设置单品的店铺和审核状态
        spu.setSkuStoreIdAndAuditAndShelvesStatus(baseInfoSetService.isSkuNeedAudit());

        // 设置单品是否虚拟
        spu.setSkuIsVirtual();

        // 设置单品物流模版id
        spu.setSkuLogisticsTemplateId();

        // 设置商品的分类信息
        setSpuCategory(spu);

        // 新增商品
        spu.setCreateTime(DateUtils.getNowDate());
        pmsGoodsMapper.insertPmsGoods(spu);

        // 设置商品关联信息的商品id
        spu.setSpuLinkedSpuId();

        // 新增商品服务支持
        spuServicceSupportService.addSpuServicceSupport(spu.getSpuServiceSupports());

        // 新增商品规格值信息
        spuSpecValueService.addSpuSpecValues(spu.getSpuSpecValues());

        // 新增商品图片
        spuImageService.addSpuImages(spu.getSpuImages());

        // 新增商品属性值
        spuAttributeValueService.addSpuAttributeValues(spu.getSpuAttributeValues());

        // 新增单品
        skuService.addSkus(spu.getSkus().stream().peek(sku -> sku.fixSkuNameForSave(spu.getName())).collect(Collectors.toList()));


        return 1;
    }

    /**
     * 修改商品
     *
     * @param spu 商品
     * @return 结果
     */
    @Override
    public int updatePmsGoods(PmsGoods spu) {
        logger.debug("updateSpu  and spu:{}", spu);

        if (Objects.isNull(spu)) {
            logger.error("updateSpu fail due to spu is null....");
            return 0;
        }

        // 设置商品的默认图片
        spu.setDefaultPic();

        // 如果不是平台的商品并且审核开关打开 则需要判断是否修改了商品如果商品修改了 则商品下的所有单品都需要审核
        if (CommonConstant.ADMIN_STOREID != spu.getStoreId() && baseInfoSetService.isSkuNeedAudit()) {
            setSkuAuditStatus(spu);
        }

        // 更新商品信息
        if (spuMapper.updateSpu(spu) == 0) {
            logger.error("updateSpu fail....");
            return 0;
        }

        // 设置店铺id
        spu.setSkuStoreIdAndShelvesStatus();

        // 设置单品物流模版id
        spu.setSkuLogisticsTemplateId();

        // 设置商品相关的商品id
        spu.setSpuLinkedSpuId();

        // 更新商品图片
        spuImageService.updateSpuImages(spu.getSpuImages(), spu.getId());

        // 更新商品属性值
        spuAttributeValueService.updateSpuAttributValues(spu.getSpuAttributeValues(), spu.getId());

        // 更新商品服务支持
        spuServicceSupportService.updateSpuServiceSupport(spu.getSpuServiceSupports(), spu.getId());

        // 更新商品规格值
        spuSpecValueService.updateSpuSpecValues(spu.getSpuSpecValues(), spu.getId());

        // 根据商品id和storeId查询商品信息
        Map<String, Object> params = new HashMap<>();
        params.put("id", spu.getId());
        params.put("storeId", spu.getStoreId());

        PmsGoods sputemp = spuMapper.querySpu(params);

        if (Objects.isNull(sputemp)) {
            logger.error("updateSpu fail...");
            return 0;
        }

        // 更新单品
        skuService.updateSkus(spu.getSkus().stream().peek(sku -> sku.fixSkuNameForSave(spu.getName())).collect(Collectors.toList()), spu.getId(), spu.getStoreId(), sputemp.getCommissionRate());


        return 1;
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsByIds(Long[] ids) {
        return pmsGoodsMapper.deletePmsGoodsByIds(ids);
    }

    /**
     * 删除商品信息
     *
     * @param id 商品ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsById(Long id) {
        return pmsGoodsMapper.deletePmsGoodsById(id);
    }


    @Override
    public PmsGoods querySpu(long id, long storeId) {

        logger.debug("querySpu and id :{} \r\n storeId:{}", id, storeId);


        // 查询商品信息
        PmsGoods spu = querySimpleSpu(id, storeId);

        if (Objects.isNull(spu)) {
            logger.error("querySpu fail due to goods is not exist...");
            return spu;
        }

        // 设置商品的分类信息
        setSpuCategorys(spu);

        // 设置商品的品牌信息
        spu.setBrand(brandService.queryBrandById(spu.getBrandId(), CommonConstant.QUERY_WITH_NO_STORE));

        // 查询商品的服务支持
        spu.setSpuServiceSupports(spuServicceSupportService.queryBySpuId(id));

        // 查询商品的规格值信息(包括规格信息和规格值信息)
        spu.setSpuSpecValues(spuSpecValueService.queryBySpuIdWithSpec(id));

        // 查询商品图片
        spu.setSpuImages(spuImageService.queryBySpuId(id));

        // 查询商品属性值
        spu.setSpuAttributeValues(spuAttributeValueService.queryBySpuId(id));

        // 查询商品下面的单品信息
        spu.setSkus(skuService.querySkuBySpuId(id, spu.getStoreId(), PmsSkuItem.IMAGE, PmsSkuItem.MEMBER_PRICE, PmsSkuItem.SPEC, PmsSkuItem.BATCH));

        return spu;
    }


    /**
     * 分页查询单品信息(目前用在社区团购新增团购页面选择列表查询)
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param skuItemse  要设置的属性枚举
     * @return 返回单品信息 (包括单品的规格信息)
     */
    @Override
    public PageHelper<PmsSku> querySkus(PageHelper<PmsSku> pageHelper, long storeId, String name, String skuNo, PmsSkuItem... skuItemse) {

        logger.debug("querySkus and pageHelper:{} \r\n  skuSearchCondition:{}", pageHelper, storeId, name, skuNo, skuItemse);


        //获取单品分页结果集
        PageHelper<PmsSku> skuPageHelper = skuService.querySkusWithSpecs(pageHelper, storeId, name, skuNo, skuItemse);


        //根据spuID 查询移动版详情
        skuPageHelper.setList(skuPageHelper.getList().stream().map(sku -> sku.addMobileDesc(spuMapper.querySpu(sku.querySpuParam()).getMobileDesc())).collect(Collectors.toList()));

        return skuPageHelper;
    }


    @Transactional
    @Override
    public int deleteSpu(PmsGoods spu) {

        logger.debug("deleteSpu and goods:{}", spu);

        if (Objects.isNull(spu)) {
            logger.error("deleteSpu fail due to goods is null...");
            return 0;
        }
        // 删除商品信息
        if (spuMapper.deleteSpu(spu) == 0) {
            logger.error("deleteSpu fail...");
            return 0;
        }

        // 删除商品属性值
        spuAttributeValueService.deleteBySpuId(spu.getId());

        // 删除商品图片
        spuImageService.deleteBySpuId(spu.getId());

        // 删除商品服务支持
        spuServicceSupportService.deleteBySpuId(spu.getId());

        // 删除商品规格值
        spuSpecValueService.deleteBySpuId(spu.getId());

        // 删除单品
        skuService.deleteBySpuId(spu.getId(), spu.getStoreId());

        //删除单品关联信息
        // storeSkuService.deleteStoreSkuBySpuId(goods.getId());

        // 删除es中的商品数据
        //  ThreadTask.getInstance().addTask(() -> esSearchService.deleteSpu(goods.getId()));

        return 1;
    }

    @Override
    public int deleteSpus(List<PmsGoods> spus) {
        logger.debug("deleteSpus and spus:{}", spus);

        if (CollectionUtils.isEmpty(spus)) {
            return 0;
        }

        spus.parallelStream().forEach(this::deleteSpu);
        return 1;
    }

    @Transactional
    @Override
    public int updateShelvesStatus(List<Long> spuIds, String status, long storeId, Consumer<Long> consumer) {
        logger.debug("updateShelvesStatus and spuIds:{} \r\n status:{} \r\n storeId:{}", spuIds, status, storeId);

        if (CollectionUtils.isEmpty(spuIds)) {
            logger.error("updateShelvesStatus fail due to spuIds is empty....");
            return 0;
        }

        // 修改商品的上下架状态
        updateSpuShelvesStatus(spuIds, status, storeId);

        // 修改单品的上下架状态
        skuService.updateShelvesStatus(spuIds, status, storeId);



        // 如果是上架 并且是店铺商品和审核开关开启
        if (CommonConstant.ADMIN_STOREID != storeId && baseInfoSetService.isSkuNeedAudit() && "1".equals(status)) {
            // 修改商品为待审核状态
            updateSpuToAudit(spuIds, storeId);
            // 修改单品为待审核状态
            skuService.updateSkuToAudit(spuIds, storeId);
        }

        return 1;
    }

    /**
     * 修改商品的状态为待审核状态
     *
     * @param spuIds  商品id
     * @param storeId 店铺id
     */
    private void updateSpuToAudit(List<Long> spuIds, long storeId) {
        logger.debug("updateSpuToAudit and spuIds:{} \r\n storeId:{}", spuIds, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("spuIds", spuIds);
        params.put("status", "2");
        params.put("storeId", storeId);

        spuMapper.updateSpuAuditStatus(params);
    }

    /**
     * 修改商品的上下架状态
     *
     * @param spuIds  商品id
     * @param status  上下架状态
     * @param storeId 店铺id
     */
    private void updateSpuShelvesStatus(List<Long> spuIds, String status, long storeId) {
        logger.debug("updateSpuShelvesStatus and spuIds:{} \r\n status:{} \r\n storeId:{}", spuIds, status, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("spuIds", spuIds);
        params.put("status", status);
        params.put("storeId", storeId);
        spuMapper.updateShelvesStatus(params);
    }

    @Transactional
    @Override
    public int updateSpu(PmsGoods spu, Consumer<Long> consumer) {
        logger.debug("updateSpu  and goods:{}", spu);

        if (Objects.isNull(spu)) {
            logger.error("updateSpu fail due to goods is null....");
            return 0;
        }

        // 设置商品的默认图片
        spu.setDefaultPic();

        // 如果不是平台的商品并且审核开关打开 则需要判断是否修改了商品如果商品修改了 则商品下的所有单品都需要审核
        if (CommonConstant.ADMIN_STOREID != spu.getStoreId() && baseInfoSetService.isSkuNeedAudit()) {
            setSkuAuditStatus(spu);
        }

        // 更新商品信息
        if (spuMapper.updateSpu(spu) == 0) {
            logger.error("updateSpu fail....");
            return 0;
        }

        // 设置店铺id
        spu.setSkuStoreIdAndShelvesStatus();

        // 设置单品物流模版id
        spu.setSkuLogisticsTemplateId();

        // 设置商品相关的商品id
        spu.setSpuLinkedSpuId();

        // 更新商品图片
        spuImageService.updateSpuImages(spu.getSpuImages(), spu.getId());

        // 更新商品属性值
        spuAttributeValueService.updateSpuAttributValues(spu.getSpuAttributeValues(), spu.getId());

        // 更新商品服务支持
        spuServicceSupportService.updateSpuServiceSupport(spu.getSpuServiceSupports(), spu.getId());

        // 更新商品规格值
        spuSpecValueService.updateSpuSpecValues(spu.getSpuSpecValues(), spu.getId());

        // 根据商品id和storeId查询商品信息
        Map<String, Object> params = new HashMap<>();
        params.put("id", spu.getId());
        params.put("storeId", spu.getStoreId());

        PmsGoods sputemp = spuMapper.querySpu(params);

        if (Objects.isNull(sputemp)) {
            logger.error("updateSpu fail...");
            return 0;
        }

        // 更新单品
        skuService.updateSkus(spu.getSkus().stream().peek(sku -> sku.fixSkuNameForSave(spu.getName())).collect(Collectors.toList()), spu.getId(), spu.getStoreId(), sputemp.getCommissionRate());


        return 1;
    }

    @Override
    public String queryMobileDesc(long id) {
        logger.debug("queryMobileDesc and id :{}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", CommonConstant.QUERY_WITH_NO_STORE);

        // 查询商品信息
        PmsGoods spu = spuMapper.querySpu(params);


        return Objects.isNull(spu) ? "" : spu.getMobileDesc();
    }

    @Override
    public PmsGoods querySimpleSpu(long spuId, long storeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", spuId);
        params.put("storeId", storeId);

        return spuMapper.querySpu(params);
    }

    /**
     * 根据三级分类id查询是否关联商品总条数
     *
     * @param ThirdCateId 商品三级分类id
     * @return 返回 0 即表示该三级分类不关联商品 >0 即表示该三级分类关联商品
     */
    @Override
    public int querySpuByThirdCateId(long ThirdCateId) {
        logger.debug("querySpuByThirdCateId and ThirdCateId :{}", ThirdCateId);
        return spuMapper.querySpuByThirdCateId(ThirdCateId);
    }


    @Override
    public int querySpuCountForEs() {
        logger.debug("querySpuCountForEs ......");
        return spuMapper.querySpuCountForEs();
    }


    @Override
    public List<PmsGoods> querySpuForEs(int start, int size) {
        logger.debug("querySpuForEs and start:{} \r\n size:{} \r\n", start, size);
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("size", size);

        // 商品信息
        List<PmsGoods> spus = spuMapper.querySpuForEs(params);

        if (!CollectionUtils.isEmpty(spus)) {
            return spus.stream().map(spu -> this.querySpu(spu.getId(), CommonConstant.QUERY_WITH_NO_STORE)).collect(Collectors.toList());
        }

        return spus;
    }

    @Override
    public int updateCommission(long id, BigDecimal commissionRate, BigDecimal sCommissionRate, long storeId) {
        logger.debug("updateCommission and id :{} \r\n commission :{} \r\n sCommissionRate :{} \r\n storeId", id, commissionRate, sCommissionRate, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("commissionRate", commissionRate);
        params.put("sCommissionRate", sCommissionRate);
        params.put("storeId", storeId);

        //给单品设置佣金比例
        if (skuService.updateCommission(id, commissionRate, sCommissionRate, storeId) == 0) {
            logger.error("updateCommission fail due to updateCommissionForSku fail");
            return 0;
        }
        //给商品设置佣金比例
        return spuMapper.updateCommission(params);
    }

    @Transactional
    @Override
    public int updateCommissions(List<Long> ids, BigDecimal commissionRate, BigDecimal sCommissionRate, long storeId) {
        logger.debug("updateCommissions and ids :{} \r\n commissionRate :{} \r\n sCommissionRate :{} \r\n storeId", ids, commissionRate, sCommissionRate, storeId);

        if (CollectionUtils.isEmpty(ids)) {
            logger.error("updateCommissions fail due to ids is empty");
            return 0;
        }
        ids.stream().forEach(id -> updateCommission(id, commissionRate, sCommissionRate, storeId));
        return 1;
    }

    @Transactional
    @Override
    public int auditPass(Consumer<Long> consumer, long spuId) {
        logger.debug("auditPass and spuId:{}", spuId);

        // 商品审核通过
        spuMapper.auditPass(spuId);

        // 单品审核通过
        skuService.auditPass(spuId);

        // 回调es 刷新es

        return 0;
    }

    @Transactional
    @Override
    public int auditRefuse(String reason, Consumer<Long> consumer, long spuId) {
        logger.debug("auditRefuse and spuId:{}", spuId);

        // 商品审核拒绝
        spuMapper.auditRefuse(spuId);

        // 单品审核拒绝
        skuService.auditRefuse(reason, spuId);
        // 回调es 刷新es

        return 0;
    }

    @Override
    @Transactional
    public int updateShelvesStatusByStoreIds(String status, List<Long> storeIds) {
        logger.debug("updateShelvesStatusByStoreIds and status:{} \r\n storeIds:{}", status, storeIds);
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("storeIds", storeIds);
        //更改单品上下架状态
        skuService.updateShelvesStatusByStoreIds(status, storeIds);
        //更改商品上下架状态
        spuMapper.updateShelvesStatusByStoreIds(params);
        return 1;
    }

    @Override
    public BigDecimal queryCateRateBySkuId(String skuId) {

        logger.debug("queryCateRateBySkuId and skuId:{}", skuId);

        // 查询单品信息
        PmsSku sku = skuService.querySkuById(skuId);

        if (Objects.isNull(sku)) {
            logger.error("queryCateRateBySkuId fail due to sku is not exist....");
            return BigDecimal.ZERO;
        }

        // 查询商品信息
        PmsGoods spu = this.querySimpleSpu(sku.getSpuId(), sku.getStoreId());

        if (Objects.isNull(spu)) {
            logger.error("queryCateRateBySkuId fail due to goods is not exist...");
            return BigDecimal.ZERO;
        }

        // 查询商品的分类信息
        PmsCategory category = categoryService.queryCategoryById(spu.getThirdCateId());

        if (Objects.isNull(category)) {
            logger.error("queryCateRateBySkuId fail due to category is not exist...");
            return BigDecimal.ZERO;
        }

        return category.getRate();
    }

    @Override
    public int querySpuCountByTypeId(long typeId) {
        logger.debug("querySpuCountByTypeId and typeId:{}", typeId);
        return spuMapper.querySpuCountByTypeId(typeId);
    }

    @Override
    public PmsGoods querySeoBySpuId(long spuId) {
        logger.debug("querySeoBySpuId and spuId:{}", spuId);
        return spuMapper.querySeoBySpuId(spuId);
    }

    @Override
    public PageHelper<StoreSpu> queryStoreSpuList(PageHelper<StoreSpu> pageHelper, String name, long storeId) {
        logger.debug("queryStoreSpuList and pageHelper:{} \r\n name:{} \r\n storeId:{}", pageHelper, name, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        String siteUrl = baseInfoSetService.queryBaseInfoSet().getSiteUrlWithBias();
        return pageHelper.setListDates(spuMapper.queryStoreSpuList(pageHelper.getQueryParams(params, spuMapper.queryStoreSpuListCount(params)))
                .stream().map(storeSpu ->
                {
                    // storeSpu.buildOtherInfo(storeSkuService.queryStoreSkuListBySpuId(storeSpu.getSpuId(), storeId));
                    storeSpu.setVisitUrl(siteUrl + "#/spudetail?id=" + skuService.querySkuBySpuId(storeSpu.getSpuId(), CommonConstant.ADMIN_STOREID).stream().findFirst().orElse(new PmsSku()).getId());
                    return storeSpu;
                })
                .collect(Collectors.toList()));
    }

    @Override
    public PageHelper<StoreSpu> queryStoreOnSaleSpuList(PageHelper<StoreSpu> pageHelper, String name, long storeId) {
        logger.debug("queryStoreOnSaleSpuList and pageHelper:{} \r\n name:{} \r\n storeId:{}", pageHelper, name, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("storeId", storeId);
        return pageHelper.setListDates(spuMapper.queryStoreOnSaleSpuList(pageHelper.getQueryParams(params, spuMapper.queryStoreOnSaleSpuListCount(params))));
    }

    @Override
    public StoreSpu queryStoreSpu(long spuId, long storeId) {
        logger.debug("queryStoreSpu and spuId:{} \r\n storeId:{}", spuId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", spuId);
        params.put("storeId", CommonConstant.ADMIN_STOREID);
        StoreSpu storeSpu = new StoreSpu();
        PmsGoods spu = spuMapper.querySpu(params);
        if (Objects.isNull(spu)) {
            logger.debug("queryStoreSpu error : goods is null ");
            return null;
        }
        BeanUtils.copyProperties(spu, storeSpu);        // 查询商品的规格值信息(包括规格信息和规格值信息)
        storeSpu.setSpuSpecValues(spuSpecValueService.queryBySpuIdWithSpec(spuId));
        // 查询商品下面的单品信息
        storeSpu.setSkuList(skuService.querySkuBySpuId(spuId, CommonConstant.ADMIN_STOREID, PmsSkuItem.SPEC)
                .stream().map(sku -> sku.buildStoreSkuList(storeSkuService.queryStoreSkuListBySkuId(sku.getId(), storeId))).collect(Collectors.toList()));

        return storeSpu;
    }

    @Override
    public List<PmsGoods> querySpuByIdsForExport(List<Long> ids, long storeId) {
        logger.debug("querySpuByIdsForExport and ids:{} \r\n storeId:{}", ids, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);
        return spuMapper.querySpuByIdsForExport(params);
    }

    @Override
    public List<PmsGoods> queryAllSpuForExport(long storeId) {
        logger.debug("queryAllSpuForExport and storeId:{}", storeId);
        return spuMapper.queryAllSpuForExport(storeId);
    }

    /**
     * 分页查询未关联店铺三级分类的商品
     *
     * @param pageHelper 分页帮助类
     * @param name       商品名称
     * @param spuId      商品id
     * @param storeId    店铺id
     * @return 返回商品信息
     */
    @Override
    public PageHelper<PmsGoods> queryAllSpusWithoutStoreCategory(PageHelper<PmsGoods> pageHelper, String name, Long spuId, long storeId) {
        logger.debug("queryAllSpusWithoutStoreCategory and pageHelper:{} \r\n  name:{} \r\n spuId:{} \r\n storeId:{}", pageHelper, name, spuId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("id", spuId);
        params.put("storeId", storeId);
        return pageHelper.setListDates(spuMapper.queryAllSpusWithoutStoreCategory(pageHelper.getQueryParams(params, spuMapper.queryAllSpusWithoutStoreCategoryCount(params))));
    }

    /**
     * 根据店铺三级分类分页查询商品信息
     *
     * @param pageHelper   分页帮助类
     * @param name         商品名称
     * @param spuId        商品id
     * @param storeId      店铺id
     * @param storeTcateId 店铺三级分类id
     * @return 返回商品信息
     */
    @Override
    public PageHelper<PmsGoods> queryAllSpusByStoreCategory(PageHelper<PmsGoods> pageHelper, String name, Long spuId, long storeId, long storeTcateId) {
        logger.debug("queryAllSpusByStoreCategory and pageHelper:{} \r\n  name:{} \r\n spuId:{} \r\n storeId:{} \r\n storeTcateId:{}", pageHelper, name, spuId, storeId, storeTcateId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("id", spuId);
        params.put("storeId", storeId);
        params.put("storeTcateId", storeTcateId);
        return pageHelper.setListDates(spuMapper.queryAllSpusByStoreCategory(pageHelper.getQueryParams(params, spuMapper.queryAllSpusByStoreCategoryCount(params))));
    }

    /**
     * 根据店铺三级分类查询商品id
     *
     * @param storeId      店铺id
     * @param storeTcateId 店铺三级分类id
     * @return 返回商品id数组
     */
    @Override
    public Long[] queryAllSpuIdByStoreCategory(long storeId, long storeTcateId) {
        logger.debug("queryAllSpuIdByStoreCategory and storeId:{} \r\n storeTcateId:{}", storeId, storeTcateId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("storeTcateId", storeTcateId);
        return spuMapper.queryAllSpuIdByStoreCategory(params);
    }

    /**
     * 修改店铺三级分类关联商品
     *
     * @param ids          商品id数组
     * @param storeFcateId 店铺商品一级分类
     * @param storeScateId 店铺商品二级分类
     * @param storeTcateId 店铺商品三级分类
     * @param storeId      店铺id
     * @return 成功>0 失败0
     */
    @Override
    public int updateSpuWithStoreCategory(Long[] ids, long storeFcateId, long storeScateId, long storeTcateId, long storeId) {
        logger.debug("updateSpuWithStoreCategory and ids:{} \r\n  storeFcateId:{} \r\n storeScateId:{} \r\n storeTcateId:{} \r\n storeId:{}", ids, storeFcateId, storeScateId, storeTcateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeFcateId", storeFcateId);
        params.put("storeScateId", storeScateId);
        params.put("storeTcateId", storeTcateId);
        params.put("storeId", storeId);
        return spuMapper.updateSpuWithStoreCategory(params);
    }

    /**
     * 取消店铺三级分类关联商品
     *
     * @param ids     商品id数组
     * @param storeId 店铺id
     * @return 成功>0 失败0
     */
    @Override
    public int cancelSpuWithStoreCategory(Long[] ids, long storeId) {
        logger.debug("cancelSpuWithStoreCategory and ids:{} \r\n storeId:{}", ids, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);
        return spuMapper.cancelSpuWithStoreCategory(params);
    }

    /**
     * 根据三级分类id取消关联商品
     *
     * @param storeTcateId 三级分类id
     * @param storeId      店铺id
     * @return 成功>0 失败0
     */
    @Override
    public int cancelSpuWithStoreCategoryByStoreTcateId(long storeTcateId, long storeId) {
        logger.debug("cancelSpuWithStoreCategoryByStoreTcateId and storeTcateId:{} \r\n storeId:{}", storeTcateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeTcateId", storeTcateId);
        params.put("storeId", storeId);
        return spuMapper.cancelSpuWithStoreCategoryByStoreTcateId(params);
    }

    /**
     * 修改商品物流模版id
     *
     * @param logisticsTemplateId        物流模版id
     * @param defaultLogisticsTemplateId 默认物流模版id
     * @param storeId                    店铺id
     * @return 成功>0 否则失败
     */
    @Override
    public int updateSpuLogisticsTemplateId(long logisticsTemplateId, long defaultLogisticsTemplateId, long storeId) {
        logger.debug("updateSpuLogisticsTemplateId and logisticsTemplateId:{} \r\n defaultLogisticsTemplateId :{} \r\n storeId:{}", logisticsTemplateId, defaultLogisticsTemplateId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("logisticsTemplateId", logisticsTemplateId);
        params.put("defaultLogisticsTemplateId", defaultLogisticsTemplateId);
        params.put("storeId", storeId);
        return spuMapper.updateSpuLogisticsTemplateId(params);
    }

    /**
     * 设置单品的审核状态
     *
     * @param spu 商品信息
     */
    private void setSkuAuditStatus(PmsGoods spu) {

        // 设置商品为审核状态
        spu.setAuditStatus(baseInfoSetService.isSkuNeedAudit());

        // 设置商品下面单品的审核状态和商品一致
        spu.setSkuStatus();
    }

    /**
     * 设置商品的分类
     *
     * @param spu 商品信息
     */
    private void setSpuCategorys(PmsGoods spu) {
        spu.setFirstCategory(categoryService.selectPmsCategoryById(spu.getFirstCateId()));
        spu.setSecondCategory(categoryService.selectPmsCategoryById(spu.getSecondCateId()));
        spu.setThirdCategory(categoryService.selectPmsCategoryById(spu.getThirdCateId()));
    }

    /**
     * 设置商品的分类和品牌数据
     *
     * @param spu 商品信息
     */
    private void setBrandAndCategory(PmsGoods spu) {
        spu.setBrand(brandService.selectPmsBrandById(spu.getBrandId()));
        spu.setThirdCategory(categoryService.selectPmsCategoryById(spu.getThirdCateId()));
    }

    /**
     * 设置商品的分类信息
     *
     * @param spu 商品信息
     */
    private void setSpuCategory(PmsGoods spu) {
        // 店铺的商品需要设置 平台的不需要 已经带过来
        if (spu.isStoreSpu()) {
            // 二级分类
            PmsCategory secondCate = categoryService.selectPmsCategoryById(categoryService.selectPmsCategoryById(spu.getThirdCateId()).getParentId());
            spu.setSecondCateId(secondCate.getId());
            spu.setFirstCateId(secondCate.getParentId());
        }
    }

    /**
     * 设置商品的三级分类和品牌信息
     *
     * @param spus 商品集合
     * @return 返回商品集合
     */
    private List<PmsGoods> setBrandAndCategorys(List<PmsGoods> spus) {

        spus.parallelStream().forEach(this::setBrandAndCategory);
        return spus;
    }

    /**
     * 设置访问商品的url
     *
     * @param spus 商品信息
     * @return 返回商品信息
     */
    private List<PmsGoods> setVisitUrl(List<PmsGoods> spus) {
        spus.stream().forEach(this::setVisitUrl);
        return spus;
    }


    /**
     * 设置访问商品的url
     *
     * @param spu 商品
     */
    private void setVisitUrl(PmsGoods spu) {
        // 根据商品id查询单品信息
        List<PmsSku> skus = skuService.querySkuBySpuId(spu.getId(), spu.getStoreId());
        if (!CollectionUtils.isEmpty(skus)) {
            //  goods.setVisitUrl(baseInfoSetService.queryBaseInfoSet().getSiteUrlWithBias() + "#/spudetail?id=" + skus.get(0).getId());
        }
    }
}
