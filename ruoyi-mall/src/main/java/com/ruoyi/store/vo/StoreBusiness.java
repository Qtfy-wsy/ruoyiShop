package com.ruoyi.store.vo;


import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 店铺信息实体类用于开店流程添加
 *
 * @author 伊甸园商城 on 2017/6/19.
 */
@Data
@ApiModel(description = "店铺信息实体类用于开店流程添加")
public class StoreBusiness {
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    /**
     * 签约分类ids
     */
    @ApiModelProperty(value = "签约分类ids")
    private long[] categoryIds;
    /**
     * 主营品牌
     */
    @ApiModelProperty(value = "主营品牌")
    private long[] brandIds;
    /**
     * 自定义品牌
     */
    @ApiModelProperty(value = "自定义品牌")
    private List<PmsBrand> brands;

    /**
     * 店铺信息
     */
    @ApiModelProperty(value = "店铺信息")
    private TStoreInfo storeInfo;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String mobile;
}
