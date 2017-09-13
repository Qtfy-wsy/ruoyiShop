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
 * @author zsCat 2017-1-7 16:15:50
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_reply")
public class Reply extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private Long goodsid;
public Long getGoodsid() {return this.getLong("goodsid");}
public void setGoodsid(Long goodsid) {this.set("goodsid",goodsid);}
private String content;
public String getContent() {return this.getString("content");}
public void setContent(String content) {this.set("content",content);}
@DateTimeFormat( pattern = "yyyy-MM-dd" )
private Date createdate;
public Date getCreatedate() {return this.getDate("createdate");}
public void setCreatedate(Date createdate) {this.set("createdate",createdate);}
private Integer status;
public Integer getStatus() {return this.getInteger("status");}
public void setStatus(Integer status) {this.set("status",status);}
private Long userid;
public Long getUserid() {return this.getLong("userid");}
public void setUserid(Long userid) {this.set("userid",userid);}
private String username;
public String getUsername() {return this.getString("username");}
public void setUsername(String username) {this.set("username",username);}


}
