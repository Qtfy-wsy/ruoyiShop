/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.model;

import java.util.Date;
import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2017-1-19 10:17:13
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@SuppressWarnings({ "unused"})
@Table(name="d_village")
public class Dvillage extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String name;
public String getName() {return this.getString("name");}
public void setName(String name) {this.set("name",name);}
private String address;
public String getAddress() {return this.getString("address");}
public void setAddress(String address) {this.set("address",address);}
private String phone;
public String getPhone() {return this.getString("phone");}
public void setPhone(String phone) {this.set("phone",phone);}
private Long userid;
public Long getUserid() {return this.getLong("userid");}
public void setUserid(Long userid) {this.set("userid",userid);}
private Integer hit;
public Integer getHit() {return this.getInteger("hit");}
public void setHit(Integer hit) {this.set("hit",hit);}
private String postcpde;
public String getPostcpde() {return this.getString("postcpde");}
public void setPostcpde(String postcpde) {this.set("postcpde",postcpde);}
private String img;
public String getImg() {return this.getString("img");}
public void setImg(String img) {this.set("img",img);}
private String orderby;
public String getOrderby() {return this.getString("orderby");}
public void setOrderby(String orderby) {this.set("orderby",orderby);}
private Integer stat;
public Integer getStat() {return this.getInteger("stat");}
public void setStat(Integer stat) {this.set("stat",stat);}
}
