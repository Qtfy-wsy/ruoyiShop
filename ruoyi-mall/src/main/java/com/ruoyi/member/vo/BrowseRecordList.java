package com.ruoyi.member.vo;

import com.ruoyi.member.domain.UmsBrowseRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 浏览历史列表
 * <p>
 * Created by 魔金商城 on 2017/7/4.
 */
@Data
@ApiModel(description = "浏览历史列表实体")
public class BrowseRecordList implements Comparable<BrowseRecordList> {

    /**
     * 浏览时间
     */
    @ApiModelProperty(value = "浏览时间")
    private String time;

    /**
     * 某一天的浏览历史列表
     */
    @ApiModelProperty(value = "某一天的浏览历史列表")
    private List<UmsBrowseRecord> browseRecordList;

    /**
     * 将某一天的浏览历史按时间排序
     *
     * @param browseRecordList 浏览历史列表
     * @return 相等返回0，小于返回>0，大于返回<0
     */
    @Override
    public int compareTo(BrowseRecordList browseRecordList) {
        return -this.getTime().compareTo(browseRecordList.getTime());
    }

}
