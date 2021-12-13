package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 2020/5/12.
 * 秒杀场次下的抢购
 */
@Data
public class SeckillScenePanicbuy {

    /**
     * 主键id
     */
    private long id;

    /**
     * 秒杀场次的id 对应sms_seckill_scene表中的id
     */
    private long seckillSceneId;

    /**
     * 折扣促销id 对应sms_marketing_panicbuy表中的id
     */
    private long panicbuyId;

    /**
     * 促销的id 对应sms_marketing表中的id
     */
    private long marketingId;

    /**
     * 排序
     */
    private int sort;

    /**
     * 秒杀时间 该字段是冗余字段
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime seckillTime;

    /**
     * 是否显示  0 不显示 1 显示 默认0
     */
    private String isShow;

    /**
     * 删除标记 0 未删除 1 已删除
     */
    private int delFlag;

    /**
     * 折扣
     */
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    /**
     * 限购个数
     */
    @ApiModelProperty(value = "限购个数")
    private int limitNum;

    /**
     * 抢购的单品id
     */
    @ApiModelProperty(value = "抢购的单品id")
    private String skuId;

    /**
     * 单品信息
     */
    @ApiModelProperty(value = "单品信息")
    private PmsSku sku;

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


    public static SeckillScenePanicbuy build(PanicBuy panicBuy, SeckillScene seckillScene) {

        if (Objects.isNull(panicBuy) || Objects.isNull(seckillScene)) {
            return null;
        }

        SeckillScenePanicbuy seckillScenePanicbuy = new SeckillScenePanicbuy();
        seckillScenePanicbuy.seckillSceneId = seckillScene.getId();
        seckillScenePanicbuy.panicbuyId = panicBuy.getId();
        seckillScenePanicbuy.marketingId = panicBuy.getMarketingId();
        seckillScenePanicbuy.sort = 1;
        seckillScenePanicbuy.seckillTime = seckillScene.getSeckillTime();
        seckillScenePanicbuy.isShow = "0";
        seckillScenePanicbuy.delFlag = 0;
        return seckillScenePanicbuy;
    }
}
