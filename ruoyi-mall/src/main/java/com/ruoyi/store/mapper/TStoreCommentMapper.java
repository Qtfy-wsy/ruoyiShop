package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreComment;
import com.ruoyi.store.vo.StoreScore;

import java.util.List;
import java.util.Map;

/**
 * 店铺评论Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreCommentMapper {
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
     * 查询用户订单下店铺的评论
     *
     * @param params 参数
     * @return 返回用户订单下店铺的评论
     */
    TStoreComment queryByOrderIdAndCutomerId(Map<String, Object> params);

    /**
     * 查询店铺评分
     *
     * @param storeId 店铺id
     * @return 店铺评分实体
     */
    StoreScore queryStoreScore(Long storeId);

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
     * 删除店铺评论
     *
     * @param id 店铺评论ID
     * @return 结果
     */
    public int deleteTStoreCommentById(Long id);

    /**
     * 批量删除店铺评论
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreCommentByIds(Long[] ids);
}
