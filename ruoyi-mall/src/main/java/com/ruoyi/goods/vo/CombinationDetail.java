package com.ruoyi.goods.vo;


import com.ruoyi.marketing.domain.GoodsCombination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 魔金商城 on 17/12/26.
 * 商品组合详情
 */
@Data
@ApiModel(description = "商品组合详情")
public class CombinationDetail {

    /**
     * 是否有商品组合信息  默认没有
     */
    @ApiModelProperty(value = "是否有商品组合信息  默认没有")
    private boolean hasCombination = false;

    /**
     * 商品组合
     */
    @ApiModelProperty(value = "商品组合")
    private GoodsCombination goodsCombination;

    /**
     * 商品详情
     */
    @ApiModelProperty(value = " 商品详情")
    private List<SpuDetail> spuDetails;


    /**
     * 构造没有商品组合的实体
     *
     * @return 商品组合
     */
    public static CombinationDetail buildNoCombination() {
        return new CombinationDetail();
    }


    /**
     * 构造有商品组合的实体
     *
     * @param goodsCombination 商品组合
     * @param spuDetails       商品组合下的商品信息
     * @return 返回商品组合
     */
    public static CombinationDetail buildHasCombination(GoodsCombination goodsCombination, List<SpuDetail> spuDetails) {
        CombinationDetail combinationDetail = new CombinationDetail();
        combinationDetail.hasCombination = true;
        combinationDetail.spuDetails = spuDetails;
        combinationDetail.goodsCombination = goodsCombination;
        return combinationDetail;
    }

}
