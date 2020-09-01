package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 单品评论下的图片对象 pms_comment_picture
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class PmsCommentPicture extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 评论id
     */
    @Excel(name = "评论id")
    private Long commentId;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("commentId", getCommentId())
                .append("url", getUrl())
                .toString();
    }
}
