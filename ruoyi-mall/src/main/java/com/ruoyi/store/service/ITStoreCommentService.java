package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreComment;
import com.ruoyi.store.vo.StoreScore;

import java.util.List;

/**
 * 店铺评论Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreCommentService {
    /**
     * 查询用户订单的店铺评论
     *
     * @param orderId    订单id
     * @param customerId 用户id
     * @return 返回用户订单的店铺评论
     */
    TStoreComment queryByOrderIdAndCutomerId(long orderId, long customerId);

    /**
     * 查询店铺评分
     *
     * @param storeId 店铺id
     * @return 店铺评分实体
     */
    StoreScore queryStoreScore(Long storeId);

    /**
     * 查询店铺评分(附加店铺信息，关注人数)
     *
     * @param storeId 店铺id
     * @return 店铺评分实体
     */
    StoreScore queryStoreScoreWithStoreInfo(Long storeId);

    /**
     * 查询店铺评论
     *
     * @param id 店铺评论ID
     * @return 店铺评论
     */
    public TStoreComment selectTStoreCommentById(Long id);

    /**
     * 查询店铺评论列表
     *
     * @param tStoreComment 店铺评论
     * @return 店铺评论集合
     */
    public List<TStoreComment> selectTStoreCommentList(TStoreComment tStoreComment);

    /**
     * 新增店铺评论
     *
     * @param tStoreComment 店铺评论
     * @return 结果
     */
    public int insertTStoreComment(TStoreComment tStoreComment);

    /**
     * 修改店铺评论
     *
     * @param tStoreComment 店铺评论
     * @return 结果
     */
    public int updateTStoreComment(TStoreComment tStoreComment);

    /**
     * 批量删除店铺评论
     *
     * @param ids 需要删除的店铺评论ID
     * @return 结果
     */
    public int deleteTStoreCommentByIds(Long[] ids);

    /**
     * 删除店铺评论信息
     *
     * @param id 店铺评论ID
     * @return 结果
     */
    public int deleteTStoreCommentById(Long id);
}
