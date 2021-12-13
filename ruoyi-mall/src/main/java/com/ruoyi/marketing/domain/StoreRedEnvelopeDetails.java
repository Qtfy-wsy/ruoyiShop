package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 伊甸园商城 on 18/4/9
 * 门店红包详情实体类
 */
@Data
@ApiModel(description = "门店红包详情实体类")
public class StoreRedEnvelopeDetails {

    /**
     * 门店红包实体类
     */
    @ApiModelProperty(value = "门店红包实体类")
    private StoreRedEnvelope storeRedEnvelope;

    /**
     * 门店红包码实体类
     */
    @ApiModelProperty(value = "门店红包码实体类")
    private List<StoreRedEnvelopeCode> storeRedEnvelopeCodes;

    public StoreRedEnvelopeDetails(StoreRedEnvelope storeRedEnvelope, List<StoreRedEnvelopeCode> storeRedEnvelopeCodes) {
        super();
        this.storeRedEnvelope = storeRedEnvelope;
        this.storeRedEnvelopeCodes = storeRedEnvelopeCodes;
    }

}
