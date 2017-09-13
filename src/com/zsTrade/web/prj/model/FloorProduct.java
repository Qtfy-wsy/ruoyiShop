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
 * @author zsCat 2016-12-22 14:23:22
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层产品管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_floor_product")
public class FloorProduct extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long productid;
public Long getProductid() {return this.getLong("productid");}
public void setProductid(Long productid) {this.set("productid",productid);}
private Long floorid;
public Long getFloorid() {return this.getLong("floorid");}
public void setFloorid(Long floorid) {this.set("floorid",floorid);}


}
