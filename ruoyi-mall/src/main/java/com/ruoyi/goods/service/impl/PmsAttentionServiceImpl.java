package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsAttention;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.mapper.PmsAttentionMapper;
import com.ruoyi.goods.service.IPmsAttentionService;
import com.ruoyi.goods.service.IPmsCommentService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品关注Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsAttentionServiceImpl implements IPmsAttentionService {
    @Autowired
    private PmsAttentionMapper pmsAttentionMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsAttentionServiceImpl.class);

    /**
     * 商品关注数据库接口
     */
    @Autowired
    private PmsAttentionMapper attentionMapper;

    /**
     * 注入评论service
     */
    @Autowired
    private IPmsCommentService commentService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;


    @Override
    public PageHelper<PmsAttention> queryAttentions(PageHelper<PmsAttention> pageHelper, long customerId) {
        logger.debug("queryAttentions and pageHelper:{} \r\n customerId:{}", pageHelper, customerId);
        return queryAttentionsCommon(pageHelper, customerId);
    }

    @Override
    public PageHelper<PmsAttention> queryAttentionsForCustomerCentre(PageHelper<PmsAttention> pageHelper, long customerId) {
        PageHelper<PmsAttention> pageHelperData = queryAttentionsCommon(pageHelper, customerId);
        pageHelperData.getList().forEach((PmsAttention attention) -> {
            attention.getSku().setId(attention.getSkuId());
            skuService.setSkuDetail(attention.getSku(), PmsSkuItem.BATCH);
            attention.setCommentCount(commentService.queryCommentCountBySkuId(attention.getSkuId()));
            attention.setGoodCommentCount(commentService.queryGoodCommentCountBySkuId(attention.getSkuId()));
        });
        return pageHelperData;
    }

    @Override
    public int cancelAttention(String skuId, long customerId) {
        return attentionMapper.cancelAttention(new PmsAttention().getAttentionForCancelAttention(skuId, customerId));
    }

    @Override
    public int addAttention(long customerId, String skuId) {
        logger.debug("addAttention and customerId:{} \r\n skuId:{}", customerId, skuId);

        //查询单品信息
        PmsSku sku = skuService.querySkuById(skuId);

        if (Objects.isNull(sku)) {
            logger.error("addAttention fail due to sku is not exist...");
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("skuId", skuId);

        int count = attentionMapper.queryCustomerAttenion(params);
        if (count > 0) {
            logger.error("addAttention fail due to already been attentioned...");
            return -1;
        }
        attentionMapper.addAttention(PmsAttention.buildForAdd(customerId, skuId, sku.getSpuId()));
        return this.queryAttentionCount(customerId);
    }

    @Override
    public boolean hasAttention(long customerId, String skuId) {
        logger.debug("hasAttention and customerId:{} \r\n skuId:{}", customerId, skuId);

        if (CommonConstant.NO_LOGIN_CUSTOMERID == customerId) {
            return false;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("skuId", skuId);
        return attentionMapper.queryCustomerAttenion(params) != 0;
    }

    @Override
    public int queryAttentionCount(long customerId) {
        logger.debug("queryAttentionCount and customerId:{}", customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return attentionMapper.queryAttentionCount(params);
    }

    /**
     * 分页查询商品关注
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @return 返回商品关注
     */
    private PageHelper<PmsAttention> queryAttentionsCommon(PageHelper<PmsAttention> pageHelper, long customerId) {
        logger.debug("queryAttentionsCommon and pageHelper:{} \r\n customerId:{}", pageHelper, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return pageHelper.setListDates(attentionMapper.queryAttentions(pageHelper.getQueryParams(params, this.queryAttentionCount(customerId))));
    }

    /**
     * 查询商品关注
     *
     * @param id 商品关注ID
     * @return 商品关注
     */
    @Override
    public PmsAttention selectPmsAttentionById(Long id) {
        return pmsAttentionMapper.selectPmsAttentionById(id);
    }

    /**
     * 查询商品关注列表
     *
     * @param pmsAttention 商品关注
     * @return 商品关注
     */
    @Override
    public List<PmsAttention> selectPmsAttentionList(PmsAttention pmsAttention) {
        return pmsAttentionMapper.selectPmsAttentionList(pmsAttention);
    }

    /**
     * 新增商品关注
     *
     * @param pmsAttention 商品关注
     * @return 结果
     */
    @Override
    public int insertPmsAttention(PmsAttention pmsAttention) {
        pmsAttention.setCreateTime(DateUtils.getNowDate());
        return pmsAttentionMapper.insertPmsAttention(pmsAttention);
    }

    /**
     * 修改商品关注
     *
     * @param pmsAttention 商品关注
     * @return 结果
     */
    @Override
    public int updatePmsAttention(PmsAttention pmsAttention) {
        return pmsAttentionMapper.updatePmsAttention(pmsAttention);
    }

    /**
     * 批量删除商品关注
     *
     * @param ids 需要删除的商品关注ID
     * @return 结果
     */
    @Override
    public int deletePmsAttentionByIds(Long[] ids) {
        return pmsAttentionMapper.deletePmsAttentionByIds(ids);
    }

    /**
     * 删除商品关注信息
     *
     * @param id 商品关注ID
     * @return 结果
     */
    @Override
    public int deletePmsAttentionById(Long id) {
        return pmsAttentionMapper.deletePmsAttentionById(id);
    }
}
