package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsComment;
import com.ruoyi.goods.domain.PmsCommentPicture;

import java.util.List;
import java.util.Map;

/**
 * 单品评论Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsCommentMapper {
    /**
     * 查询单品评论
     *
     * @param id 单品评论ID
     * @return 单品评论
     */
    public PmsComment selectPmsCommentById(Long id);

    /**
     * 查询评论总数
     *
     * @param params 查询参数
     * @return 返回评论总数
     */

    int queryCommentCount(Map<String, Object> params);

    /**
     * 查询评论
     *
     * @param params 查询参数
     * @return 返回评论数据
     */

    List<PmsComment> queryComments(Map<String, Object> params);

    /**
     * 根据评论id查询评论信息
     *
     * @param map 评论id 店铺id
     * @return 返回评论信息
     */

    PmsComment queryCommentById(Map map);

    /**
     * 根据评论id查询评论图片
     *
     * @param commentId 评论id
     * @return 返回评论图片
     */

    List<PmsCommentPicture> queryCommentPicsByCommentId(long commentId);

    /**
     * 删除评论信息
     *
     * @param map 评论id集合 店铺id
     * @return 成功返回>1 失败返回0
     */

    int deleteComments(Map map);

    /**
     * 更新评论显示状态
     *
     * @param map isShow 是否显示 storeId 店铺id id 评论id
     * @return 1:成功
     */

    int updateCommentIsShow(Map map);

    /**
     * 增加评分
     *
     * @param comment 评分实体
     * @return 成功>0 失败0
     */

    int addComment(PmsComment comment);

    /**
     * 新增评论图片
     *
     * @param commentPics 评论图片
     * @return 成功>0 失败返回0
     */

    int addCommentPics(List<PmsCommentPicture> commentPics);

    /**
     * 查询用户订单下的评论信息
     *
     * @param params 参数
     * @return 返回用户订单下的评论信息
     */

    List<PmsComment> queryByOrderIdAndCustomerId(Map<String, Object> params);

    /**
     * 按条件查询商品评价总数
     *
     * @param params 查询条件
     * @return 返回商品评价总数
     */

    int querySkuCommentCount(Map<String, Object> params);

    /**
     * 分页查询单品的评论
     *
     * @param params 查询参数
     * @return 返回单品的评论
     */

    List<PmsComment> querySkuComments(Map<String, Object> params);

    /**
     * 查询单品评论列表
     *
     * @param pmsComment 单品评论
     * @return 单品评论集合
     */
    public List<PmsComment> selectPmsCommentList(PmsComment pmsComment);

    /**
     * 新增单品评论
     *
     * @param pmsComment 单品评论
     * @return 结果
     */
    public int insertPmsComment(PmsComment pmsComment);

    /**
     * 修改单品评论
     *
     * @param pmsComment 单品评论
     * @return 结果
     */
    public int updatePmsComment(PmsComment pmsComment);

    /**
     * 删除单品评论
     *
     * @param id 单品评论ID
     * @return 结果
     */
    public int deletePmsCommentById(Long id);

    /**
     * 批量删除单品评论
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsCommentByIds(Long[] ids);
}
