package com.ruoyi.marketing.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 伊甸园商城 on 18/4/9
 * 门店红包码实体类
 */
@Data
public class StoreRedEnvelopeCode {

    /**
     * 主键id
     */
    private long id;
    /**
     * 门店红包id
     */
    private long redEnvelopeId;
    /**
     * 红包码
     */
    private String code;
    /**
     * 领取红包的会员id
     */
    private long customerId;
    /**
     * 红包的状态  0 未领取 1已领取未使用 2 已使用 3已失效
     */
    private String status;
    /**
     * 领取时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime receiveTime;

    /**
     * 会员名称（用于连会员表查询）
     */
    private String userName;


    /**
     * 红包信息
     */
    private StoreRedEnvelope storeRedEnvelope;

    public StoreRedEnvelopeCode() {

    }

    public StoreRedEnvelopeCode(long id, long redEnvelopeId, String code, long customerId, String status, LocalDateTime receiveTime, String userName) {
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
     * 构建用户id
     *
     * @param customerId 用户id
     * @return 门店红包code实体
     */
    public StoreRedEnvelopeCode buildCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }


}
