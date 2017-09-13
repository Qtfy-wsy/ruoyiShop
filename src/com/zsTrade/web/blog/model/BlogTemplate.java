//Powered By if, Since 2014 - 2020

package com.zsTrade.web.blog.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.zsTrade.common.base.BaseEntity;



/**
 * 
 * @author zsCat 2016-10-28 10:30:31
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的官网
 */
@SuppressWarnings({ "unused"})
@Table(name="t_blog_template")
public class BlogTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Transient
	private String img;

	public String getImg() {
		return this.getString("img");
	}

	public void setImg(String img) {
		this.set("img", img);
	}
  		 private String title;
public String getTitle() {return this.getString("title");}
public void setTitle(String title) {this.set("title",title);}
private String summary;
public String getSummary() {return this.getString("summary");}
public void setSummary(String summary) {this.set("summary",summary);}
@DateTimeFormat( pattern = "yyyy-MM-dd" )
private Date releasedate;
public Date getReleasedate() {return this.getDate("releasedate");}
public void setReleasedate(Date releasedate) {this.set("releasedate",releasedate);}
private Integer clickhit;
public Integer getClickhit() {return this.getInteger("clickhit");}
public void setClickhit(Integer clickhit) {this.set("clickhit",clickhit);}
private Integer replyhit;
public Integer getReplyhit() {return this.getInteger("replyhit");}
public void setReplyhit(Integer replyhit) {this.set("replyhit",replyhit);}
private String content;
public String getContent() {return this.getString("content");}
public void setContent(String content) {this.set("content",content);}
private String keyword;
public String getKeyword() {return this.getString("keyword");}
public void setKeyword(String keyword) {this.set("keyword",keyword);}


}
