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
 * @author zsCat 2017-1-7 16:07:35
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	商品订单管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_goodsorder")
public class GoodsOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long goodsid;
public Long getGoodsid() {return this.getLong("goodsid");}
public void setGoodsid(Long goodsid) {this.set("goodsid",goodsid);}
private Long orderid;
public Long getOrderid() {return this.getLong("orderid");}
public void setOrderid(Long orderid) {this.set("orderid",orderid);}


}
