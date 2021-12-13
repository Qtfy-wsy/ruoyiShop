package com.ruoyi.appletsutil;


import com.ruoyi.common.md5.MessageSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础返回
 * Created by 伊甸园商城 on 17/7/10.
 */
@Data
@AllArgsConstructor
public class BaseResponse implements Serializable {

    /**
     * 结果码
     */
    private String code;
    /**
     * 消息内容
     */
    private String message;

    public BaseResponse(String code) {
        this.code = code;
        this.message = MessageSourceUtil.getMessage(code);
    }

    public static BaseResponse SUCCESSFUL() {
        return new BaseResponse(ResultCode.SUCCESSFUL);
    }

    public static BaseResponse FAILED() {
        return new BaseResponse(ResultCode.FAILED);
    }
}
