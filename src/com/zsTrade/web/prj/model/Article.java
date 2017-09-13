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
 * @author zsCat 2016-12-22 16:15:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	文章管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_article")
public class Article extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String title;
public String getTitle() {return this.getString("title");}
public void setTitle(String title) {this.set("title",title);}
private String type;
public String getType() {return this.getString("type");}
public void setType(String type) {this.set("type",type);}
private String remark;
public String getRemark() {return this.getString("remark");}
public void setRemark(String remark) {this.set("remark",remark);}
private String delFlag;
public String getDelFlag() {return this.getString("delFlag");}
public void setDelFlag(String delFlag) {this.set("delFlag",delFlag);}
private String name;
public String getName() {return this.getString("name");}
public void setName(String name) {this.set("name",name);}


}
