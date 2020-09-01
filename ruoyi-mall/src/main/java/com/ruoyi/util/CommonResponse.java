package com.ruoyi.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * Created by 魔金商城 on 17/12/1.
 * 通用返回对象
 */
@Data
@ApiModel(description = "通用返回对象")
public class CommonResponse<T> {

    /**
     * 返回码 1 成功 其他失败   默认0没有数据
     */
    @ApiModelProperty(value = "返回码 1 成功 其他失败   默认0没有数据")
    private int flag = 0;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T data;


    /**
     * 构造返回对象
     *
     * @param t 返回的对象
     * @return 返回的对象
     */
    public static <T> CommonResponse build(T t) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        // 没有数据
        if (ObjectUtils.isEmpty(t)) {
            return commonResponse;
        }
        commonResponse.flag = 1;
        commonResponse.data = t;
        return commonResponse;
    }

    /**
     * 构造返回对象
     *
     * @param t    返回的对象
     * @param code 返回码
     * @return 返回的对象
     */
    public static <T> CommonResponse<T> build(T t, int code) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.flag = code;
        commonResponse.data = t;
        return commonResponse;
    }
}
