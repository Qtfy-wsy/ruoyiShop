package com.ruoyi.common.utils.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端通用返回类
 *
 * @author 徐凌峰
 */
@ApiModel("接口返回公共对象")
@Data
public class CommonWebResult<T> {
    public static final CommonWebResult SUCCESS = new CommonWebResult<>(true);
    public static final CommonWebResult FAIL = new CommonWebResult(false);

    static {
        SUCCESS.setErrCode(0);
        SUCCESS.setErrMsg("SUCCESS");
    }

    /**
     * 返回给前端的业务数据
     */
    @ApiModelProperty("返回的具体业务数据")
    private T data;
    /**
     * 错误码
     */
    private Integer errCode;
    /**
     * 错误信息
     */
    private String errMsg;

    public CommonWebResult(T data) {
        this.data = data;
    }

    public CommonWebResult() {
    }

    public CommonWebResult data(T data) {
        this.setData(data);
        return this;
    }
}
