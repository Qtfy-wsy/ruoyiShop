//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-10-31 20:30:14
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	基础管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_adv")
public class Adv extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long apId;
public Long getApId() {return this.getLong("apId");}
public void setApId(Long apId) {this.set("apId",apId);}
private String advTitle;
public String getAdvTitle() {return this.getString("advTitle");}
public void setAdvTitle(String advTitle) {this.set("advTitle",advTitle);}
private String advUrl;
public String getAdvUrl() {return this.getString("advUrl");}
public void setAdvUrl(String advUrl) {this.set("advUrl",advUrl);}
private Long startDate;
public Long getStartDate() {return this.getLong("startDate");}
public void setStartDate(Long startDate) {this.set("startDate",startDate);}
private Long endDate;
public Long getEndDate() {return this.getLong("endDate");}
public void setEndDate(Long endDate) {this.set("endDate",endDate);}
private Long sort;
public Long getSort() {return this.getLong("sort");}
public void setSort(Long sort) {this.set("sort",sort);}
private Long clickNum;
public Long getClickNum() {return this.getLong("clickNum");}
public void setClickNum(Long clickNum) {this.set("clickNum",clickNum);}
private Long isAllow;
public Long getIsAllow() {return this.getLong("isAllow");}
public void setIsAllow(Long isAllow) {this.set("isAllow",isAllow);}
private String resUrl;
public String getResUrl() {return this.getString("resUrl");}
public void setResUrl(String resUrl) {this.set("resUrl",resUrl);}


}
