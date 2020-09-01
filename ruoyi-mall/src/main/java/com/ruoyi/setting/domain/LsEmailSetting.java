package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 邮箱设置对象 ls_email_setting
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public class LsEmailSetting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 发信邮箱
     */
    @Excel(name = "发信邮箱")
    private String senderMail;

    /**
     * 发信人
     */
    @Excel(name = "发信人")
    private String senderName;

    /**
     * SMTP的服务器地址
     */
    @Excel(name = "SMTP的服务器地址")
    private String smtpServer;

    /**
     * SMTP 的端口
     */
    @Excel(name = "SMTP 的端口")
    private Long smtpPort;

    /**
     * 邮箱帐号
     */
    @Excel(name = "邮箱帐号")
    private String username;

    /**
     * 邮箱密码
     */
    @Excel(name = "邮箱密码")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public Long getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Long smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("senderMail", getSenderMail())
                .append("senderName", getSenderName())
                .append("smtpServer", getSmtpServer())
                .append("smtpPort", getSmtpPort())
                .append("username", getUsername())
                .append("password", getPassword())
                .toString();
    }
}
