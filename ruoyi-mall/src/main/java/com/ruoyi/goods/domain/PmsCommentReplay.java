package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 评论回复对象 pms_comment_replay
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsCommentReplay extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 评论id  对应pms_comment 表中的id
     */
    @Excel(name = "评论id  对应pms_comment 表中的id")
    private Long commentId;

    /**
     * 回复内容
     */
    @Excel(name = "回复内容")
    private String reply;

    /**
     * 是否显示 0 显示 1 不显示 默认0
     */
    @Excel(name = "是否显示 0 显示 1 不显示 默认0 ")
    private String isShow;

    /**
     * 店铺id 平台为0
     */
    @Excel(name = "店铺id 平台为0")
    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("commentId", getCommentId())
                .append("reply", getReply())
                .append("isShow", getIsShow())
                .append("storeId", getStoreId())
                .append("createTime", getCreateTime())
                .toString();
    }
}
