package com.ruoyi.common.exception;

import com.ruoyi.common.md5.MessageSourceUtil;
import lombok.Data;

/**
 * Created by 魔金商城 on 17/7/10.
 * 业务异常
 */
@Data
public class ServiceException extends RuntimeException {

    /**
     * 错误code
     */
    private String errorCode;
    public ServiceException() {

    }
    public ServiceException(String errorCode) {
        this(errorCode, MessageSourceUtil.getMessage(errorCode));
    }

    public ServiceException(String errorCode, String message) {
        super(message == null ? errorCode : message);
        this.errorCode = errorCode;
    }

}
