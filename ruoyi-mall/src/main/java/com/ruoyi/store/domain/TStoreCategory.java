package com.ruoyi.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 店铺分类对象 t_store_category
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String name;

    /**
     * 上级分类id 0 表示没有上级
     */
    @Excel(name = "上级分类id 0 表示没有上级")
    private Long parentId;

    /**
     * 1 一级分类 2 二级分类 3 三级分类
     */
    @Excel(name = "1 一级分类 2 二级分类 3 三级分类")
    private Long grade;

    /**
     * 排序  数值越小 越靠前
     */
    @Excel(name = "排序  数值越小 越靠前")
    private Long sort;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long storeId;

    /**
     * 删除标记  0 未删除 1 删除
     */
    private int delFlag;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /**
     * 子分类
     */
    @ApiModelProperty(value = "子分类")
    private List<TStoreCategory> childCateGory;
    /**
     * 是否显示下级
     */
    @ApiModelProperty(value = "是否显示下级")
    private boolean showChild;

    /**
     * 判断是否为一级分类
     *
     * @return 一级分类返回true, 否则返回false
     */
    @JsonIgnore
    public boolean isFirst() {
        return 1 == this.grade;
    }
}
