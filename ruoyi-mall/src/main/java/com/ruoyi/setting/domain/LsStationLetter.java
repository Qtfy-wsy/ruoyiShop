package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 站内信对象 ls_station_letter
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
public class LsStationLetter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;

    /**
     * 站内信标题
     */
    @Excel(name = "站内信标题")
    private String title;

    /**
     * 站内信内容
     */
    @Excel(name = "站内信内容")
    private String content;

    /**
     * 是否已读  0 未读  1 已读 默认0
     */
    @Excel(name = "是否已读  0 未读  1 已读 默认0 ")
    private String isRead;

    /**
     * 删除标记  0 未删除 1 删除 默认0
     */
    private int delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerId", getCustomerId())
                .append("title", getTitle())
                .append("content", getContent())
                .append("isRead", getIsRead())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .toString();
    }
}
