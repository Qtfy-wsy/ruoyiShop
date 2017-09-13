//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-11-2 10:56:35
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	优惠劵管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_area")
public class Area extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String areaName;
public String getAreaName() {return this.getString("areaName");}
public void setAreaName(String areaName) {this.set("areaName",areaName);}
private Long parentId;
public Long getParentId() {return this.getLong("parentId");}
public void setParentId(Long parentId) {this.set("parentId",parentId);}
private Long areaSort;
public Long getAreaSort() {return this.getLong("areaSort");}
public void setAreaSort(Long areaSort) {this.set("areaSort",areaSort);}
private Long areaDeep;
public Long getAreaDeep() {return this.getLong("areaDeep");}
public void setAreaDeep(Long areaDeep) {this.set("areaDeep",areaDeep);}
private Integer isDel;
public Integer getIsDel() {return this.getInteger("isDel");}
public void setIsDel(Integer isDel) {this.set("isDel",isDel);}
private Long createTime;
public Long getCreateTime() {return this.getLong("createTime");}
public void setCreateTime(Long createTime) {this.set("createTime",createTime);}
private Long updateTime;
public Long getUpdateTime() {return this.getLong("updateTime");}
public void setUpdateTime(Long updateTime) {this.set("updateTime",updateTime);}
private String parentIds;
public String getParentIds() {return this.getString("parentIds");}
public void setParentIds(String parentIds) {this.set("parentIds",parentIds);}


}
