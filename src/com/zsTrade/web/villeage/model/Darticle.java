/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.model;

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
 * @author zsCat 2017-1-19 10:16:56
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@SuppressWarnings({ "unused"})
@Table(name="d_article")
public class Darticle extends BaseEntity {

	private static final long serialVersionUID = 1L;

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
private Integer state;
public Integer getState() {return this.getInteger("state");}
public void setState(Integer state) {this.set("state",state);}
private Long userid;
public Long getUserid() {return this.getLong("userid");}
public void setUserid(Long userid) {this.set("userid",userid);}
private String img;
public String getImg() {return this.getString("img");}
public void setImg(String img) {this.set("img",img);}
private Long villageid;
public Long getVillageid() {return this.getLong("villageid");}
public void setVillageid(Long villageid) {this.set("villageid",villageid);}
private String username;
public String getUsername() {return this.getString("username");}
public void setUsername(String username) {this.set("username",username);}


}
