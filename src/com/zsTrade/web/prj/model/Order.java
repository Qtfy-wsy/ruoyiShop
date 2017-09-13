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
 * @author zsCat 2017-1-7 16:06:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_order")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private BigDecimal totalprice;
public BigDecimal getTotalprice() {return this.getBigDecimal("totalprice");}
public void setTotalprice(BigDecimal totalprice) {this.set("totalprice",totalprice);}
private int totalcount;
public int getTotalcount() {return this.getInteger("totalcount");}
public void setTotalcount(int totalcount) {this.set("totalcount",totalcount);}
private String ordersn;
public String getOrdersn() {return this.getString("ordersn");}
public void setOrdersn(String ordersn) {this.set("ordersn",ordersn);}
private int status;
public int getStatus() {return this.getInteger("status");}
public void setStatus(int status) {this.set("status",status);}
private Long userid;
public Long getUserid() {return this.getLong("userid");}
public void setUserid(Long userid) {this.set("userid",userid);}
@DateTimeFormat( pattern = "yyyy-MM-dd" )
private Date createdate;
public Date getCreatedate() {return this.getDate("createdate");}
public void setCreatedate(Date createdate) {this.set("createdate",createdate);}
private Long paymentid;
public Long getPaymentid() {return this.getLong("paymentid");}
public void setPaymentid(Long paymentid) {this.set("paymentid",paymentid);}
private Long addressid;
public Long getAddressid() {return this.getLong("addressid");}
public void setAddressid(Long addressid) {this.set("addressid",addressid);}
private String username;
public String getUsername() {return this.getString("username");}
public void setUsername(String username) {this.set("username",username);}

private String usercontent;
public String getUsercontent() {return this.getString("usercontent");}
public void setUsercontent(String usercontent) {this.set("usercontent",usercontent);}
}
