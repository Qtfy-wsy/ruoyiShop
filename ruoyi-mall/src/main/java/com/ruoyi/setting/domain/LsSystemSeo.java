package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 系统seo设置对象 ls_system_seo
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
public class LsSystemSeo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * seo标题
     */
    @Excel(name = "seo标题")
    private String title;

    /**
     * seo关键字
     */
    @Excel(name = "seo关键字")
    private String keyWord;

    /**
     * seo描述
     */
    @Excel(name = "seo描述")
    private String seoDesc;

    /**
     * 是否开启  0 未开启 1 开启
     */
    @Excel(name = "是否开启  0 未开启 1 开启 ")
    private String isOpen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getSeoDesc() {
        return seoDesc;
    }

    public void setSeoDesc(String seoDesc) {
        this.seoDesc = seoDesc;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("keyWord", getKeyWord())
                .append("seoDesc", getSeoDesc())
                .append("isOpen", getIsOpen())
                .toString();
    }
}
