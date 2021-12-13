package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsBrandApply;
import com.ruoyi.goods.mapper.PmsBrandApplyMapper;
import com.ruoyi.goods.service.IPmsBrandApplyService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 品牌申请Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsBrandApplyServiceImpl implements IPmsBrandApplyService {
    @Autowired
    private PmsBrandApplyMapper pmsBrandApplyMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsBrandApplyServiceImpl.class);

    /**
     * 品牌申请数据库接口
     */
    @Autowired
    private PmsBrandApplyMapper brandApplyMapper;

    @Override
    public List<PmsBrandApply> queryAllPassBrand(long storeId) {
        logger.debug("queryAllPassBrand and storeId:{}", storeId);
        return brandApplyMapper.queryAllPassBrand(storeId);
    }

    /**
     * 分页查询待审核的品牌
     *
     * @param pageHelper 分页帮助类
     * @param name       品牌名称
     * @param storeName  店铺名称
     * @return 待审核的品牌
     */
    @Override
    public PageHelper<PmsBrandApply> queryBrandToBeAudit(PageHelper<PmsBrandApply> pageHelper, String name, String storeName) {
        logger.debug("queryBrandToBeAudit and pageHelper :{} \r\n and name :{} \r\n and nickName :{} \r\n and storeId :{}", pageHelper, name, storeName);
        Map<String, Object> params = new HashMap<>();
        params.put("brandName", name);
        params.put("storeName", storeName);
        return pageHelper.setListDates(brandApplyMapper.queryBrandToBeAudit(pageHelper.getQueryParams(params, brandApplyMapper.queryBrandToBeAuditCount(params))));
    }

    /**
     * 通过品牌审核
     *
     * @param id 品牌审核id
     * @return 成功返回1，失败返回0
     */
    @Override
    public int passBrandAudit(long id) {
        logger.debug("passBrandAudit and id :{}", id);
        return brandApplyMapper.passBrandAudit(id);
    }

    /**
     * 批量通过品牌审核
     *
     * @param ids 品牌审核id数组
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int batchPassBrandAudit(long[] ids) {
        logger.debug("batchPassBrandAudit and ids :{}", ids);
        return brandApplyMapper.batchPassBrandAudit(ids);
    }

    /**
     * 拒绝品牌审核
     *
     * @param brandApply 商品审核实例
     * @return 成功返回1，失败返回0
     */
    @Override
    public int refuseBrandAudit(PmsBrandApply brandApply) {
        logger.debug("refuseBrandAudit and brandApply :{}", brandApply);
        return brandApplyMapper.refuseBrandAudit(brandApply);
    }

    /**
     * 批量拒绝品牌审核
     *
     * @param ids    品牌审核id数组
     * @param reason 拒绝原因
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int batchRefuseBrandAudit(long[] ids, String reason) {
        logger.debug("batchRefuseBrandAudit and ids :{} and reason :{}", ids, reason);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("reason", reason);
        return brandApplyMapper.batchRefuseBrandAudit(params);
    }

    /**
     * 根据店铺id查询品牌并通过审核
     *
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int passBrandAuditByStoreId(long storeId) {
        logger.debug("passBrandAuditByStoreId and storeId :{}", storeId);
        return brandApplyMapper.passBrandAuditByStoreId(storeId);
    }

    /**
     * 开店-添加店铺品牌
     *
     * @param list 品牌集合
     * @return 添加返回码
     */
    @Override
    public int addStoreBrand(List<PmsBrandApply> list) {
        logger.debug("addStoreBrand and list:{}", list);
        if (CollectionUtils.isEmpty(list)) {
            logger.error("addStoreBrand fail: list is empty");
            return 0;
        }
        return brandApplyMapper.addStoreBrand(list);
    }

    /**
     * 处理添加签约品牌数据
     *
     * @param brandApply 实体类
     * @param storeId    店铺id
     * @return 添加返回码
     */
    @Override
    public int doAddStoreBrand(PmsBrandApply brandApply, Long storeId) {
        logger.debug("doAddStoreBrand and brandApply:{}\r\n storeId:{}", brandApply, storeId);
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        List<Long> idList = new ArrayList<>();
        idList.add(brandApply.getBrandId());
        map.put("idArrays", idList);
        if (!Objects.isNull(brandApplyMapper.queryStoreBrandByStoreIdAndBrandId(map))) {
            logger.error("doAddStoreBrand error due to isnull");
            return -1;
        }
        List<PmsBrandApply> list = new ArrayList<>();
        brandApply.setStoreId(storeId);
        list.add(brandApply);
        return addStoreBrand(list);
    }

    /**
     * 删除店铺品牌
     *
     * @param storeId 店铺id
     * @return 删除返回码
     */
    @Override
    public int deleteStoreBrand(long storeId) {
        logger.debug("deleteStoreBrand and storeId:{}", storeId);
        return brandApplyMapper.deleteStoreBrand(storeId);
    }

    /**
     * 根据店铺id和品牌id删除签约品牌
     *
     * @param storeId 删除条件
     * @param ids     品牌id
     * @return 删除返回码
     */
    @Override
    public int deleteStoreBrandByStoreIdAndBrandId(long storeId, long[] ids) {
        logger.debug("deleteStoreBrand and storeId:{}\r\n ids:{}", storeId, ids);
        Map<String, Object> param = new HashMap<>();
        param.put("storeId", storeId);
        param.put("idArrays", ids);
        return brandApplyMapper.deleteStoreBrandByStoreIdAndBrandId(param);
    }

    /**
     * 查询品牌申请
     *
     * @param id 品牌申请ID
     * @return 品牌申请
     */
    @Override
    public PmsBrandApply selectPmsBrandApplyById(Long id) {
        return pmsBrandApplyMapper.selectPmsBrandApplyById(id);
    }

    /**
     * 查询品牌申请列表
     *
     * @param pmsBrandApply 品牌申请
     * @return 品牌申请
     */
    @Override
    public List<PmsBrandApply> selectPmsBrandApplyList(PmsBrandApply pmsBrandApply) {
        return pmsBrandApplyMapper.selectPmsBrandApplyList(pmsBrandApply);
    }

    /**
     * 新增品牌申请
     *
     * @param pmsBrandApply 品牌申请
     * @return 结果
     */
    @Override
    public int insertPmsBrandApply(PmsBrandApply pmsBrandApply) {
        pmsBrandApply.setCreateTime(DateUtils.getNowDate());
        return pmsBrandApplyMapper.insertPmsBrandApply(pmsBrandApply);
    }

    /**
     * 修改品牌申请
     *
     * @param pmsBrandApply 品牌申请
     * @return 结果
     */
    @Override
    public int updatePmsBrandApply(PmsBrandApply pmsBrandApply) {
        return pmsBrandApplyMapper.updatePmsBrandApply(pmsBrandApply);
    }

    /**
     * 批量删除品牌申请
     *
     * @param ids 需要删除的品牌申请ID
     * @return 结果
     */
    @Override
    public int deletePmsBrandApplyByIds(Long[] ids) {
        return pmsBrandApplyMapper.deletePmsBrandApplyByIds(ids);
    }

    /**
     * 删除品牌申请信息
     *
     * @param id 品牌申请ID
     * @return 结果
     */
    @Override
    public int deletePmsBrandApplyById(Long id) {
        return pmsBrandApplyMapper.deletePmsBrandApplyById(id);
    }
}
