package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsComment;
import com.ruoyi.goods.domain.PmsCommentReplay;
import com.ruoyi.goods.vo.CommentSummarize;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 单品评论Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsCommentService {
    /**
     * 分页查询评论信息
     *
     * @param pageHelper   分页帮助类
     * @param customerName 发表人名称
     * @param skuName      单品名称
     * @return 返回评论信息
     */
    PageHelper<PmsComment> queryComments(PageHelper<PmsComment> pageHelper, String customerName, String skuName, String storeName, Long storeId, Long customerId, Long isShow);

    /**
     * 根据id查询评论信息
     *
     * @param id         评论id
     * @param storeId    店铺id
     * @param customerId 用户id
     * @param isShow     是否显示 0显示
     * @return 返回评论信息
     */
    PmsComment queryCommentById(Long id, Long storeId, Long customerId, Long isShow);

    /**
     * 删除评论
     *
     * @param ids        评论id集合
     * @param storeId    店铺id
     * @param customerId 用户id
     * @return 成功返回>1 失败返回0
     */
    int deleteComments(List<Long> ids, Long storeId, Long customerId);

    /**
     * 根据skuId查询评论总数(用户端)
     *
     * @param skuId 单品id
     * @return 评论总数
     */
    int queryCommentCountBySkuId(String skuId);

    /**
     * 根据单品id查询好评数(用户端)
     *
     * @param skuId 单品id
     * @return 返回单品的好评数
     */
    int queryGoodCommentCountBySkuId(String skuId);

    /**
     * 更新评论显示状态
     *
     * @param id      评论id
     * @param storeId 店铺id
     * @param isShow  显示状态 0显示 1不显示
     * @return 1:成功
     */
    int updateCommentIsShow(Long id, Long storeId, String isShow);

    /**
     * 更新回复显示状态
     *
     * @param id      回复id
     * @param storeId 店铺id
     * @param isShow  显示状态 0显示 1不显示
     * @return 1:成功
     */
    int updateReplyIsShow(Long id, Long storeId, String isShow);

    /**
     * 新增回复
     *
     * @param commentReply 回复实体
     * @return 1:成功
     */
    int addCommentReply(PmsCommentReplay commentReply);

    /**
     * 添加评论
     *
     * @param comments 评论实体
     * @return 成功>0 失败0
     */
    int addComments(List<PmsComment> comments);

    /**
     * 根据订单id和用户id查询评价信息
     *
     * @param orderId    订单id
     * @param customerId 用户id
     * @return 返回用户订单下的评价信息
     */
    List<PmsComment> queryByOrderIdAndCustomerId(long orderId, long customerId);

    /**
     * 查询商品评价概括
     *
     * @param skuId 单品id
     * @return 返回商品评价概括
     */
    CommentSummarize queryCommentSummarize(String skuId);

    /**
     * 查询商品评论
     *
     * @param pageHelper 分页帮助类
     * @param type       类型 -1 全部 1 好评 2 中评 3 差评
     * @param skuId      单品id
     * @return 返回商品评论
     */
    PageHelper<PmsComment> querySkuComments(PageHelper<PmsComment> pageHelper, String spuId, String type);

    /**
     * 查询单品评论
     *
     * @param id 单品评论ID
     * @return 单品评论
     */
    public PmsComment selectPmsCommentById(Long id);

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
     * 批量删除单品评论
     *
     * @param ids 需要删除的单品评论ID
     * @return 结果
     */
    public int deletePmsCommentByIds(Long[] ids);

    /**
     * 删除单品评论信息
     *
     * @param id 单品评论ID
     * @return 结果
     */
    public int deletePmsCommentById(Long id);
}
