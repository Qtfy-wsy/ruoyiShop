//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-10-31 20:31:15
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	基础管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_express")
public class Express extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String eName;
public String getEName() {return this.getString("eName");}
public void setEName(String eName) {this.set("eName",eName);}
private Integer eState;
public Integer getEState() {return this.getInteger("eState");}
public void setEState(Integer eState) {this.set("eState",eState);}
private String eCode;
public String getECode() {return this.getString("eCode");}
public void setECode(String eCode) {this.set("eCode",eCode);}
private String eLetter;
public String getELetter() {return this.getString("eLetter");}
public void setELetter(String eLetter) {this.set("eLetter",eLetter);}
private Integer eOrder;
public Integer getEOrder() {return this.getInteger("eOrder");}
public void setEOrder(Integer eOrder) {this.set("eOrder",eOrder);}
private String eUrl;
public String getEUrl() {return this.getString("eUrl");}
public void setEUrl(String eUrl) {this.set("eUrl",eUrl);}
private Integer isDel;
public Integer getIsDel() {return this.getInteger("isDel");}
public void setIsDel(Integer isDel) {this.set("isDel",isDel);}


}
