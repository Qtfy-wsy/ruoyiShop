package com.ruoyi.store.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.goods.domain.PmsBrandApply;
import com.ruoyi.goods.service.*;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.domain.TStoreSignedCategory;
import com.ruoyi.store.mapper.TStoreInfoMapper;
import com.ruoyi.store.service.ITStoreCommentService;
import com.ruoyi.store.service.ITStoreEvaluationService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.store.service.ITStoreSignedCategoryService;
import com.ruoyi.store.vo.OnSaleStoreQueryParam;
import com.ruoyi.store.vo.StoreBusiness;
import com.ruoyi.store.vo.StoreBusinessInfo;
import com.ruoyi.store.vo.StoreReview;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.service.ISysStoreRoleService;
import com.ruoyi.system.service.ISysStoreUserService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

/**
 * 店铺信息Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStoreInfoServiceImpl implements ITStoreInfoService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreInfoServiceImpl.class);
    @Autowired
    private TStoreInfoMapper tStoreInfoMapper;


    /**
     * 注入店铺信息mapper
     */
    @Autowired
    private TStoreInfoMapper storeInfoMapper;

    /**
     * 注入会员service
     */
    @Autowired
    private ISysStoreUserService customerService;
    /**
     * 签约分类service
     */
    @Autowired
    private ITStoreSignedCategoryService storeSignedCategoryService;

    /**
     * 品牌申请service
     */
    @Autowired
    private IPmsBrandApplyService brandApplyService;

    /**
     * 品牌service
     */
    @Autowired
    private IPmsBrandService brandService;

    /**
     * 分类service
     */
    @Autowired
    private IPmsCategoryService categoryService;

    /**
     * 注入店铺角色service
     */
    @Autowired
    private ISysStoreRoleService storeRoleService;


    /**
     * 注入店铺评论服务
     */
    @Autowired
    private ITStoreCommentService storeCommentService;


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
     * 注入门店评价服务
     */
    @Autowired
    private ITStoreEvaluationService storeEvaluationService;

    /**
     * 根据店铺id查询店铺信息
     *
     * @param storeId 店铺id
     * @return 店铺信息
     */
    @Override
    public TStoreInfo queryStoreInfo(Long storeId) {
        logger.debug("queryAuditPassStoreInfo and storeId:{}", storeId);

        if (storeId == 0) {
            TStoreInfo storeInfo = new TStoreInfo();
            storeInfo.setId(0L);
            storeInfo.setStoreName("商城自营");
            //TODO 商城自营 logo
            storeInfo.setAvatarPicture("http://yanxuan.nosdn.127.net/56da5270172244be56c00fdc8eb24fae.png");
            return storeInfo;
        }

        return storeInfoMapper.queryStoreInfo(storeId);
    }


    /**
     * 查询开店时填写的信息
     *
     * @param customerId 会员ID
     * @return 店铺信息
     */
    @Override
    public TStoreInfo findOpenStoreInfo(long customerId) {
        logger.debug("findOpenStoreInfo and customerId:{}", customerId);
        SysUser customer = customerService.selectUserById(customerId);
        TStoreInfo returnStoreInfo = new TStoreInfo();
        returnStoreInfo.setDelFlag(1);
        TStoreInfo storeInfo = queryStoreInfo(customer.getStoreId());
        return storeInfo == null ? returnStoreInfo : storeInfo;
    }

    /**
     * 开店处理店铺信息
     *
     * @param storeInfo  店铺实体类
     * @param customerId 会员ID
     * @return 返回值跳转页面  0 出错 1 下一页 2店铺首页 3 拒绝通过页 4 登录页
     */
    @Override
    public int dealStoreInfo(TStoreInfo storeInfo, long customerId) {
        logger.debug("dealStoreInfo and storeInfo:{}\r\n customerId:{}", storeInfo, customerId);
        SysUser customer = customerService.selectUserById(customerId);
        TStoreInfo queryStoreInfo = storeInfoMapper.queryStoreInfo(customer.getStoreId());
        storeInfo.setStatus("0");
        //店铺信息有无信息

        //无信息   新增
        if (Objects.isNull(queryStoreInfo)) {
            //新增
            if (checkCompanyNameExist(storeInfo.getCompanyName(), CommonConstant.QUERY_WITH_NO_STORE) > 0) {
                logger.error("dealStoreInfo fail :");
                return -1;
            }
            if (storeInfoMapper.addStoreInfo(storeInfo) == 1) {
                //更新会员表中的数据
                SysUser reCustomer = new SysUser();
                reCustomer.setStoreId(storeInfo.getId());
                reCustomer.setUserId(customer.getUserId());
                //更新会员表中的storeI的和type
                return customerService.updateUser(reCustomer);
            }
            return 0;
        }

        //有信息 编辑
        storeInfo.setId(queryStoreInfo.getId());
        if (checkCompanyNameExist(storeInfo.getCompanyName(), storeInfo.getId()) > 0) {
            logger.error("dealStoreInfo fail :");
            return -1;
        }
        //编辑
        return storeInfoMapper.editStoreInfo(storeInfo);

    }

    /**
     * 开店-处理店铺经营信息
     *
     * @param customerId  会员ID
     * @param storeName   店铺名称
     * @param categoryIds 分类ids
     * @param brandIds    品牌ids
     * @param brands      自定义品牌集合
     * @return 返回码 1处理成功
     */
    @Override
    @Transactional
    public int dealStoreBusinessInfo(long customerId, String storeName, long[] categoryIds, long[] brandIds, List<PmsBrand> brands) {
        logger.debug("dealStoreBusinessInfo and customerId:{}\r\n storeName:{}\r\n categoryIds:{}\r\n categoryIds:{}\r\n" +
                "brandIds:{}\r\n brands:{}", customerId, storeName, categoryIds, brandIds, brands);
        TStoreInfo storeInfo = storeInfoMapper.queryStoreInfoByName(storeName);
        SysUser customer = customerService.selectUserById(customerId);
        if (!Objects.isNull(storeInfo) && customer.getStoreId() != storeInfo.getId()) {
            return -1;
        }
        long storeId = customer.getStoreId();
        String name = customer.getUserName();
        saveOtherStoreInfo(categoryIds, brandIds, brands, storeId, name);
        //更新店铺名称
        return storeInfoMapper.editStoreName(new TStoreInfo().getStoreInfoToEditStoreName(storeId, "1", storeName));
    }

    /**
     * 保存店铺其他信息
     *
     * @param categoryIds  分类ids
     * @param brandIds     品牌ids
     * @param brands       自定义品牌集合
     * @param storeId      店铺id
     * @param customerName 用户名
     */
    private void saveOtherStoreInfo(long[] categoryIds, long[] brandIds, List<PmsBrand> brands, long storeId, String customerName) {
        //先删后增
        storeSignedCategoryService.deleteSignedCategory(storeId);
        brandApplyService.deleteStoreBrand(storeId);
        brandService.batchDeleteCustomBrand(storeId);
        //添加签约分类
        if (!ArrayUtils.isEmpty(categoryIds)) {
            List<TStoreSignedCategory> storeSignedCategories = new ArrayList<>();
            Arrays.stream(categoryIds).forEach(categoryId -> storeSignedCategories.add(new TStoreSignedCategory().getStoreSignedCategory(storeId, categoryId, true)));
            storeSignedCategoryService.addSignedCategory(storeSignedCategories);
        }
        //添加店铺品牌
        if (!ArrayUtils.isEmpty(brandIds)) {
            List<PmsBrandApply> brandApplies = new ArrayList<>();
            Arrays.stream(brandIds).forEach(brandId -> brandApplies.add(new PmsBrandApply().getBrandApply(storeId, brandId)));
            brandApplyService.addStoreBrand(brandApplies);
        }
        //添加自定义品牌
        if (CollectionUtils.isNotEmpty(brands)) {
            List<PmsBrand> brandList = new ArrayList<>();
            brands.forEach(brand -> brandList.add(new PmsBrand().addMySelfBrand(brand.getName(), brand.getUrl(), brand.getCertificatUrl(), storeId, customerName)));
            brandService.batchAddCustomBrand(brandList);
        }
    }

    @Override
    @Transactional
    public int addStore(StoreBusiness storeBusiness) {
        logger.debug("addStore and storeBusiness:{}", storeBusiness);

        SysUser customer = customerService.selectUserByUserName(storeBusiness.getMobile());
        if (Objects.nonNull(customer)) {
            if (Objects.nonNull(customer.getStoreId()) && CommonConstant.ADMIN_STOREID != customer.getStoreId()) {
                logger.error("addStore fail:already has store");
                return -2;
            }
            storeBusiness.getStoreInfo().setStatus("1");
            storeInfoMapper.insertTStoreInfo(storeBusiness.getStoreInfo());

            long storeId = storeBusiness.getStoreInfo().getId();
            customer.setStoreId(storeId);

            customerService.updateUser(customer);
            saveOtherStoreInfo(storeBusiness.getCategoryIds(), storeBusiness.getBrandIds(), storeBusiness.getBrands(), storeId, customer.getUserName());
            //为该用户添加权限

            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();

            SysUserRole ur = new SysUserRole();
            ur.setUserId(customer.getUserId());
            ur.setRoleId(1L);
            list.add(ur);

            if (list.size() > 0) {
                storeRoleService.batchUserRole(list);
            }
            //设置店铺审核通过
            int res = storeInfoMapper.passStoreAudit(storeBusiness.getStoreInfo());
            if (res == 1) {
                //初始化商家模板
                //   storeTemplateService.initTemplate(storeBusiness.getStoreInfo().getId());
            }
            return 1;
        }
        logger.error("addStore fail:member is null");
        return -1;
    }

    /**
     * 开店查询店铺信息
     *
     * @param customerId 会员id
     * @param status     品牌状态 状态  0 申请中  1通过 2 拒绝
     * @return 店铺信息
     */
    @Override
    public StoreBusinessInfo queryStoreBusinessInfoForOpneStore(long customerId, String status) {
        long storeId = customerService.selectUserById(customerId).getStoreId();
        return new StoreBusinessInfo().getStoreBusinessInfo(queryStoreInfo(storeId), categoryService.queryTwoCategoryByStoreId(storeId),
                categoryService.queryThreeCategoryByStoreId(storeId), brandService.queryStoreBrands(storeId, status),
                brandService.queryCustomBrandByStoreIdAndStatus(storeId, status));
    }

    /**
     * 开店-查询店铺信息
     *
     * @param storeId 店铺id
     * @param status  品牌状态 状态  0 申请中  1通过 2 拒绝
     * @return 店铺信息
     */
    @Override
    @Transactional
    public StoreBusinessInfo queryStoreBusinessInfo(long storeId, String status) {
        logger.debug("queryStoreBusinessInfo and storeId:{}\r\n status:{}", storeId, status);
        return new StoreBusinessInfo().getStoreBusinessInfo(queryStoreInfo(storeId), categoryService.queryTwoCategoryByStoreId(storeId),
                categoryService.queryThreeCategoryByStoreId(storeId), brandService.queryStoreBrands(storeId, status),
                brandService.queryCustomBrandByStoreIdAndStatus(storeId, status));
    }

    /**
     * 查询已审核/未审核商家集合
     *
     * @param pageHelper     分页帮助类
     * @param status         店铺状态 0填写资料中 1店铺审核中 2审核通过 3审核不通过 4店铺关闭
     * @param companyName    公司名称
     * @param storeName      店铺名称
     * @param createTime     创建时间
     * @param customerMobile 用户手机号
     * @param provinceId     省份id
     * @return 已审核/未审核商家集合
     */
    @Override
    public PageHelper<TStoreInfo> queryStoreInfoForAuditList(PageHelper<TStoreInfo> pageHelper, String status, String companyName, String storeName, String createTime, String customerMobile, long provinceId) {
        logger.debug("queryStoreInfoForAuditList and status:{}\r\n companyName:{}\r\n storeName:{}\r\n createTime:{}", status, companyName, storeName, createTime);
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("companyName", companyName);
        params.put("storeName", storeName);
        params.put("createTime", createTime);
        if (!StringUtils.isEmpty(customerMobile)) {
            params.put("mobile", customerMobile);
        }
        params.put("provinceId", provinceId);
        return pageHelper.setListDates(storeInfoMapper.queryStoreInfoForAuditList(pageHelper.getQueryParams(params, storeInfoMapper.queryStoreInfoForAuditListCount(params))));
    }

    /**
     * 编辑店铺有效期,结算周期,是否关店
     *
     * @param storeInfo 店铺信息
     * @return 编辑返回码
     */
    @Override
    @Transactional
    public int editStoreTimeAndIsClose(TStoreInfo storeInfo, Consumer<Integer> consumer) {
        logger.debug("editStoreTimeAndIsClose and storeInfo:{}", storeInfo);
        if ("4".equals(storeInfo.getStatus())) {
            //更改商品为下架状态
            spuService.updateShelvesStatusByStoreIds("0", Arrays.asList(storeInfo.getId()));
        }
        //更改店铺信息
        storeInfoMapper.editStoreTimeAndIsClose(storeInfo);
        //回调
        consumer.accept(1);
        return 1;
    }

    /**
     * 通过商家审核
     *
     * @param storeInfo 商家实例
     * @return 成功返回1，失败返回0
     */
    @Override
    @Transactional
    public int passStoreAudit(TStoreInfo storeInfo) {
        logger.debug("passStoreAudit and storeInfo :{}", storeInfo);
        passStoreAuditDealData(storeInfo.getId());
        int res = storeInfoMapper.passStoreAudit(storeInfo);
        if (res == 1) {
            //初始化商家模板
            //storeTemplateService.initTemplate(storeInfo.getId());
        }
        return res;
    }

    /**
     * 拒绝商家审核
     *
     * @param storeInfo 商家实例
     * @return 成功返回1，失败返回0
     */
    @Override
    public int refuseStoreAudit(TStoreInfo storeInfo) {
        logger.debug("refuseStoreAudit and storeInfo :{}", storeInfo);
        return storeInfoMapper.refuseStoreAudit(storeInfo);
    }

    /**
     * 删除商家
     *
     * @param id 商家id
     * @return 成功返回1，失败返回0
     */
    @Override
    @Transactional
    public int deleteStore(long id) {
        logger.debug("deleteStore and id :{}", id);
        deleteStoreDealData(id);
        return storeInfoMapper.deleteStore(id);
    }

    /**
     * 编辑店铺信息-客服QQ-公司信息-银行信息
     *
     * @param storeInfo 店铺信息实体类
     * @param flag      1客服QQ 2公司信息 3银行信息
     * @return -1参数错误编辑失败 1 编辑成功
     */
    @Override
    public int editMyStoreInfo(TStoreInfo storeInfo, String flag) {
        logger.debug("editMyStoreInfo and storeInfo :{}\r\n flag:{}", storeInfo, flag);
        if ("1".equals(flag)) {
            return storeInfoMapper.editStoreInfoForServiceQQ(storeInfo);
        }
        if ("2".equals(flag)) {
            return storeInfoMapper.editStoreInfoForCompanyInfo(storeInfo);
        }
        if ("3".equals(flag)) {
            return storeInfoMapper.editStoreInfoForBankInfo(storeInfo);
        }
        return -1;
    }

    @Override
    public int queryStoreState(Long storeId) {
        logger.debug("queryStoreState and storeId:{}", storeId);
        if (ObjectUtils.isEmpty(storeId) || storeId <= 0) {
            logger.error("queryStoreState error : storeId is null ");
            return -1;
        }
        TStoreInfo storeInfo = storeInfoMapper.queryStoreInfo(storeId);
        if (ObjectUtils.isEmpty(storeInfo)) {
            logger.error("queryStoreState error : store not exist ");
            return -2;
        }
        if ("1".equals(storeInfo.getDelFlag())) {
            logger.error("queryStoreState error : store is deleted ");
            return -3;
        }
        if ("1".equals(storeInfo.getStatus())) {
            logger.error("queryStoreState error : store is waiting for review ");
            return -4;
        }
        if ("3".equals(storeInfo.getStatus())) {
            logger.error("queryStoreState error : store is been rejected ");
            return -5;
        }
        if ("4".equals(storeInfo.getStatus())) {
            logger.error("queryStoreState error : store is close ");
            return -6;
        }
        if (!"2".equals(storeInfo.getStatus())) {
            logger.error("queryStoreState error : store is not pass ");
            return -4;
        }
        if (storeInfo.getEffectiveTime().isBefore(LocalDateTime.now())) {
            logger.error("queryStoreState error : store is not effective ");
            return -7;
        }
        return 1;
    }

    @Override
    public PageHelper<TStoreInfo> queryStoreInfoForSearch(PageHelper<TStoreInfo> pageHelper, String keyword, int orderBy) {
        logger.debug("queryStoreInfoForSearch and keyword:{} \r\n orderBy:{}", keyword, orderBy);
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("orderBys", orderBy);
        return pageHelper.setListDates(storeInfoMapper.queryStoreInfoForSearch(pageHelper.getQueryParams(params, storeInfoMapper.queryStoreInfoForSearchCount(params)))
                .stream().map(storeInfo -> storeInfo.buildStoreScore(storeCommentService.queryStoreScore(storeInfo.getId()))).collect(toList())
                .stream().map(storeInfo -> storeInfo.bulidSkus(skuService.queryFiveDataForAttentionStore(storeInfo.getId()))).collect(toList())
                .stream().map(storeInfo ->
                        storeInfo.buildSkusCount(skuService.querySkuCountByStoreId(storeInfo.getId()))
                ).collect(toList()));
    }

    @Override
    @Transactional
    public int closeStores(List<Long> ids, Consumer<Integer> consumer) {
        logger.debug("closeStores and ids:{} ", ids);
        if (CollectionUtils.isEmpty(ids)) {
            logger.error("closeStores fail : ids is empty");
            return 0;
        }
        //更改店铺商品状态为下架
        spuService.updateShelvesStatusByStoreIds("0", ids);
        //关闭店铺
        storeInfoMapper.closeStores(ids);
        //回调
        consumer.accept(1);
        return 1;
    }

    @Override
    public int checkStoreNameExist(String storeName, long storeId) {
        logger.debug("checkStoreNameExist and storeName:{}", storeName);
        Map<String, Object> params = new HashMap<>();
        params.put("storeName", storeName);
        params.put("storeId", storeId);
        return storeInfoMapper.queryStoreCountByStoreName(params);
    }

    /**
     * 校验公司名是否存在(store端开店用)
     *
     * @param companyName 店铺名称
     * @param customerId  用户ID
     * @return 0 可用  1 不可用
     */
    @Override
    public int checkCompanyNameForOpenStore(String companyName, long customerId) {
        logger.debug("checkCompanyNameForOpenStore and companyName:{} , customerId: {}", companyName, customerId);
        Map<String, Object> params = new HashMap<>(2);
        params.put("companyName", companyName);
        params.put("storeId", customerService.selectUserById(customerId).getStoreId());
        return storeInfoMapper.queryStoreCountByCompanyName(params);
    }

    @Override
    public int checkCompanyNameExist(String companyName, long storeId) {
        logger.debug("checkCompanyNameExist and companyName:{}", companyName);
        Map<String, Object> params = new HashMap<>();
        params.put("companyName", companyName);
        params.put("storeId", storeId);
        return storeInfoMapper.queryStoreCountByCompanyName(params);
    }

    @Override
    public TStoreInfo selStoreInfo(Long storeId) {
        logger.debug("selStoreInfo and storeId:{}", storeId);

        if (Objects.isNull(storeId)) {
            logger.error("selStoreInfo fail : storeId is null");
            return null;
        }
        TStoreInfo storeInfo = this.queryStoreInfo(storeId);
        //获取店铺评分平均数
        storeInfo.setAveScore(storeEvaluationService.queryStoreAveScore(storeId));
        return storeInfo;
    }

    @Override
    public TStoreInfo queryOneOnSaleStore(String skuId, long cityId) {
        logger.debug("queryOneOnSaleStore and skuId:{} \r\n cityId:{}", skuId, cityId);
        TStoreInfo storeInfo = storeInfoMapper.queryOneOnSaleStore(skuId, cityId);
        if (Objects.isNull(storeInfo)) {
            logger.error("queryOneOnSaleStore fail : no storeInfo");
            return null;
        }
        return storeInfo.buildAveScore(storeEvaluationService.queryStoreAveScore(storeInfo.getId()));
    }

    @Override
    public List<TStoreInfo> queryOnSaleStoreList(String skuId, long cityId) {
        logger.debug("queryOnSaleStoreList and skuId:{} \r\n cityId:{}", skuId, cityId);
        return storeInfoMapper.queryOnSaleStoreList(skuId, cityId);
    }

    @Override
    public TStoreInfo queryOneOnSaleStoreByCoordinate(OnSaleStoreQueryParam onSaleStoreQueryParam) {
        logger.debug("queryOneOnSaleStore and OnSaleStoreQueryParam:{}", onSaleStoreQueryParam);
        return storeInfoMapper.queryOneOnSaleStoreByCoordinate(onSaleStoreQueryParam);
    }

    @Override
    public List<TStoreInfo> queryOnSaleStoreListByCoordinate(OnSaleStoreQueryParam onSaleStoreQueryParam) {
        logger.debug("queryOnSaleStoreList and OnSaleStoreQueryParam:{}", onSaleStoreQueryParam);
        return storeInfoMapper.queryOnSaleStoreListByCoordinate(onSaleStoreQueryParam);
    }

    @Override
    public List<TStoreInfo> queryNearByStoreList(BigDecimal longitude, BigDecimal latitude, int distance) {
        logger.debug("queryNearByStoreList and longitude:{} \r\n latitude:{}", longitude, latitude);
        return storeInfoMapper.queryNearByStoreList(longitude, latitude, distance);
    }

    @Override
    public PageHelper<TStoreInfo> queryStoreInfoList(PageHelper<TStoreInfo> pageHelper, String companyName, String storeName, String customerMobile) {
        logger.debug("queryStoreInfoList and pageHelper:{} \r\n companyName:{} \r\n storeName:{} \r\n customerMobile:{} \r\n", pageHelper, companyName, storeName, customerMobile);
        Map<String, Object> params = new HashMap<>();
        params.put("companyName", companyName);
        params.put("storeName", storeName);
        if (!StringUtils.isEmpty(customerMobile)) {
            params.put("mobile", customerMobile);
        }
        return pageHelper.setListDates(storeInfoMapper.queryStoreInfoList(pageHelper.getQueryParams(params, storeInfoMapper.queryStoreInfoListCount(params))));

    }

    @Override
    public int openStoreForOutLetStore(long storeId) {
        logger.debug("openStoreForOutLetStore and storeId:{}", storeId);
        return storeInfoMapper.openStoreForOutLetStore(storeId);
    }

    @Override
    public boolean isEffective(long storeId) {
        logger.debug("isEffective and storeId:{}", storeId);
        if (CommonConstant.ADMIN_STOREID == storeId) {
            return true;
        }
        TStoreInfo storeInfo = storeInfoMapper.queryStoreInfo(storeId);
        return Objects.nonNull(storeInfo) && storeInfo.isEffective();
    }


    @Override
    public List<TStoreInfo> queryNearStoreList(BigDecimal longitude, BigDecimal latitude, int distance) {
        logger.debug("queryNearStoreList and longitude:{} \r\n latitude:{}", longitude, latitude);
        return storeInfoMapper.queryNearStoreList(longitude, latitude, distance)
                .stream().map(storeInfo -> storeInfo.buildStoreScore(storeCommentService.queryStoreScore(storeInfo.getId()))).collect(toList())
                .stream().map(storeInfo -> storeInfo.bulidSkus(skuService.queryFiveDataForAttentionStore(storeInfo.getId()))).collect(toList())
                .stream().map(storeInfo ->
                        storeInfo.buildSkusCount(skuService.querySkuCountByStoreId(storeInfo.getId()))
                ).collect(toList());
    }

    @Override
    public StoreReview queryStoreReview(UmsMember customer) {
        logger.debug("queryStoreReview and member :{}", customer);

        if (Objects.isNull(customer)) {
            logger.error("queryStoreReview fail due to member is null....");
            return StoreReview.buildFail("", "");
        }

        // 根据会员的店铺id查询店铺信息
        TStoreInfo storeInfo = this.queryStoreInfo(customerService.selectUserById(customer.getId()).getStoreId());

        logger.info("queryStoreReview and  and storeInfo:{}", storeInfo);

        //如果没有店铺信息，或者开店状态为0，则返回资料未提交完成
        if (Objects.isNull(storeInfo) || "0".equals(storeInfo.getStatus()) || 0 == storeInfo.getId()) {
            logger.info("queryStoreReview :storeInfo is null or status is 0");
            return StoreReview.buildUnFinishData(customer.getUsername());
        }

        //开店状态为2，表示通过审核
        if ("2".equals(storeInfo.getStatus())) {
            logger.info("queryStoreReview : and revire succes....");
            return StoreReview.buildSuccess(customer.getUsername());
        }
        //审核中
        if ("1".equals(storeInfo.getStatus())) {
            logger.info("queryStoreReview and under revirew....");
            return StoreReview.buildUnderReview(customer.getUsername());
        }

        //审核失败
        if ("3".equals(storeInfo.getStatus())) {
            logger.error("queryStoreReview and review fail...");
            return StoreReview.buildFail(customer.getUsername(), storeInfo.getReason());
        }

        // 店铺关闭或失效
        if ("4".equals(storeInfo.getStatus()) || !storeInfo.isEffective()) {
            logger.error("queryStoreReview and store close or not effective...");
            return StoreReview.buildNotEffectiveReview(customer.getUsername());
        }

        return StoreReview.buildFail("", "");
    }

    /**
     * 店铺审核通过后处理相关数据
     *
     * @param storeId 店铺id
     */
    @Transactional
    protected void passStoreAuditDealData(long storeId) {
        //为该用户添加权限
        //  storeRoleService.linkStaffRole(new RoleAndCustomer().getRoleAndCustomer(customerService.queryCustomerIdByStoreId(storeId).getId(), 3));
        //将该店铺下的自定义品牌变为审核通过
        brandService.passCustomBrandByStoreId(storeId);
        //将店铺的签约品牌变为审核通过
        brandApplyService.passBrandAuditByStoreId(storeId);
    }

    /**
     * 删除店铺后处理数据
     *
     * @param storeId 店铺id
     */
    @Transactional
    protected void deleteStoreDealData(long storeId) {
        //删除签约分类
        storeSignedCategoryService.deleteSignedCategory(storeId);
        //删除店铺品牌
        brandApplyService.deleteStoreBrand(storeId);
        //删除自定义品牌
        brandService.batchDeleteCustomBrand(storeId);
    }

    /**
     * 查询店铺信息
     *
     * @param id 店铺信息ID
     * @return 店铺信息
     */
    @Override
    public TStoreInfo selectTStoreInfoById(Long id) {
        return tStoreInfoMapper.selectTStoreInfoById(id);
    }

    /**
     * 查询店铺信息列表
     *
     * @param tStoreInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    public List<TStoreInfo> selectTStoreInfoList(TStoreInfo tStoreInfo) {
        return tStoreInfoMapper.selectTStoreInfoList(tStoreInfo);
    }

    /**
     * 新增店铺信息
     *
     * @param tStoreInfo 店铺信息
     * @return 结果
     */
    @Override
    public int insertTStoreInfo(TStoreInfo tStoreInfo) {
        tStoreInfo.setCreateTime(DateUtils.getNowDate());
        return tStoreInfoMapper.insertTStoreInfo(tStoreInfo);
    }

    /**
     * 修改店铺信息
     *
     * @param tStoreInfo 店铺信息
     * @return 结果
     */
    @Override
    public int updateTStoreInfo(TStoreInfo tStoreInfo) {
        return tStoreInfoMapper.updateTStoreInfo(tStoreInfo);
    }

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的店铺信息ID
     * @return 结果
     */
    @Override
    public int deleteTStoreInfoByIds(Long[] ids) {
        return tStoreInfoMapper.deleteTStoreInfoByIds(ids);
    }

    /**
     * 删除店铺信息信息
     *
     * @param id 店铺信息ID
     * @return 结果
     */
    @Override
    public int deleteTStoreInfoById(Long id) {
        return tStoreInfoMapper.deleteTStoreInfoById(id);
    }
}
