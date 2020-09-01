package com.ruoyi.store.vo;


import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.goods.domain.PmsCategory;
import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 开店信息实体类用于开店流程查询
 *
 * @author 魔金商城 on 2017/6/20.
 */
@Data
@ApiModel(description = "开店信息实体类用于开店流程查询")
public class StoreBusinessInfo {

    /**
     * 公司信息
     */
    @ApiModelProperty(value = "公司信息")
    private TStoreInfo storeInfo;
    /**
     * 二级分类
     */
    @ApiModelProperty(value = "二级分类")
    private List<PmsCategory> twoCategoryList;
    /**
     * 三级分类（签约分类）
     */
    @ApiModelProperty(value = "三级分类（签约分类）")
    private List<PmsCategory> threeCategoryList;
    /**
     * 主营店铺品牌
     */
    @ApiModelProperty(value = "主营店铺品牌")
    private List<PmsBrand> storeBrandList;
    /**
     * 自定义品牌
     */
    @ApiModelProperty(value = "自定义品牌")
    private List<PmsBrand> mySelfBrandList;

    public StoreBusinessInfo getStoreBusinessInfo(TStoreInfo storeInfo, List<PmsCategory> twoCategoryList, List<PmsCategory> threeCategoryList, List<PmsBrand> storeBrandList, List<PmsBrand> mySelfBrandList) {
        this.storeInfo = storeInfo;
        this.twoCategoryList = twoCategoryList;
        this.threeCategoryList = threeCategoryList;
        this.storeBrandList = storeBrandList;
        this.mySelfBrandList = mySelfBrandList;
        return this;
    }

}
