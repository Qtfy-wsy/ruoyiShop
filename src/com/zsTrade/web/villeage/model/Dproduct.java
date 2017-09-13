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
 * @author zsCat 2017-1-19 11:45:30
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@SuppressWarnings({ "unused"})
@Table(name="d_product")
public class Dproduct extends BaseEntity {

	private static final long serialVersionUID = 1L;

  		 private String title;
public String getTitle() {return this.getString("title");}
public void setTitle(String title) {this.set("title",title);}
private String tag;
public String getTag() {return this.getString("tag");}
public void setTag(String tag) {this.set("tag",tag);}
private String remark;
public String getRemark() {return this.getString("remark");}
public void setRemark(String remark) {this.set("remark",remark);}
private String summary;
public String getSummary() {return this.getString("summary");}
public void setSummary(String summary) {this.set("summary",summary);}
private Integer clickhit;
public Integer getClickhit() {return this.getInteger("clickhit");}
public void setClickhit(Integer clickhit) {this.set("clickhit",clickhit);}
private Long typeid;
public Long getTypeid() {return this.getLong("typeid");}
public void setTypeid(Long typeid) {this.set("typeid",typeid);}
private String img;
public String getImg() {return this.getString("img");}
public void setImg(String img) {this.set("img",img);}
private String typename;
public String getTypename() {return this.getString("typename");}
public void setTypename(String typename) {this.set("typename",typename);}
private Long type;
public Long getType() {return this.getLong("type");}
public void setType(Long type) {this.set("type",type);}
private String orderby;
public String getOrderby() {return this.getString("orderby");}
public void setOrderby(String orderby) {this.set("orderby",orderby);}
private String prices;
public String getPrices() {return this.getString("prices");}
public void setPrices(String prices) {this.set("prices",prices);}
private String imgmore;
public String getImgmore() {return this.getString("imgmore");}
public void setImgmore(String imgmore) {this.set("imgmore",imgmore);}
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
private Integer replyhit;
public Integer getReplyhit() {return this.getInteger("replyhit");}
public void setReplyhit(Integer replyhit) {this.set("replyhit",replyhit);}
private Integer sellhit;
public Integer getSellhit() {return this.getInteger("sellhit");}
public void setSellhit(Integer sellhit) {this.set("sellhit",sellhit);}
private Integer iscom;
public Integer getIscom() {return this.getInteger("iscom");}
public void setIscom(Integer iscom) {this.set("iscom",iscom);}
private Long villageid;
public Long getVillageid() {return this.getLong("villageid");}
public void setVillageid(Long villageid) {this.set("villageid",villageid);}


}
