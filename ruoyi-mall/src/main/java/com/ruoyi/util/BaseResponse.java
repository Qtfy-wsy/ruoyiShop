package com.ruoyi.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 伊甸园商城 on 17/5/9.
 * 分页基础响应类
 */
@Data
@ApiModel(description = "分页基础响应类")
public class BaseResponse {


    /**
     * 返回的数据
     */
    @ApiModelProperty(value = "返回的数据")
    private List data;
    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private int recordsTotal;
    /**
     * 过滤后的记录数
     */
    @ApiModelProperty(value = "过滤后的记录数")
    private int recordsFiltered;
    /**
     * 总的页数
     */
    @ApiModelProperty(value = "总的页数")
    private int totalPage;

    private BaseResponse() {

    }

    private BaseResponse(PageHelper pageHelper) {
        this.data = pageHelper.getList();
        this.recordsTotal = pageHelper.getRows();
        this.recordsFiltered = pageHelper.getRows();
        this.totalPage = pageHelper.getTotalPages();
    }

    /**
     * 构造分页响应实体
     *
     * @param pageHelper 分页帮助类
     * @return 返回分页响应实体
     */
    public static BaseResponse build(PageHelper pageHelper) {
        return new BaseResponse(pageHelper);
    }
}
