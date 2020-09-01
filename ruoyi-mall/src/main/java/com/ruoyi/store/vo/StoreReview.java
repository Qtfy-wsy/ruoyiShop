package com.ruoyi.store.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 魔金商城 on 2019/10/31.
 * 店铺审核结果实体
 */
@Data
@ApiModel(description = "店铺审核结果实体")
public class StoreReview {

    /**
     * 审核用户的名称
     */
    @ApiModelProperty(value = "审核用户的名称")
    private String userName;

    /**
     * 审核结果 0 提交未提交完成 1 审核通过 2 审核失败 3 资料审核中 4 店铺关闭或失效
     */
    @ApiModelProperty(value = "审核结果 0 提交未提交完成 1 审核通过 2 审核失败 3 资料审核中 4 店铺关闭或失效")
    private int result;

    /**
     * 审核失败原因
     */
    @ApiModelProperty(value = "审核失败原因")
    private String fail;

    /**
     * 店铺Id
     */
    @ApiModelProperty(value = "店铺Id")
    private long storeId;

    /**
     * 构造审核失败的实体
     *
     * @param userName 会员名称
     * @param fail     失败原因
     * @return 返回审核失败实体
     */
    public static StoreReview buildFail(String userName, String fail) {
        StoreReview storeReview = new StoreReview();
        storeReview.result = 2;
        storeReview.userName = userName;
        storeReview.fail = fail;
        return storeReview;
    }

    /**
     * 构造资料未完成实体
     *
     * @param userName 会员名称
     * @return 返回资料未完成实体
     */
    public static StoreReview buildUnFinishData(String userName) {
        StoreReview storeReview = new StoreReview();
        storeReview.result = 0;
        storeReview.userName = userName;
        return storeReview;
    }

    /**
     * 构造审核通过的实体
     *
     * @param userName 会员名称
     * @return 返回审核通过的实体
     */
    public static StoreReview buildSuccess(String userName) {
        StoreReview storeReview = new StoreReview();
        storeReview.result = 1;
        storeReview.userName = userName;
        return storeReview;
    }

    /**
     * 构造审核中实体
     *
     * @param userName 会员名
     * @return 返回审核中实体
     */
    public static StoreReview buildUnderReview(String userName) {
        StoreReview storeReview = new StoreReview();
        storeReview.result = 3;
        storeReview.userName = userName;
        return storeReview;
    }

    /**
     * 构造店铺关闭或失效实体
     *
     * @param userName 会员名
     * @return 返回店铺关闭或失效实体
     */
    public static StoreReview buildNotEffectiveReview(String userName) {
        StoreReview storeReview = new StoreReview();
        storeReview.result = 4;
        storeReview.userName = userName;
        return storeReview;
    }

    /**
     * 添加店铺ID
     *
     * @param storeId 店铺ID
     * @return 店铺审核结果实体
     */
    public StoreReview addStoreId(long storeId) {
        this.storeId = storeId;
        return this;
    }

}
