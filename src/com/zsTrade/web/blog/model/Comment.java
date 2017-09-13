//Powered By if, Since 2014 - 2020

package com.zsTrade.web.blog.model;

import java.util.Date;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;
/**
 * 
 * @author zs 2016-5-5 11:35:17
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */
@SuppressWarnings({ "unused"})
@Table(name="t_comment")
public class Comment extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String blogger;
	public String getBlogger() {return this.getString("blogger");}
	public void setBlogger(String blogger) {this.set("blogger",blogger);}
	private String title;
	public String getTitle() {return this.getString("title");}
	public void setTitle(String title) {this.set("title",title);}
	private Long bloggerId;
	public Long getBloggerId() {return this.getLong("bloggerId");}
	public void setBloggerId(Long bloggerId) {this.set("bloggerId",bloggerId);}
	private String userip;
	public String getUserip() {return this.getString("userip");}
	public void setUserip(String userip) {this.set("userip",userip);}
	private Long blogid;
	public Long getBlogid() {return this.getLong("blogid");}
	public void setBlogid(Long blogid) {this.set("blogid",blogid);}
	private String content;
	public String getContent() {return this.getString("content");}
	public void setContent(String content) {this.set("content",content);}
	private Date commentdate;
	public Date getCommentdate() {return this.getDate("commentdate");}
	public void setCommentdate(Date commentdate) {this.set("commentdate",commentdate);}
	private Integer state;
	public Integer getState() {return this.getInteger("state");}
	public void setState(Integer state) {this.set("state",state);}

}
