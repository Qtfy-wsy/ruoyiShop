//Powered By if, Since 2014 - 2020

package com.zsTrade.web.blog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.zsTrade.common.base.BaseEntity;


/**
 * 
 * @author zs 2016-5-5 11:33:51
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */
@SuppressWarnings({ "unused"})
@Table(name="t_blog")
public class Blog extends BaseEntity {

	@Transient
	BlogType blogType;
	
	public BlogType getBlogType() {
		return blogType;
	}

	public void setBlogType(BlogType blogType) {
		this.blogType = blogType;
	}

	private String img;

	public String getImg() {
		return this.getString("img");
	}

	public void setImg(String img) {
		this.set("img", img);
	}
	
	private static final long serialVersionUID = 1L;
	private String title;
	public String getTitle() {return this.getString("title");}
	public void setTitle(String title) {this.set("title",title);}
	private String summary;
	public String getSummary() {return this.getString("summary");}
	public void setSummary(String summary) {this.set("summary",summary);}
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
	private Long typeid;
	public Long getTypeid() {return this.getLong("typeid");}
	public void setTypeid(Long typeid) {this.set("typeid",typeid);}
	private String typename;
	public String getTypename() {return this.getString("typename");}
	public void setTypename(String typename) {this.set("typename",typename);}
	
	private Long bloggerId;
	public Long getBloggerId() {return this.getLong("bloggerId");}
	public void setBloggerId(Long bloggerId) {this.set("bloggerId",bloggerId);}
	private String keyword;
	public String getKeyword() {return this.getString("keyword");}
	public void setKeyword(String keyword) {this.set("keyword",keyword);}
   
	@Transient
	private List<String> imagesList=new LinkedList<String>(); // 博客里存在的图片 主要用于列表展示显示缩略图
	@Transient
	private String contentNoTag; // 博客内容 无网页标签 Lucene分词用
	@Transient
	private String releaseDateStr; // 发布日期字符串 只取年和月
	
	
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	public List<String> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	private Integer state;
	public Integer getState() {return this.getInteger("state");}
	public void setState(Integer state) {this.set("state",state);}
}
