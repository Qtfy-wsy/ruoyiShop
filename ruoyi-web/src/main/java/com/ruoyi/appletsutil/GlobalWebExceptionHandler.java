package com.ruoyi.appletsutil;

import com.ruoyi.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 伊甸园商城 on 17/7/10.
 * 全局异常
 */
@ControllerAdvice
public class GlobalWebExceptionHandler {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(GlobalWebExceptionHandler.class);

    /**
     * 未认证异常（有可能是未关联用户 也有可能是微信未授权根据返回码判断）
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseBody
    public BaseResponse signatureExceptionHandler(HttpServletRequest request, UnAuthorizedException ex) {
        logger.error("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), request.getRequestURL(),
                ex.getLocalizedMessage());
        logger.error("queryString:{}, parameterMap: {}", request.getQueryString(), request.getParameterMap(), ex);

        return new BaseResponse(ex.getErrorCode(), ex.getLocalizedMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse serviceExceptionHandler(HttpServletRequest request, ServiceException ex) throws Exception {
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null)
            throw ex;

        logger.error("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), request.getRequestURL(),
                ex.getLocalizedMessage());
        if (logger.isDebugEnabled()) {
            logger.error("queryString:{}, parameterMap: {}", request.getQueryString(), request.getParameterMap(), ex);
        }

        return new BaseResponse(ex.getErrorCode(), ex.getLocalizedMessage());
    }
}
