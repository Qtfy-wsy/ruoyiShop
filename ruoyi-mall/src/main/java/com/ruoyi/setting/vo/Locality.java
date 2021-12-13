package com.ruoyi.setting.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 伊甸园商城
 * @date 2019-12-04 15:09
 * <p>
 * 地区新数据接口实体
 */
@Data
@ApiModel(description = "地区新数据接口实体")
public class Locality {


    /**
     * 地区名称
     */
    @ApiModelProperty(value = "地区名称")
    private String name;


    /**
     * 数据真实主键ID
     */
    @ApiModelProperty(value = "数据真实主键ID")
    private String id;


    /**
     * 临时Id字串 保证唯一
     */
    @ApiModelProperty(value = "临时Id字串 保证唯一")
    private String value;


    /**
     * 父类的临时字串  一级为0
     */
    @ApiModelProperty(value = "父类的临时字串  一级为0")
    private String parent;


    /**
     * 重写全参构造
     *
     * @param name   地区名称
     * @param value  ID
     * @param parent 父类ID  一级为0
     */
    public Locality(String name, String value, String parent, long id) {
        this.name = name;
        this.value = value;
        this.parent = parent;
        this.id = String.valueOf(id);
    }
}
