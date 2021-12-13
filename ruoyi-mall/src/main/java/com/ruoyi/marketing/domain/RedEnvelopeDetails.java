package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 伊甸园商城 on 18/1/16
 * 红包详情实体类
 */
@Data
@ApiModel(description = "红包详情实体")
public class RedEnvelopeDetails {

    /**
     * 红包实体类
     */
    @ApiModelProperty(value = "红包实体类")
    private RedEnvelope redEnvelope;

    /**
     * 红包码实体类
     */
    @ApiModelProperty(value = "红包码实体类")
    private List<RedEnvelopeCode> redEnvelopeCode;

    public RedEnvelopeDetails(RedEnvelope redEnvelope, List<RedEnvelopeCode> redEnvelopeCode) {
        super();
        this.redEnvelope = redEnvelope;
        this.redEnvelopeCode = redEnvelopeCode;
    }
}
