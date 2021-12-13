package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 伊甸园商城 on 2020/5/11.
 * 秒杀场次
 */
@Data
@ApiModel(description = "秒杀场次")
public class SeckillScene {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;

    /**
     * 秒杀时间(年月日时)
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime seckillTime;

    /**
     * 结束时间(年月日时)
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime seckillendTime;

    /**
     * 场次  0-23 代表24小时
     */
    @ApiModelProperty(value = "场次  0-23 代表24小时")
    private int scene;

    /**
     * 删除标记  0 未删除 1 删除
     */
    @ApiModelProperty(value = "删除标记  0 未删除 1 删除")
    private int delFlag;


    /**
     * 参与的商家数量
     */
    @ApiModelProperty(value = "参与的商家数量")
    private int storeNum;

    /**
     * 秒杀商品的数量
     */
    @ApiModelProperty(value = "秒杀商品的数量")
    private int skuNum;

    /**
     * 店铺是否可以参与该秒杀场次 true 可以参加 false 不可以
     */
    @ApiModelProperty(value = "店铺是否可以参与该秒杀场次 true 可以参加 false 不可以")
    private boolean ifCanAttend;

    /**
     * 判断是否是新增的秒杀场次
     *
     * @return 如果id 为0 则是新增的秒杀场次
     */
    @JsonIgnore
    public boolean isAddSeckillScene() {
        return this.id == 0;
    }

    /**
     * 判断是否是不需要删除的场次
     *
     * @return 如果id不为0  则是不需要删的秒杀场次
     */
    @JsonIgnore
    public boolean isNoNeedDeleteSeckillScene() {
        return this.id != 0;
    }

    /**
     * 设置完整的秒杀场次时间 seckillTime+scene
     */
    public void setFullSeckillTime() {
        this.seckillTime = this.seckillTime.plusHours(this.scene);
    }


    /**
     * 获得秒杀场次时间
     *
     * @return 返回秒杀场次时间
     */
    public String getSeckillTimeForPanicBuy() {
        return this.seckillTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 设置秒杀活动结束时间固定一小时
     */
    public void setEndTime() {
        this.seckillendTime = this.seckillTime.plusHours(1);
    }


    /**
     * 判断是否没过期 没过期返回true 否则返回false
     *
     * @param hour 当天比较的时
     * @return 判断是否没过期 规则是 如果场次>=hour则算没过期
     */
    @JsonIgnore
    public boolean noExpire(int hour) {
        return this.scene >= hour;
    }

    /**
     * 获得秒杀场次的时间 返回时部分比如2020-05-15 12:00:00 则返回 12：00
     *
     * @return 返回秒杀场次的时间
     */
    public String getSeceneTime() {
        return this.getSeckillTimeForPanicBuy().substring(11, 16);
    }
}
