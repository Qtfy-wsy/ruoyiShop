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
 * @author zsCat 2017-1-7 16:57:31
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单日志管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_order_log")
public class OrderLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long orderId;
public Long getOrderId() {return this.getLong("orderId");}
public void setOrderId(Long orderId) {this.set("orderId",orderId);}
private String orderState;
public String getOrderState() {return this.getString("orderState");}
public void setOrderState(String orderState) {this.set("orderState",orderState);}
private String changeState;
public String getChangeState() {return this.getString("changeState");}
public void setChangeState(String changeState) {this.set("changeState",changeState);}
private String stateInfo;
public String getStateInfo() {return this.getString("stateInfo");}
public void setStateInfo(String stateInfo) {this.set("stateInfo",stateInfo);}
private Long createTime;
public Long getCreateTime() {return this.getLong("createTime");}
public void setCreateTime(Long createTime) {this.set("createTime",createTime);}
private String operator;
public String getOperator() {return this.getString("operator");}
public void setOperator(String operator) {this.set("operator",operator);}


}
