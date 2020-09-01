package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsComment;
import com.ruoyi.goods.domain.PmsCommentReplay;
import com.ruoyi.goods.mapper.PmsCommentMapper;
import com.ruoyi.goods.mapper.PmsCommentReplayMapper;
import com.ruoyi.goods.service.IPmsCommentService;
import com.ruoyi.goods.vo.CommentSummarize;
import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.domain.PointSeting;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.integral.service.PointSetingService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单品评论Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsCommentServiceImpl implements IPmsCommentService {
    @Autowired
    private PmsCommentMapper pmsCommentMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsCommentServiceImpl.class);

    /**
     * 注入评论数据库接口
     */
    @Autowired
    private PmsCommentMapper commentMapper;

    /**
     * 注入回复数据库接口
     */
    @Autowired
    private PmsCommentReplayMapper commentReplyMapper;

    /**
     * 注入积分设置服务
     */
     @Autowired
    private PointSetingService pointSetingService;

    /**
     * 注入用户积分服务
     */
      @Autowired
      private CustomerPointService customerPointService;
    @Override
    public PageHelper<PmsComment> queryComments(PageHelper<PmsComment> pageHelper, String customerName, String skuName, String storeName, Long storeId, Long customerId, Long isShow) {
        logger.debug("queryComments and pageHelper:{} \r\n customerName :{} \r\n skuName:{} \r\n storeId:{} \r\n customerId:{} \r\n isShow:{}", pageHelper, customerName, skuName, storeId, customerId, isShow);
        Map<String, Object> params = new HashMap<>();
        params.put("customerName", customerName);
        params.put("skuName", skuName);
        params.put("storeName", storeName);
        params.put("storeId", storeId);
        params.put("customerId", customerId);
        params.put("isShow", isShow);
        return pageHelper.setListDates(commentMapper.queryComments(pageHelper.getQueryParams(params, commentMapper.queryCommentCount(params))));
    }

    @Override
    public PmsComment queryCommentById(Long id, Long storeId, Long customerId, Long isShow) {
        logger.debug("queryCommentById and id :{} \r\n storeId:{} \r\n customerId:{} \r\n isShow:{}", id, storeId, customerId, isShow);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("storeId", storeId);
        map.put("customerId", customerId);
        map.put("isShow", isShow);
        PmsComment comment = commentMapper.queryCommentById(map);
        if (ObjectUtils.isEmpty(comment)) {
            logger.error("queryCommentById error: query null");
            return null;
        }
        comment.setCommentPics(commentMapper.queryCommentPicsByCommentId(id));
        map.put("storeId", CommonConstant.QUERY_WITH_NO_STORE);
        comment.setCommentReplies(commentReplyMapper.queryCommentReply(map));
        return comment;
    }

    @Override
    public int deleteComments(List<Long> ids, Long storeId, Long customerId) {
        logger.debug("deleteComments and ids:{} \r\n storeId:{} \r\n customerId:{}", ids, storeId, customerId);
        if (CollectionUtils.isEmpty(ids)) {
            logger.error("deleteComments fail due to ids is empty....");
            return 0;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", ids);
        map.put("storeId", storeId);
        map.put("customerId", customerId);
        return commentMapper.deleteComments(map);
    }

    @Override
    public int queryCommentCountBySkuId(String skuId) {
        logger.debug("queryCommentCountBySkuId and skuId:{}", skuId);
        return querySkuCommentCount(skuId, "-1");
    }

    @Override
    public int queryGoodCommentCountBySkuId(String skuId) {
        logger.debug("queryGoodCommentCountBySkuId and skuId:{}", skuId);
        return querySkuCommentCount(skuId, "1");
    }

    /**
     * 更新评论显示状态
     *
     * @param id      评论id
     * @param storeId 店铺id
     * @param isShow  显示状态 0显示 1不显示
     * @return 1成功
     */
    @Override
    public int updateCommentIsShow(Long id, Long storeId, String isShow) {
        logger.debug("updateCommentIsShow and id:{} \r\n storeId:{} \r\n isShow:{}", id, storeId, isShow);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("storeId", storeId);
        map.put("isShow", isShow);
        return commentMapper.updateCommentIsShow(map);
    }

    /**
     * 更新回复显示状态
     *
     * @param id      评论id
     * @param storeId 店铺id
     * @param isShow  显示状态 0显示 1不显示
     * @return 1成功
     */
    @Override
    public int updateReplyIsShow(Long id, Long storeId, String isShow) {
        logger.debug("updateReplyIsShow and id:{} \r\n storeId:{} \r\n isShow:{}", id, storeId, isShow);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("storeId", storeId);
        map.put("isShow", isShow);
        return commentReplyMapper.updateIsShow(map);
    }

    /**
     * 新增回复
     *
     * @param commentReply 回复实体
     * @return 1:成功
     */
    @Override
    public int addCommentReply(PmsCommentReplay commentReply) {
        logger.debug("addCommentReply and commentReply:{}", commentReply);
        return commentReplyMapper.insertPmsCommentReplay(commentReply);
    }

    @Transactional
    @Override
    public int addComments(List<PmsComment> comments) {
        logger.debug("addComments and comments:{}", comments);

        if (CollectionUtils.isEmpty(comments)) {
            logger.error("addComments fail due to comments is empty...");
            return 0;
        }

        // 增加单品评分
        comments.stream().forEach(this::addComment);

        PointSeting pointSeting = pointSetingService.queryPointSeting();
        //如果积分赠送活动开启，则增加积分
        if (!ObjectUtils.isEmpty(pointSeting) && pointSeting.isOpen()) {
            customerPointService.addCustomerPoint(CustomerPoint.buildForComment(comments.get(0).getCustomerId(), pointSeting.getCommentPoint()));
        }

        return 1;
    }

    @Override
    public List<PmsComment> queryByOrderIdAndCustomerId(long orderId, long customerId) {
        logger.debug("queryByOrderIdAndCustomerId and orderId:{} \r\n customerId:{}", orderId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);

        // 评论信息
        List<PmsComment> comments = commentMapper.queryByOrderIdAndCustomerId(params);

        if (CollectionUtils.isEmpty(comments)) {
            return comments;
        }
        // 设置评论图片
        comments.stream().forEach(comment -> comment.setCommentPics(commentMapper.queryCommentPicsByCommentId(comment.getId())));

        return comments;
    }


    @Override
    public CommentSummarize queryCommentSummarize(String spuId) {
        logger.debug("queryCommentSummarize and spuId:{}" + spuId);
        return CommentSummarize.build(querySkuCommentCount(spuId, "-1"), querySkuCommentCount(spuId, "1"), querySkuCommentCount(spuId, "2"), querySkuCommentCount(spuId, "3"));
    }

    @Override
    public PageHelper<PmsComment> querySkuComments(PageHelper<PmsComment> pageHelper, String spuId, String type) {
        logger.debug("querySkuComments and pageHelper:{} \r\n skuId :{} \r\n type:{}");

        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("type", type);

        // 单品评论
        List<PmsComment> comments = commentMapper.querySkuComments(pageHelper.getQueryParams(params, this.querySkuCommentCount(spuId, type)));

        // 设置评论的图片和回复
        if (!CollectionUtils.isEmpty(comments)) {
            comments.stream().forEach(comment -> {
                // 设置图片
                comment.setCommentPics(commentMapper.queryCommentPicsByCommentId(comment.getId()));

                Map<String, Object> map = new HashMap<>();
                map.put("id", comment.getId());
                map.put("storeId", CommonConstant.QUERY_WITH_NO_STORE);
                map.put("isShow", CommonConstant.QUERY_WITH_ISSHOW);

                // 设置回复
                comment.setCommentReplies(commentReplyMapper.queryCommentReply(map));


                // 如果是匿名的话把名字去除
                if (comment.isAnonymous()) {
                    comment.setCustomerName("******");
                }

            });
        }

        return pageHelper.setListDates(comments);
    }

    /**
     * 按条件查询商品评价总数
     *
     * @param skuId 单品id
     * @param type  -1 查询单品所有评价  1 单品的好评  2 单品的中评 3 单品的差评
     * @return 返回商品评价总数
     */
    private int querySkuCommentCount(String skuId, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("spuId", skuId);
        params.put("type", type);
        return commentMapper.querySkuCommentCount(params);
    }

    /**
     * 添加单品评分
     *
     * @param comment 单品评分
     */
    private void addComment(PmsComment comment) {

        // 设置评论是否有图片
        comment.setCommentHasPic();

        // 添加评论
        commentMapper.addComment(comment);

        // 设置评论图片的评论id
        comment.setPicCommentId();

        // 新增评论图片
        if (!CollectionUtils.isEmpty(comment.getCommentPics())) {
            commentMapper.addCommentPics(comment.getCommentPics());
        }
    }

    /**
     * 查询单品评论
     *
     * @param id 单品评论ID
     * @return 单品评论
     */
    @Override
    public PmsComment selectPmsCommentById(Long id) {
        return pmsCommentMapper.selectPmsCommentById(id);
    }

    /**
     * 查询单品评论列表
     *
     * @param pmsComment 单品评论
     * @return 单品评论
     */
    @Override
    public List<PmsComment> selectPmsCommentList(PmsComment pmsComment) {
        return pmsCommentMapper.selectPmsCommentList(pmsComment);
    }

    /**
     * 新增单品评论
     *
     * @param pmsComment 单品评论
     * @return 结果
     */
    @Override
    public int insertPmsComment(PmsComment pmsComment) {
        pmsComment.setCreateTime(DateUtils.getNowDate());
        return pmsCommentMapper.insertPmsComment(pmsComment);
    }

    /**
     * 修改单品评论
     *
     * @param pmsComment 单品评论
     * @return 结果
     */
    @Override
    public int updatePmsComment(PmsComment pmsComment) {
        return pmsCommentMapper.updatePmsComment(pmsComment);
    }

    /**
     * 批量删除单品评论
     *
     * @param ids 需要删除的单品评论ID
     * @return 结果
     */
    @Override
    public int deletePmsCommentByIds(Long[] ids) {
        return pmsCommentMapper.deletePmsCommentByIds(ids);
    }

    /**
     * 删除单品评论信息
     *
     * @param id 单品评论ID
     * @return 结果
     */
    @Override
    public int deletePmsCommentById(Long id) {
        return pmsCommentMapper.deletePmsCommentById(id);
    }
}
