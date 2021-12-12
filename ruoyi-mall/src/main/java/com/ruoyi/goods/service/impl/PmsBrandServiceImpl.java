package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.goods.domain.PmsBrandApply;
import com.ruoyi.goods.mapper.PmsBrandMapper;
import com.ruoyi.goods.service.IPmsBrandApplyService;
import com.ruoyi.goods.service.IPmsBrandService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 品牌Service业务层处理
 *
 * @author 商城
 */
@Service
public class PmsBrandServiceImpl implements IPmsBrandService {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;


    /**
     * 注入品牌数据库接口
     */
    @Autowired
    private PmsBrandMapper brandMapper;

    /**
     * 注入品牌申请服务接口
     */
    @Autowired
    private IPmsBrandApplyService brandApplyService;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsBrandServiceImpl.class);

    @Override
    public int addBrand(PmsBrand brand) {

        if (Objects.isNull(brand)) {
            logger.error("addBrand fail due to brand is null...");
            return 0;
        }

        logger.debug("addBrand and brand {}", brand);

        return brandMapper.addBrand(brand);
    }

    @Override
    public PmsBrand queryBrandById(long id, long storeId) {
        logger.debug("queryBrandById and id {} \r\n storeId:{}", id, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return brandMapper.queryBrandById(params);
    }

    @Override
    public int updateBrand(PmsBrand brand) {
        logger.debug("updateBrand and brand {}", brand);
        return brandMapper.updateBrand(brand);
    }

    @Override
    public int deleteBrand(PmsBrand brand) {
        logger.debug("deleteBrand and brand {}", brand);
        return brandMapper.deleteBrand(brand);
    }

    @Override
    public PageHelper<PmsBrand> queryBrands(PageHelper<PmsBrand> pageHelper, String name, String nickName, long storeId) {
        logger.debug("queryBrands and pageHelper :{} \r\n name : {} \r\n nickName : {} \r\n storeId:{}", pageHelper, name, nickName, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("nickName", nickName);
        params.put("storeId", storeId);
        return pageHelper.setListDates(brandMapper.queryBrands(pageHelper.getQueryParams(params, brandMapper.queryBrandsCount(params))));
    }

    @Transactional
    @Override
    public int batchDeleteBrands(List<PmsBrand> brands) {
        logger.debug("batchDeleteBrands and brands : {}", brands);
        if (CollectionUtils.isEmpty(brands)) {
            logger.error("batchDeleteBrands due to brands is empty....");
            return 0;
        }
        brands.stream().forEach(this::deleteBrand);
        return 1;
    }

    @Override
    public List<PmsBrand> queryAllBrands(long storeId) {
        logger.debug("queryAllBrands..... and storeId:{}", storeId);
        List<PmsBrand> allBrands = new ArrayList<>();
        allBrands.addAll(brandMapper.queryAllBrands(storeId));
        // 店铺申请通过的品牌
        List<PmsBrandApply> brandApplies = brandApplyService.queryAllPassBrand(storeId);
        if (!CollectionUtils.isEmpty(brandApplies)) {
            allBrands.addAll(brandApplies.stream().map(brandApply -> brandApply.getBrand()).collect(Collectors.toList()));
        }
        return allBrands;
    }

    /**
     * 查询所有admin端的品牌
     *
     * @return 品牌集合
     */
    @Override
    public List<PmsBrand> queryAllAdminBrands() {
        logger.debug("queryAllAdminBrands");
        return brandMapper.queryAllBrands(CommonConstant.ADMIN_STOREID);
    }

    /**
     * 批量添加签约品牌
     *
     * @param list 品牌集合
     * @return 添加返回码
     */
    @Override
    public int batchAddCustomBrand(List<PmsBrand> list) {
        logger.debug("queryStoreBrands and list:{}\r\n", list);
        return brandMapper.batchAddCustomBrand(list);
    }

    /**
     * 批量删除签约品牌
     *
     * @param storeId 店铺id
     * @return 删除返回码
     */
    @Override
    public int batchDeleteCustomBrand(long storeId) {
        logger.debug("queryStoreBrands and storeId:{}\r\n", storeId);
        return brandMapper.batchDeleteCustomBrand(storeId);
    }

    /**
     * 查询店铺品牌
     *
     * @param storeId 店铺id
     * @param status  状态
     * @return 品牌集合
     */
    @Override
    public List<PmsBrand> queryStoreBrands(long storeId, String status) {
        logger.debug("queryStoreBrands and storeId:{}\r\n status:{}\r\n", storeId, status);
        PmsBrand brand = new PmsBrand();
        brand.setStatus(status);
        brand.setStoreId(storeId);
        return brandMapper.queryStoreBrands(brand);
    }

    /**
     * 根据店铺id和状态查询签约品牌
     *
     * @param storeId 店铺id
     * @param status  状态
     * @return 签约品牌集合
     */
    @Override
    public List<PmsBrand> queryCustomBrandByStoreIdAndStatus(long storeId, String status) {
        logger.debug("queryCustomBrandByStoreIdAndStatus and storeId:{}\r\n status:{}\r\n", storeId, status);
        PmsBrand brand = new PmsBrand();
        brand.setStoreId(storeId);
        brand.setStatus(status);
        return brandMapper.queryCustomBrandByStoreIdAndStatus(brand);
    }

    /**
     * 分页查询自定义品牌
     *
     * @param pageHelper 分页帮助类
     * @param brandName  品牌名称
     * @param storeName  店铺名称
     * @return 自定义品牌信息
     */
    @Override
    public PageHelper<PmsBrand> queryCustomBrandByStatus(PageHelper<PmsBrand> pageHelper, String brandName, String storeName) {
        logger.debug("queryMySelfBrandsByStatus and pageHelper :{} \r\n and brandName :{} \r\n and storeName :{}", pageHelper, brandName, storeName);
        Map<String, Object> params = new HashMap<>();
        params.put("brandName", brandName);
        params.put("storeName", storeName);
        return pageHelper.setListDates(brandMapper.queryCustomBrandByStatus(pageHelper.getQueryParams(params, brandMapper.queryCustomBrandCount(params))));
    }

    /**
     * 通过自定义品牌审核
     *
     * @param id 自定义品牌id
     * @return 成功返回1，失败返回0
     */
    @Override
    public int passCustomBrandAudit(long id) {
        logger.debug("passMySelfBrandAudit and id :{}", id);
        return brandMapper.passCustomBrandAudit(id);
    }

    /**
     * 批量通过自定义品牌审核
     *
     * @param ids 自定义品牌id数组
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int batchPassCustomBrandAudit(long[] ids) {
        logger.debug("batchPassMySelfBrandAudit and ids :{}", ids);
        return brandMapper.batchPassCustomBrandAudit(ids);
    }

    /**
     * 拒绝自定义品牌审核
     *
     * @param brand 自定义品牌实例
     * @return 成功返回1，失败返回0
     */
    @Override
    public int refuseCustomBrandAudit(PmsBrand brand) {
        logger.debug("refuseMySelfBrandAudit and id :{}", brand);
        return brandMapper.refuseCustomBrandAudit(brand);
    }

    /**
     * 批量拒绝自定义品牌审核
     *
     * @param ids    自定义品牌id数组
     * @param reason 拒绝原因
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int batchRefuseCustomBrandAudit(long[] ids, String reason) {
        logger.debug("batchRefuseMySelfBrandAudit and ids :{} \r\n and reason :{}", ids, reason);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("reason", reason);
        return brandMapper.batchRefuseCustomBrandAudit(params);
    }

    /**
     * 根据店铺id查找其自定义品牌并通过品牌审核
     *
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int passCustomBrandByStoreId(long storeId) {
        logger.debug("passMySelfBrandByStoreId and storeId :{}", storeId);
        return brandMapper.passCustomBrandByStoreId(storeId);
    }

    /**
     * 分页查询店铺下的所有品牌
     *
     * @param pageHelper 品牌分类
     * @param name       品牌名称
     * @return 分页数据
     */
    @Override
    public PageHelper<PmsBrand> queryStoreBrandsForPage(PageHelper<PmsBrand> pageHelper, long storeId, String name) {
        logger.debug("queryStoreBrandsForPage and pageHelper :{} \r\n and name :{}", pageHelper, name);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("storeId", storeId);
        return pageHelper.setListDates(brandMapper.queryStoreBrandsForPage(pageHelper.getQueryParams(params, brandMapper.queryStoreBrandsForPageCount(params))));
    }

    @Override
    public List<PmsBrand> queryBrandToBeAudit(PmsBrand pmsBrand) {
        return brandMapper.queryBrandToBeAudit(pmsBrand);
    }

    @Override
    public List<PmsBrand> queryMySelfBrands(PmsBrand pmsBrand) {
        return brandMapper.queryMySelfBrands(pmsBrand);
    }

    /**
     * 查询品牌
     *
     * @param id 品牌ID
     * @return 品牌
     */
    @Override
    public PmsBrand selectPmsBrandById(Long id) {
        return pmsBrandMapper.selectPmsBrandById(id);
    }

    /**
     * 查询品牌列表
     *
     * @param pmsBrand 品牌
     * @return 品牌
     */
    @Override
    public List<PmsBrand> selectPmsBrandList(PmsBrand pmsBrand) {
        return pmsBrandMapper.selectPmsBrandList(pmsBrand);
    }

    /**
     * 新增品牌
     *
     * @param pmsBrand 品牌
     * @return 结果
     */
    @Override
    public int insertPmsBrand(PmsBrand pmsBrand) {
        pmsBrand.setCreateTime(DateUtils.getNowDate());
        return pmsBrandMapper.insertPmsBrand(pmsBrand);
    }

    /**
     * 修改品牌
     *
     * @param pmsBrand 品牌
     * @return 结果
     */
    @Override
    public int updatePmsBrand(PmsBrand pmsBrand) {
        return pmsBrandMapper.updatePmsBrand(pmsBrand);
    }

    /**
     * 批量删除品牌
     *
     * @param ids 需要删除的品牌ID
     * @return 结果
     */
    @Override
    public int deletePmsBrandByIds(Long[] ids) {
        return pmsBrandMapper.deletePmsBrandByIds(ids);
    }

    /**
     * 删除品牌信息
     *
     * @param id 品牌ID
     * @return 结果
     */
    @Override
    public int deletePmsBrandById(Long id) {
        return pmsBrandMapper.deletePmsBrandById(id);
    }
}
