package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 众筹促销实体
 *
 * @author SK
 * @since 2018/4/23
 */
@Data
@ApiModel(description = "众筹促销实体")
public class CrowdFunding {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 促销id  对应sms_marketing表中的id
     */
    @ApiModelProperty(value = "促销id  对应sms_marketing表中的id")
    private long marketingId;

    /**
     * 众筹金额
     */
    @ApiModelProperty(value = "众筹金额")
    private BigDecimal money;

    /**
     * 发货时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "发货时间")
    private LocalDateTime shipTime;

    /**
     * 1 元抽奖  0 开启 1 关闭 默认0
     */
    @ApiModelProperty(value = "1 元抽奖  0 开启 1 关闭 默认0")
    private String lottery;

    /**
     * 抽奖人数
     */
    @ApiModelProperty(value = "抽奖人数")
    private int lotteryNum;

    /**
     * 无回报支付 是否开启  0 开启 1 关闭 默认0
     */
    @ApiModelProperty(value = "无回报支付 是否开启  0 开启 1 关闭 默认0")
    private String noReturnSupport;

    /**
     * 已经筹到的金额
     */
    @ApiModelProperty(value = "已经筹到的金额")
    private BigDecimal alreadyMoney;


    /**
     * 支持数量（即订单数量）
     */
    @ApiModelProperty(value = "支持数量（即订单数量）")
    private int supportNum;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private long spuId;

    /**
     * 众筹图片
     */
    @ApiModelProperty(value = "众筹图片")
    private String pic;

    /**
     * 自动处理状态 0 未处理 1已处理 默认0
     */
    @ApiModelProperty(value = "自动处理状态 0 未处理 1已处理 默认0")
    private String autoHandleStatus;

    /**
     * 判断众筹是否成功
     *
     * @return 成功返回true
     */
    @JsonIgnore
    public boolean isSuccess(LocalDateTime endTime) {
        // 当前时间在结算时间之前直接返回false
        if (LocalDateTime.now().isBefore(endTime)) {
            return false;
        }
        return alreadyMoney.compareTo(money) > -1;
    }

    /**
     * 判断一元支持是否开启
     *
     * @return 开启返回true
     */
    @JsonIgnore
    public boolean isLotteryOpen() {
        return "0".equals(this.lottery);
    }

    /**
     * 查询条件
     */
    @Data
    @ApiModel(description = "众筹促销查询条件")
    public static class QueryCriteria {

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
         * 项目名称
         */
        @ApiModelProperty(value = "项目名称")
        private String name;

        /**
         * 状态 1筹备中 2众筹中 3众筹成功 4众筹失败
         */
        @ApiModelProperty(value = "状态 1筹备中 2众筹中 3众筹成功 4众筹失败")
        private long status = -1;

        /**
         * 店铺id
         */
        @ApiModelProperty(value = "店铺id")
        private long storeId;

        /**
         * 店铺名称
         */
        @ApiModelProperty(value = "店铺名称")
        private String storeName;


        /**
         * 构建店铺id
         *
         * @param storeId 店铺id
         * @return 查询参数实体
         */
        public QueryCriteria buildStoreId(long storeId) {
            this.storeId = storeId;
            return this;
        }

        /**
         * 获得查询参数
         */
        public Map<String, Object> getQueryMap() {
            Map<String, Object> params = new HashMap<>();
            if (!StringUtils.isEmpty(startTime)) {
                params.put("startTime", startTime);
            }
            if (!StringUtils.isEmpty(endTime)) {
                params.put("endTime", endTime);
            }
            if (!StringUtils.isEmpty(name)) {
                params.put("name", name);
            }
            if (!StringUtils.isEmpty(storeName)) {
                params.put("storeName", storeName);
            }
            params.put("status", status);
            params.put("storeId", storeId);
            return params;
        }

    }
}
