package com.ruoyi.member.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单投诉实体
 */
@Data
@ApiModel(description = "订单投诉实体")
public class ComplaintOrder {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 订单code
     */
    @ApiModelProperty(value = "订单code")
    private String orderCode;

    /**
     * 订单投诉类型  1 产品相关 2价格相关 3服务相关 4物流相关 5售后相关 6财务相关 7活动相关 8网站相关 9 预约相关 10其他方面
     */
    @ApiModelProperty(value = "订单投诉类型  1 产品相关 2价格相关 3服务相关 4物流相关 5售后相关 6财务相关 7活动相关 8网站相关 9 预约相关 10其他方面")
    private String complaintsType;

    /**
     * 投诉描述
     */
    @ApiModelProperty(value = "投诉描述")
    private String complaintsDesc;

    /**
     * 投诉状态  0 未处理  1 已处理
     */
    @ApiModelProperty(value = "投诉状态  0 未处理  1 已处理")
    private String status;

    /**
     * 投诉回复
     */
    @ApiModelProperty(value = "投诉回复")
    private String complaintsReply;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operator;


    /**
     * 构建回复实体
     *
     * @param operator 操作人
     * @param reply    回复
     * @param id       订单投诉id
     * @return 订单投诉实体
     */
    public static ComplaintOrder buildForReply(String operator, String reply,long id) {
        ComplaintOrder complaintOrder = new ComplaintOrder();
        complaintOrder.setId(id);
        complaintOrder.setOperator(operator);
        complaintOrder.setComplaintsReply(reply);
        return complaintOrder;
    }


    /**
     * 构建用户id
     *
     * @param customerId 用户id
     * @return 订单投诉实体
     */
    public ComplaintOrder buildCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * 查询参数实体
     */
    @Data
    public static class QueryCriteria {
        /**
         * 用户id
         */
        private long customerId = -1;

        /**
         * 状态
         */
        private String status = "-1";

        /**
         * 投诉类型
         */
        private String complaintsType = "-1";

        /**
         * 订单code
         */
        private String orderCode;

        /**
         * 获取查询参数map
         *
         * @return 查询参数map
         */
        public Map<String, Object> getQueryMap() {
            Map<String, Object> params = new HashMap<>();
            params.put("customerId", customerId);
            params.put("status", status);
            params.put("complaintsType", complaintsType);
            params.put("orderCode", orderCode);
            return params;
        }

        /**
         * 构建pc查询
         *
         * @param customerId 用户id
         * @return 查询参数实体
         */
        public static QueryCriteria buildForPc(long customerId) {
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.customerId = customerId;
            return queryCriteria;
        }
    }


}
