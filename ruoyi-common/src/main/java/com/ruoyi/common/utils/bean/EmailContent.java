package com.ruoyi.common.utils.bean;

import lombok.Data;

/**
 * Created by 伊甸园商城 on 17/10/14.
 * 邮箱内容
 */
@Data
public class EmailContent {


    /**
     * 邮件目的地
     */
    private String sendTo;


    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

}
