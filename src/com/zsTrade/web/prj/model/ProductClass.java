//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.prj.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zsCat 2016-12-22 11:28:44
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	test管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_product_class")
public class ProductClass extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String name;
public String getName() {return this.getString("name");}
public void setName(String name) {this.set("name",name);}
private String pic;
public String getPic() {return this.getString("pic");}
public void setPic(String pic) {this.set("pic",pic);}
private String delFlag;
public String getDelFlag() {return this.getString("delFlag");}
public void setDelFlag(String delFlag) {this.set("delFlag",delFlag);}
private String title;
public String getTitle() {return this.getString("title");}
public void setTitle(String title) {this.set("title",title);}
private Long parentId;
public Long getParentId() {return this.getLong("parentId");}
public void setParentId(Long parentId) {this.set("parentId",parentId);}
private String parentIds;
public String getParentIds() {return this.getString("parentIds");}
public void setParentIds(String parentIds) {this.set("parentIds",parentIds);}
@Transient
private String oldParentIds; //旧的pids,非表中字段，用作更新用
public String getOldParentIds() { return this.getString("oldParentIds"); }
 public void setOldParentIds(String oldParentIds) { this.set("oldParentIds", oldParentIds); }


}
