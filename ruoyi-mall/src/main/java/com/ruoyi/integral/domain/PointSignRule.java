package com.ruoyi.integral.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 签到送积分规则实体
 */
@Data
@ApiModel(description = "签到送积分规则实体")
public class PointSignRule {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 是否开启 0 开启  1 不开启 默认0
     */
    @ApiModelProperty(value = "是否开启 0 开启  1 不开启 默认0")
    private String status;

    /**
     * 签到规则[{day:1,point:10},{day:2,point:20}...]
     */
    @ApiModelProperty(value = "签到规则[{day:1,point:10},{day:2,point:20}...]")
    private String rule;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 规则信息集合
     */
    @ApiModelProperty(value = "规则信息集合")
    private List<RuleInfo> ruleInfos;

    /**
     * 是否开启，开启返回true,否则返回false
     */
    @JsonIgnore
    public boolean isOpen() {
        return "0".equals(this.status);
    }

    /**
     * 判断是否有规则
     */
    public boolean hasRule() {
        return StringUtils.isEmpty(this.rule) ? false : !CollectionUtils.isEmpty(JSON.parseArray(this.rule, RuleInfo.class));
    }

    /**
     * 将赠品的json信息转化成对象信息
     *
     * @return 签到送积分规则实体
     */
    public com.ruoyi.integral.domain.PointSignRule convertJsonToObject() {
        if (StringUtils.isEmpty(this.rule)) {
            return this;
        }
        ruleInfos = JSON.parseArray(this.rule, RuleInfo.class).stream().sorted().collect(Collectors.toList());
        return this;
    }

    /**
     * 校验规则
     *
     * @return -2:包含天数小于等于0的规则 -3:包含积分小于0的规则 1:校验成功
     */
    public int checkRuleInfos() {
        if (this.ruleInfos.stream().filter(ruleInfo -> ruleInfo.filterDay(0)).collect(Collectors.toList()).size() > 0) {
            return -2;
        } else {
            if (this.ruleInfos.stream().filter(ruleInfo -> ruleInfo.filterPoint(-1)).collect(Collectors.toList()).size() > 0) {
                return -3;
            }
            return 1;
        }
    }

    /**
     * 规则去重
     *
     * @return 签到送积分规则实体
     */
    public com.ruoyi.integral.domain.PointSignRule distinctRuleInfos() {
        this.ruleInfos = new ArrayList<>(this.ruleInfos.stream().sorted().collect(Collectors.toSet()));
        this.rule = JSON.toJSONString(ruleInfos);
        return this;
    }

    /**
     * 规则信息
     */
    @Data
    @EqualsAndHashCode(callSuper = false)
    @ApiModel(description = "规则信息实体")
    public static class RuleInfo implements Comparable<RuleInfo> {

        /**
         * 连续天数
         */
        @ApiModelProperty(value = "连续天数")
        private int day;

        /**
         * 赠送的积分
         */
        @ApiModelProperty(value = "赠送的积分")
        private int point;

        @Override
        public int compareTo(RuleInfo o) {
            return this.day - o.day;
        }

        /**
         * 过滤连续天数
         *
         * @param day 连续天数
         * @return 相等返回true，如果参数为0，则小于等于0返回true
         */
        public boolean filterDay(int day) {
            if (day == 0) {
                return this.day <= 0;
            }
            return this.day == day;
        }

        /**
         * 过滤赠送积分
         *
         * @param point 积分
         * @return 相等返回true，如果参数小于0，则小于0返回true
         */
        public boolean filterPoint(int point) {
            if (point < 0) {
                return this.point < 0;
            }
            return this.point == point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RuleInfo ruleInfo = (RuleInfo) o;
            return day == ruleInfo.day;
        }

        @Override
        public int hashCode() {
            return day;
        }
    }


}
