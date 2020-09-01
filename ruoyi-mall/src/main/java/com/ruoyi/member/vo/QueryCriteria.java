package com.ruoyi.member.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员预存款查询条件
 */
@Data
@ApiModel(description = "会员预存款查询条件")
public class QueryCriteria {

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    private int pageNum =0;
    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String userName;

    /**
     * 交易单号
     */
    @ApiModelProperty(value = "交易单号")
    private String transCode;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /**
     * 查询类型 1全部 2 收入 3支出 默认全部
     */
    @ApiModelProperty(value = "查询类型 1全部 2 收入 3支出 默认全部")
    private String filterType = "1";

    /**
     * 获得查询条件
     *
     * @return 返回查询条件
     */
    public Map<String, Object> getQueryMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("userName", userName);
        params.put("transCode", transCode);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("filterType", filterType);
        params.put("pageNum", pageNum);
        return params;
    }

}
