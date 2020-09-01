package com.ruoyi.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsSku;
import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * 推荐货品实体类
 */
@Data
public class RecommendSku {


    /**
     * id
     */
    private long id;
    /**
     * 货品id
     */
    private String skuId;

    /**
     * 销售数量
     */
    private int num;

    /**
     * 货品
     */
    private PmsSku sku;

    /**
     * 评论数
     */
    private int commentCount;

    /**
     * 设置单品信息
     *
     * @param sku 单品信息
     */
    @JsonIgnore
    public RecommendSku setSkuDetail(PmsSku sku) {
        this.sku = sku;
        return this;
    }

    /**
     * 有上架的货品信息返回true,否则返回false
     */
    @JsonIgnore
    public boolean hasOnSaleSku() {
        return !ObjectUtils.isEmpty(this.sku) && sku.validateStatus();
    }

    /**
     * 设置评论数
     *
     * @param count 评论数
     */
    @JsonIgnore
    public RecommendSku initCommentCount(int count) {
        this.commentCount = count;
        return this;
    }
}
