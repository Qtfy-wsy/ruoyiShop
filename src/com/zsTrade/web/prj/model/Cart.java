/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.model;

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
 * @author zsCat 2017-1-7 15:58:43
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_cart")
public class Cart extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long goodsid;
public Long getGoodsid() {return this.getLong("goodsid");}
public void setGoodsid(Long goodsid) {this.set("goodsid",goodsid);}
private Long userid;
public Long getUserid() {return this.getLong("userid");}
public void setUserid(Long userid) {this.set("userid",userid);}
private String goodsname;
public String getGoodsname() {return this.getString("goodsname");}
public void setGoodsname(String goodsname) {this.set("goodsname",goodsname);}
private String price;
public String getPrice() {return this.getString("price");}
public void setPrice(String string) {this.set("price",string);}
private Integer count;
public Integer getCount() {return this.getInteger("count");}
public void setCount(Integer count) {this.set("count",count);}
private String img;
public String getImg() {return this.getString("img");}
public void setImg(String img) {this.set("img",img);}

}
