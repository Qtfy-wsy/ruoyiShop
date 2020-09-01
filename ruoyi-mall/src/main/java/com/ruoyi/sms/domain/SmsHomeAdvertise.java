package com.ruoyi.sms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 首页轮播广告对象 sms_home_advertise
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Setter
@Getter
public class SmsHomeAdvertise extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;

    /**
     * 轮播位置：2->h5轮播；1->小程序；3->pc；4->android；5->ios
     */
    @Excel(name = "轮播位置：2->h5轮播；1->小程序；3->pc；4->android；5->ios")
    private Long type;

    /**
     * $column.columnComment
     */
    @Excel(name = "轮播位置：2->h5轮播；1->小程序；3->pc；4->android；5->ios")
    private String pic;

    /**
     * $column.columnComment
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "轮播位置：2->h5轮播；1->小程序；3->pc；4->android；5->ios", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;
    private Date endTime;

    /**
     * 上下线状态：0->下线；1->上线
     */
    @Excel(name = "上下线状态：0->下线；1->上线")
    private Integer status;

    /**
     * 点击数
     */
    @Excel(name = "点击数")
    private Integer clickCount;

    /**
     * 1 轮播图 2 新品推荐广告 3 人气推荐广告 4热门推荐广告 5 分类广告
     */
    @Excel(name = "位置")
    private Integer orderCount;

    /**
     * 链接地址
     */
    @Excel(name = "链接地址")
    private String url;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 广告所属店铺
     */
    @Excel(name = "广告所属店铺")
    private Long storeId;


}
