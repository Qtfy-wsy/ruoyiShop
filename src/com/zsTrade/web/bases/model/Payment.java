//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-11-2 13:20:51
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	优惠劵管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_Payment")
public class Payment extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String paymentCode;
public String getPaymentCode() {return this.getString("paymentCode");}
public void setPaymentCode(String paymentCode) {this.set("paymentCode",paymentCode);}
private String paymentName;
public String getPaymentName() {return this.getString("paymentName");}
public void setPaymentName(String paymentName) {this.set("paymentName",paymentName);}
private String paymentConfig;
public String getPaymentConfig() {return this.getString("paymentConfig");}
public void setPaymentConfig(String paymentConfig) {this.set("paymentConfig",paymentConfig);}
private String paymentState;
public String getPaymentState() {return this.getString("paymentState");}
public void setPaymentState(String paymentState) {this.set("paymentState",paymentState);}
private Integer isDel;
public Integer getIsDel() {return this.getInteger("isDel");}
public void setIsDel(Integer isDel) {this.set("isDel",isDel);}
private Long createTime;
public Long getCreateTime() {return this.getLong("createTime");}
public void setCreateTime(Long createTime) {this.set("createTime",createTime);}
private Long updateTime;
public Long getUpdateTime() {return this.getLong("updateTime");}
public void setUpdateTime(Long updateTime) {this.set("updateTime",updateTime);}
private String paymentLogo;
public String getPaymentLogo() {return this.getString("paymentLogo");}
public void setPaymentLogo(String paymentLogo) {this.set("paymentLogo",paymentLogo);}


}
