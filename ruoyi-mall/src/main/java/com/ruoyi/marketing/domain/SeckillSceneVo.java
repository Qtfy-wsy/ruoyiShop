package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 2020/5/18.
 * 秒杀场次
 */
@Data
@ApiModel(description = "秒杀场次")
public class SeckillSceneVo {

    /**
     * 场次
     */
    @ApiModelProperty(value = "场次")
    private String secen;

    /**
     * 秒杀时间(年月日时)
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime seckillTime;

    /**
     * 状态 0 即将开始1 正在进行 默认0
     */
    private String status = "0";

    public static SeckillSceneVo build(SeckillScene seckillScene) {
        if (Objects.isNull(seckillScene)) {
            return null;
        }
        SeckillSceneVo seckillSceneVo = new SeckillSceneVo();
        seckillSceneVo.seckillTime = seckillScene.getSeckillTime();
        seckillSceneVo.secen = seckillScene.getSeceneTime();
        seckillSceneVo.status = LocalDateTime.now().getHour() == seckillScene.getScene() ? "1" : "0";
        return seckillSceneVo;
    }
}
