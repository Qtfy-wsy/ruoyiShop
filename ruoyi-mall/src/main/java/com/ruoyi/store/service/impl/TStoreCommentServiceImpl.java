package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreComment;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.mapper.TStoreCommentMapper;
import com.ruoyi.store.service.AttentionStoreService;
import com.ruoyi.store.service.ITStoreCommentService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.store.vo.StoreScore;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 店铺评论Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreCommentServiceImpl implements ITStoreCommentService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreCommentServiceImpl.class);
    @Autowired
    private TStoreCommentMapper tStoreCommentMapper;

    /**
     * 注入店铺服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入店铺关注服务接口
     */
    @Autowired
    private AttentionStoreService attentionStoreService;

    /**
     * 查询店铺评论
     *
     * @param id 店铺评论ID
     * @return 店铺评论
     */
    @Override
    public TStoreComment selectTStoreCommentById(Long id) {
        return tStoreCommentMapper.selectTStoreCommentById(id);
    }

    /**
     * 查询店铺评论列表
     *
     * @param tStoreComment 店铺评论
     * @return 店铺评论
     */
    @Override
    public List<TStoreComment> selectTStoreCommentList(TStoreComment tStoreComment) {
        return tStoreCommentMapper.selectTStoreCommentList(tStoreComment);
    }

    @Override
    public TStoreComment queryByOrderIdAndCutomerId(long orderId, long customerId) {
        logger.debug("queryByOrderIdAndCutomerId and orderId:{} \r\n customerId:{}", orderId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);

        // 店铺评论
        TStoreComment storeComment = tStoreCommentMapper.queryByOrderIdAndCutomerId(params);

        if (Objects.isNull(storeComment)) {
            return storeComment;
        }

        // 店铺信息
        TStoreInfo storeInfo = storeInfoService.queryStoreInfo(storeComment.getStoreId());

        if (Objects.nonNull(storeInfo)) {
            storeComment.setStoreName(storeInfo.getStoreName());
        }

        return storeComment;
    }

    @Override
    public StoreScore queryStoreScore(Long storeId) {
        logger.debug("queryStoreScore and storeId:{}", storeId);
        if (ObjectUtils.isEmpty(storeId)) {
            logger.error("queryStoreScore fail: storeId is null");
            return null;
        }
        return tStoreCommentMapper.queryStoreScore(storeId);
    }

    @Override
    public StoreScore queryStoreScoreWithStoreInfo(Long storeId) {
        logger.debug("queryStoreScoreWithStoreInfo and storeId:{}", storeId);
        if (ObjectUtils.isEmpty(storeId)) {
            logger.error("queryStoreScoreWithStoreInfo fail: storeId is null");
            return null;
        }
        StoreScore storeScore = tStoreCommentMapper.queryStoreScore(storeId);
        if (ObjectUtils.isEmpty(storeScore)) {
            storeScore = new StoreScore();
        }
        //设置店铺信息
        TStoreInfo storeInfo = storeInfoService.queryStoreInfo(storeId);
        //设置店铺关注人数
        if (!ObjectUtils.isEmpty(storeInfo)) {
            //如果平台自营，设置默认关注人数
            if (CommonConstant.ADMIN_STOREID == storeId) {
                storeInfo.setFollowNum(CommonConstant.ADMIN_FOLLOW_NUM);
                //设置客服qq
                /*CustomerService customerService = customerServiceService.queryCustomerService();
                if (Objects.nonNull(customerService) && !CollectionUtils.isEmpty(customerService.getCustomerServiceInfo())) {
                    storeInfo.setServiceQQ(customerService.getCustomerServiceInfo().stream().findFirst().orElse(new CustomerServiceInfo()).getQq());
                }*/
            } else {
                storeInfo.setFollowNum(attentionStoreService.queryNumByStore(storeId));
            }
        }
        return storeScore.buildStoreInfo(storeInfo);
    }

    /**
     * 新增店铺评论
     *
     * @param tStoreComment 店铺评论
     * @return 结果
     */
    @Override
    public int insertTStoreComment(TStoreComment tStoreComment) {
        tStoreComment.setCreateTime(DateUtils.getNowDate());
        return tStoreCommentMapper.insertTStoreComment(tStoreComment);
    }

    /**
     * 修改店铺评论
     *
     * @param tStoreComment 店铺评论
     * @return 结果
     */
    @Override
    public int updateTStoreComment(TStoreComment tStoreComment) {
        return tStoreCommentMapper.updateTStoreComment(tStoreComment);
    }

    /**
     * 批量删除店铺评论
     *
     * @param ids 需要删除的店铺评论ID
     * @return 结果
     */
    @Override
    public int deleteTStoreCommentByIds(Long[] ids) {
        return tStoreCommentMapper.deleteTStoreCommentByIds(ids);
    }

    /**
     * 删除店铺评论信息
     *
     * @param id 店铺评论ID
     * @return 结果
     */
    @Override
    public int deleteTStoreCommentById(Long id) {
        return tStoreCommentMapper.deleteTStoreCommentById(id);
    }
}
