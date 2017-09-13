//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-11-1 14:47:36
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	优惠劵管理
 */
@SuppressWarnings({ "unused"})
@Table(name="shop_consult")
public class Consult extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long goodsId;
public Long getGoodsId() {return this.getLong("goodsId");}
public void setGoodsId(Long goodsId) {this.set("goodsId",goodsId);}
private String cgoodsName;
public String getCgoodsName() {return this.getString("cgoodsName");}
public void setCgoodsName(String cgoodsName) {this.set("cgoodsName",cgoodsName);}
private Long memberId;
public Long getMemberId() {return this.getLong("memberId");}
public void setMemberId(Long memberId) {this.set("memberId",memberId);}
private String cmemberName;
public String getCmemberName() {return this.getString("cmemberName");}
public void setCmemberName(String cmemberName) {this.set("cmemberName",cmemberName);}
private Long storeId;
public Long getStoreId() {return this.getLong("storeId");}
public void setStoreId(Long storeId) {this.set("storeId",storeId);}
private String email;
public String getEmail() {return this.getString("email");}
public void setEmail(String email) {this.set("email",email);}
private String consultContent;
public String getConsultContent() {return this.getString("consultContent");}
public void setConsultContent(String consultContent) {this.set("consultContent",consultContent);}
private Long consultAddtime;
public Long getConsultAddtime() {return this.getLong("consultAddtime");}
public void setConsultAddtime(Long consultAddtime) {this.set("consultAddtime",consultAddtime);}
private String consultReply;
public String getConsultReply() {return this.getString("consultReply");}
public void setConsultReply(String consultReply) {this.set("consultReply",consultReply);}
private Long consultReplyTime;
public Long getConsultReplyTime() {return this.getLong("consultReplyTime");}
public void setConsultReplyTime(Long consultReplyTime) {this.set("consultReplyTime",consultReplyTime);}
private Integer isanonymous;
public Integer getIsanonymous() {return this.getInteger("isanonymous");}
public void setIsanonymous(Integer isanonymous) {this.set("isanonymous",isanonymous);}
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
