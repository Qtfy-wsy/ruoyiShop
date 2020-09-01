package com.ruoyi.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 会员积分详情对象 ums_member_point
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public class UmsMemberPoint extends BaseEntity {
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
     * 积分详细
     */
    @Excel(name = "积分详细")
    private String detail;

    /**
     * 积分类型 1 收入 2支出
     */
    @Excel(name = "积分类型 1 收入 2支出")
    private String type;

    /**
     * 积分纪录来源类型
     * 1 订单使用
     * 2 订单取消
     * 3 操作员修改
     * 4 签到
     * 5 积分商城使用
     * 6 邮箱验证
     * 7 评论
     */
    @Excel(name = "积分纪录来源类型 ")
    private String sourceType;

    /**
     * 积分数量 正数表示增加，负数表示减少
     */
    @Excel(name = "积分数量 正数表示增加，负数表示减少")
    private Long point;

    /**
     * 签到时间，用于防止重复签到
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签到时间，用于防止重复签到", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signData;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Date getSignData() {
        return signData;
    }

    public void setSignData(Date signData) {
        this.signData = signData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerId", getCustomerId())
                .append("detail", getDetail())
                .append("type", getType())
                .append("sourceType", getSourceType())
                .append("point", getPoint())
                .append("createTime", getCreateTime())
                .append("signData", getSignData())
                .toString();
    }
}
