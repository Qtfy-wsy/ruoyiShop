//Powered By if, Since 2014 - 2020


package com.zsTrade.web.blog.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;




/**
 * 
 * @author zs 2016-5-5 11:32:31
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */
@SuppressWarnings({ "unused"})
@Table(name="t_blogtype")
public class BlogType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String typename;
	public String getTypename() {return this.getString("typename");}
	public void setTypename(String typename) {this.set("typename",typename);}
	private Integer orderno;
	public Integer getOrderno() {return this.getInteger("orderno");}
	public void setOrderno(Integer orderno) {this.set("orderno",orderno);}

}
