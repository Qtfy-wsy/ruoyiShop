package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 18/1/15
 * 红包码实体类
 */
@Data
@ApiModel(description = "红包码实体类")
public class RedEnvelopeCode {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 红包id
     */
    @ApiModelProperty(value = "红包id")
    private long redEnvelopeId;
    /**
     * 红包码
     */
    @ApiModelProperty(value = "红包码")
    private String code;
    /**
     * 领取红包的会员id
     */
    @ApiModelProperty(value = "领取红包的会员id")
    private long customerId;
    /**
     * 红包的状态  0 未领取 1已领取未使用 2 已使用 3已失效
     */
    @ApiModelProperty(value = "红包的状态  0 未领取 1已领取未使用 2 已使用 3已失效")
    private String status;
    /**
     * 领取时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "领取时间")
    private LocalDateTime receiveTime;

    /**
     * 会员名称（用于连会员表查询）
     */
    @ApiModelProperty(value = "会员名称（用于连会员表查询）")
    private String userName;


    /**
     * 红包信息
     */
    @ApiModelProperty(value = "红包信息")
    private RedEnvelope redEnvelope;

    /**
     * 红包关联的店铺信息
     */
    @ApiModelProperty(value = "红包关联的店铺信息")
    private List<RedEnvelopeStore> redEnvelopeStores;

    public RedEnvelopeCode() {

    }

    public RedEnvelopeCode(long id, long redEnvelopeId, String code, long customerId, String status, LocalDateTime receiveTime, String userName) {
        super();
        this.id = id;
        this.redEnvelopeId = redEnvelopeId;
        this.code = code;
        this.customerId = customerId;
        this.status = status;
        this.receiveTime = receiveTime;
        this.userName = userName;
    }

    /**
     * 获得红包关联的店铺id
     *
     * @return 返回红包关联的店铺id
     */
    public List<Long> getRedEnvelopeStoreId() {
        if (CollectionUtils.isEmpty(this.redEnvelopeStores)) {
            return Collections.emptyList();
        }

        return redEnvelopeStores.stream().map(RedEnvelopeStore::getStoreId).collect(Collectors.toList());
    }
}
