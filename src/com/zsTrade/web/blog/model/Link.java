//Powered By if, Since 2014 - 2020

package com.zsTrade.web.blog.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;

/**
 * 
 * @author zs 2016-5-5 11:35:57
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */
@SuppressWarnings({ "unused"})
@Table(name="t_link")
public class Link extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String linkname;
	public String getLinkname() {return this.getString("linkname");}
	public void setLinkname(String linkname) {this.set("linkname",linkname);}
	private String linkurl;
	public String getLinkurl() {return this.getString("linkurl");}
	public void setLinkurl(String linkurl) {this.set("linkurl",linkurl);}
	private Integer orderno;
	public Integer getOrderno() {return this.getInteger("orderno");}
	public void setOrderno(Integer orderno) {this.set("orderno",orderno);}

}
