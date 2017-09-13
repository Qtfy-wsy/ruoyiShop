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
 * @author zsCat 2016-12-22 14:23:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层管理
 */
@SuppressWarnings({ "unused"})
@Table(name="t_floor")
public class Floor extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String advurl;
	public String getAdvurl() {return this.getString("advurl");}
	public void setAdvurl(String advurl) {this.set("advurl",advurl);}
	private String advimg;
	public String getAdvimg() {return this.getString("advimg");}
	public void setAdvimg(String advimg) {this.set("advimg",advimg);}
  		 private String name;
public String getName() {return this.getString("name");}
public void setName(String name) {this.set("name",name);}
private Integer typesort;
public Integer getTypesort() {return this.getInteger("typesort");}
public void setTypesort(Integer typesort) {this.set("typesort",typesort);}
private Long parentId;
public Long getParentId() {return this.getLong("parentId");}
public void setParentId(Long parentId) {this.set("parentId",parentId);}
private String parentIds;
public String getParentIds() {return this.getString("parentIds");}
public void setParentIds(String parentIds) {this.set("parentIds",parentIds);}
private String title;
public String getTitle() {return this.getString("title");}
public void setTitle(String title) {this.set("title",title);}
private String delFlag;
public String getDelFlag() {return this.getString("delFlag");}
public void setDelFlag(String delFlag) {this.set("delFlag",delFlag);}

@Transient
private Long[] goodsIds; //绑定的用户

public Long[] getGoodsIds(){
	return (Long[])this.get("goodsIds");
}

public void setGoodsIds(Long[] goodsIds){
	this.set("goodsIds", goodsIds);
}

}
