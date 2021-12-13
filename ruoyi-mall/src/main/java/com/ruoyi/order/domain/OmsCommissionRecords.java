package com.ruoyi.order.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 佣金记录对象 oms_commission_records
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Data
public class OmsCommissionRecords extends BaseEntity {
    public static final String WITHDRAW_REMARK = "提现";
    public static final String WITHDRAW_FAIL_REMARK = "提现失败退回";
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
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal money;
    /**
     * 类型  0 收入 1 支出
     */
    @Excel(name = "类型  0 收入 1 支出")
    private String type;

    /**
     * 构造减少佣金记录
     *
     * @param customerId 用户id
     * @param money      佣金金额
     * @return 佣金记录
     */
    public static OmsCommissionRecords buildForExpend(long customerId, BigDecimal money, String remark) {
        OmsCommissionRecords commissionRecord = new OmsCommissionRecords();
        commissionRecord.setCustomerId(customerId);
        commissionRecord.setType("1");
        commissionRecord.setRemark(remark);
        commissionRecord.setMoney(money);
        return commissionRecord;
    }

    /**
     * 构造增加佣金记录
     *
     * @param customerId 用户id
     * @param money      佣金金额
     * @param remark     备注
     * @param orderCode  订单code
     * @return 佣金记录
     */
    public static OmsCommissionRecords buildForAdd(long customerId, BigDecimal money, String remark, String orderCode) {
        OmsCommissionRecords commissionRecord = new OmsCommissionRecords();
        commissionRecord.setCustomerId(customerId);
        commissionRecord.setType("0");
        if (StringUtils.isEmpty(orderCode)) {
            commissionRecord.setRemark(remark);
        } else {
            commissionRecord.setRemark("分销订单号:" + orderCode);
        }
        commissionRecord.setMoney(money);
        return commissionRecord;
    }

    /**
     * 构造提现失败退回佣金记录
     *
     * @param customerId 用户id
     * @param money      佣金金额
     * @return 佣金记录
     */
    public static OmsCommissionRecords buildForWithdrawFail(long customerId, BigDecimal money) {
        OmsCommissionRecords commissionRecord = new OmsCommissionRecords();
        commissionRecord.setCustomerId(customerId);
        commissionRecord.setType("0");
        commissionRecord.setRemark(WITHDRAW_FAIL_REMARK);
        commissionRecord.setMoney(money);
        return commissionRecord;
    }


    /**
     * 佣金记录查询参数类
     */
    @Data
    public static class QueryCriteria {

        /**
         * 用户id
         */
        private long customerId = -1;

        /**
         * 开始时间
         */
        private String startTime;

        /**
         * 结束时间
         */
        private String endTime;

        /**
         * 类型
         */
        private String type;

        /**
         * 构建查询参数实体
         *
         * @param customerId 用户id
         * @return 查询参数实体
         */
        public static OmsCommissionRecords.QueryCriteria buildForSite(long customerId,String type) {
            OmsCommissionRecords.QueryCriteria queryCriteria = new OmsCommissionRecords.QueryCriteria();
            queryCriteria.setCustomerId(customerId);
            queryCriteria.setType(type);
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
            if (!StringUtils.isEmpty(type)) {
                params.put("type", type);
            }
            return params;
        }

    }
}
