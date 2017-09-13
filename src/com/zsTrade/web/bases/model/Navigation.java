//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-10-31 20:31:35
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	基础管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_navigation")
public class Navigation extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Integer navType;
public Integer getNavType() {return this.getInteger("navType");}
public void setNavType(Integer navType) {this.set("navType",navType);}
private String navTitle;
public String getNavTitle() {return this.getString("navTitle");}
public void setNavTitle(String navTitle) {this.set("navTitle",navTitle);}
private String navUrl;
public String getNavUrl() {return this.getString("navUrl");}
public void setNavUrl(String navUrl) {this.set("navUrl",navUrl);}
private Integer navLocation;
public Integer getNavLocation() {return this.getInteger("navLocation");}
public void setNavLocation(Integer navLocation) {this.set("navLocation",navLocation);}
private Long navNewOpen;
public Long getNavNewOpen() {return this.getLong("navNewOpen");}
public void setNavNewOpen(Long navNewOpen) {this.set("navNewOpen",navNewOpen);}
private Long navSort;
public Long getNavSort() {return this.getLong("navSort");}
public void setNavSort(Long navSort) {this.set("navSort",navSort);}
private Long itemId;
public Long getItemId() {return this.getLong("itemId");}
public void setItemId(Long itemId) {this.set("itemId",itemId);}
private Integer isDel;
public Integer getIsDel() {return this.getInteger("isDel");}
public void setIsDel(Integer isDel) {this.set("isDel",isDel);}
private Long createTime;
public Long getCreateTime() {return this.getLong("createTime");}
public void setCreateTime(Long createTime) {this.set("createTime",createTime);}
private Long updateTime;
public Long getUpdateTime() {return this.getLong("updateTime");}
public void setUpdateTime(Long updateTime) {this.set("updateTime",updateTime);}


}
