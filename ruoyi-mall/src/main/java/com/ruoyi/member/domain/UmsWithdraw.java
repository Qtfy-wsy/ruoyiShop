package com.ruoyi.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现记录对象 ums_withdraw
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@Data
public class UmsWithdraw extends BaseEntity {
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
     * 提现金额
     */
    @Excel(name = "提现金额")
    private BigDecimal money;

    /**
     * 状态 0 申请  1 审核通过 2 拒绝 3 已打款
     */
    @Excel(name = "状态 0 申请  1 审核通过 2 拒绝 3 已打款")
    private String status;

    /**
     * 流水号
     */
    @Excel(name = "流水号")
    private String tradeNo;

    /**
     * 支付宝账号
     */
    @Excel(name = "支付宝账号")
    private String account;

    /**
     * 支付宝姓名
     */
    @Excel(name = "支付宝姓名")
    private String name;

    /**
     * 打款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "打款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payingTime;

    /**
     * 构建customerId
     *
     * @param customerId 用户id
     * @return 提现记录实体
     */
    public UmsWithdraw buildCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * 校验参数
     *
     * @return 校验通过返回true 否则返回false
     */
    public boolean checkParams() {
        return !(ObjectUtils.isEmpty(money) || ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(account)) && checkMoney();
    }

    /**
     * 校验金额
     *
     * @return 大于0返回true 否则返回false
     */
    @JsonIgnore
    public boolean checkMoney() {
        return new BigDecimal(0).compareTo(this.money) < 0;
    }


    /**
     * 提现记录查询参数类
     */
    @Data
    @ApiModel(description = "提现记录查询参数类")
    public static class QueryCriteria {

        /**
         * 用户id
         */
        @ApiModelProperty(value = "用户id")
        private long customerId = -1;

        /**
         * 开始时间
         */
        @ApiModelProperty(value = "开始时间")
        private String startTime;

        /**
         * 结束时间
         */
        @ApiModelProperty(value = "结束时间")
        private String endTime;

        /**
         * 支付宝姓名
         */
        @ApiModelProperty(value = "支付宝姓名")
        private String name;

        /**
         * 状态
         */
        @ApiModelProperty(value = "状态")
        private String status;

        /**
         * 交易流水号
         */
        @ApiModelProperty(value = "交易流水号")
        private String tradeNo;

        /**
         * 用户手机号
         */
        @ApiModelProperty(value = "用户手机号")
        private String mobile;

        /**
         * 构建查询参数实体
         *
         * @param customerId 用户id
         * @return 查询参数实体
         */
        public static QueryCriteria buildForSite(long customerId,String status) {
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.setStatus(status);
            queryCriteria.setCustomerId(customerId);
            return queryCriteria;
        }

        /**
         * 获取查询参数
         */
        public Map<String, Object> getQueryMap() {
            Map<String, Object> params = new HashMap<>();
            params.put("customerId", customerId);
            if (!StringUtils.isEmpty(startTime)) {
                params.put("startTime", startTime);
            }
            if (!StringUtils.isEmpty(endTime)) {
                params.put("endTime", endTime);
            }
            if (!StringUtils.isEmpty(name)) {
                params.put("name", name);
            }
            if (!StringUtils.isEmpty(status)) {
                params.put("status", status);
            }
            if (!StringUtils.isEmpty(tradeNo)) {
                params.put("tradeNo", tradeNo);
            }
            if (!StringUtils.isEmpty(mobile)) {
                params.put("mobile", mobile);
            }
            return params;
        }

    }

}
