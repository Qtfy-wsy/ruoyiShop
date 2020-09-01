package com.ruoyi.store.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.CustomLocalDateTimeDeserializer;
import com.ruoyi.common.utils.CustomLocalDateTimeSerializer;
import com.ruoyi.goods.domain.PmsSku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 关注店铺实体类
 *
 * @author 魔金商城 on 2017/7/4.
 */
@Data
@ApiModel(description = "关注店铺实体类")
public class AttentionStore {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;
    /**
     * 删除标记 0 未删除 1 删除
     */
    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    private int delFlag;
    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "店铺信息")
    private TStoreInfo storeInfo;

    /**
     * 商品集合
     */
    @ApiModelProperty(value = "商品集合")
    private List<PmsSku> skuList;

    /**
     * 关注店铺数量
     */
    @ApiModelProperty(value = "关注店铺数量")
    private int storeAttentionCount;

    /**
     * 获取店铺关注
     *
     * @param storeInfo 店铺信息
     * @param skuList   单品集合
     * @return 返回店铺关注
     */
    public AttentionStore getAttentionStore(TStoreInfo storeInfo, List<PmsSku> skuList, int storeAttentionCount) {
        this.setStoreInfo(storeInfo);
        this.setSkuList(skuList);
        this.setStoreAttentionCount(storeAttentionCount);
        return this;
    }
}
