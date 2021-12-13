package com.ruoyi.common.md5;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * 消息资源根据类
 * Created by 伊甸园商城 on 17/7/10.
 */
public final class MessageSourceUtil {
    private static MessageSource messageSource;

    static {
        init((MessageSource) SpringUtils.getBean(MessageSource.class));
    }

    public static void init(MessageSource messageSource) {
        MessageSourceUtil.messageSource = messageSource;
    }


    /**
     * 根据code 获得国际化文件中的值
     *
     * @param code key
     * @return 返回值
     */
    public static String getMessage(String code) {
        try {
            return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
