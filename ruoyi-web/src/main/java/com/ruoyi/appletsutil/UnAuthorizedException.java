package com.ruoyi.appletsutil;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.md5.MessageSourceUtil;
import lombok.Data;

/**
 * Created by 伊甸园商城 on 2018/6/13.
 * 微信小程序未授权异常
 */
@Data
public class UnAuthorizedException extends ServiceException {

    /**
     * 错误code
     */
    private String errorCode;

    public UnAuthorizedException(String errorCode) {
        this(errorCode, MessageSourceUtil.getMessage(errorCode));
    }

    public UnAuthorizedException(String errorCode, String message) {
        super(message == null ? errorCode : message);
        this.errorCode = errorCode;
    }
}
