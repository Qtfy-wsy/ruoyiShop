package com.ruoyi.sms.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 首页推荐专题对象 sms_home_recommend_subject
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Setter
@Getter
public class SmsHomeRecommendSubject extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long subjectId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String subjectName;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer recommendStatus;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer sort;

    /**
     * 所属店铺
     */
    @Excel(name = "所属店铺")
    private Long storeId;

    /**
     * $column.columnComment
     */
    @Excel(name = "所属店铺")
    private String pic;

    /**
     * $column.columnComment
     */
    @Excel(name = "所属店铺")
    private Integer readCount;

    /**
     * $column.columnComment
     */
    @Excel(name = "所属店铺")
    private String description;


}
