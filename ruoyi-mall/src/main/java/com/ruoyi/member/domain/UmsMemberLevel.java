package com.ruoyi.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 会员等级对象 ums_member_level
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public class UmsMemberLevel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员等级名称
     */
    @Excel(name = "会员等级名称")
    private String name;

    /**
     * 消费小值（包含该值）
     */
    @Excel(name = "消费小值", readConverterExp = "包=含该值")
    private Long minMoney;

    /**
     * 消费金额最大值 不包含该值
     */
    @Excel(name = "消费金额最大值 不包含该值")
    private Long maxMoney;

    /**
     * 会员折扣
     */
    @Excel(name = "会员折扣")
    private BigDecimal discount;

    /**
     * 删除标记 0 未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /**
     * 构造未知等级实体
     *
     * @return 返回未知等级实体
     */
    public static UmsMemberLevel buildNoLevel() {
        UmsMemberLevel customerLevel = new UmsMemberLevel();
        customerLevel.name = "非会员";
        return customerLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Long minMoney) {
        this.minMoney = minMoney;
    }

    public Long getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Long maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    /**
     * 根据传入的金额判断  是否是当前等级
     *
     * @param money 传入金金额
     * @return 判断规则 [0,100)  前闭后开   包含0  不包含100
     */
    public boolean isCurrentLevl(BigDecimal money) {
        if (Objects.isNull(money)) {
            return false;
        }
        return money.intValue() >= this.minMoney && money.intValue() < this.maxMoney;
    }

    /**
     * 判断传入的会员等级消费金额和当前的会员等级消费金额是否有交集
     *
     * @param customerLevel 会员等级
     * @return 有返回true  没有返回false 传入的值为空  返回false
     */
    public boolean hasIntersection(UmsMemberLevel customerLevel) {
        if (Objects.isNull(customerLevel)) {
            return false;
        }

        // 如果传入的最小值比当前的最大值要大于等于 则肯定没有交集
        if (customerLevel.minMoney >= this.maxMoney) {
            return false;
        }
        if (customerLevel.minMoney >= customerLevel.maxMoney) {
            return true;
        }
        // 如果传入的最小值在当前会员等级最大值和最小值之间(不包含最大值 包含最小值) 则肯定会有交集
        if (customerLevel.minMoney >= this.minMoney && customerLevel.minMoney < this.maxMoney) {
            return true;
        }

        // 如果传入的最小值在比当前的最小值还小 则看传入的最大值
        if (customerLevel.minMoney < this.minMoney) {
            if (customerLevel.maxMoney <= this.minMoney) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("minMoney", getMinMoney())
                .append("maxMoney", getMaxMoney())
                .append("discount", getDiscount())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("delTime", getDelTime())
                .toString();
    }
}
