//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-10-31 20:30:31
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	基础管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_adv_position")
public class AdvPosition extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String apName;
public String getApName() {return this.getString("apName");}
public void setApName(String apName) {this.set("apName",apName);}
private String apIntro;
public String getApIntro() {return this.getString("apIntro");}
public void setApIntro(String apIntro) {this.set("apIntro",apIntro);}
private Long apClass;
public Long getApClass() {return this.getLong("apClass");}
public void setApClass(Long apClass) {this.set("apClass",apClass);}
private Long apDisplay;
public Long getApDisplay() {return this.getLong("apDisplay");}
public void setApDisplay(Long apDisplay) {this.set("apDisplay",apDisplay);}
private Long isUse;
public Long getIsUse() {return this.getLong("isUse");}
public void setIsUse(Long isUse) {this.set("isUse",isUse);}
private Integer apWidth;
public Integer getApWidth() {return this.getInteger("apWidth");}
public void setApWidth(Integer apWidth) {this.set("apWidth",apWidth);}
private Integer apHeight;
public Integer getApHeight() {return this.getInteger("apHeight");}
public void setApHeight(Integer apHeight) {this.set("apHeight",apHeight);}
private String apKey;
public String getApKey() {return this.getString("apKey");}
public void setApKey(String apKey) {this.set("apKey",apKey);}


}
