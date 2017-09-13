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
 * @author zsCat 2016-12-22 9:29:55
 * @Email: [email]
 * @version [version]
 *	项目类别管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_producttype")
public class ProductType extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String name;
public String getName() {return this.getString("name");}
public void setName(String name) {this.set("name",name);}
private Long createBy;
public Long getCreateBy() {return this.getLong("createBy");}
public void setCreateBy(Long createBy) {this.set("createBy",createBy);}
@DateTimeFormat( pattern = "yyyy-MM-dd" )
private Date createDate;
public Date getCreateDate() {return this.getDate("createDate");}
public void setCreateDate(Date createDate) {this.set("createDate",createDate);}
private String delFlag;
public String getDelFlag() {return this.getString("delFlag");}
public void setDelFlag(String delFlag) {this.set("delFlag",delFlag);}


}
