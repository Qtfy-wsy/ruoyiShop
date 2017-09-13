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
 * @author zsCat 2017-1-19 10:37:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@SuppressWarnings({ "unused"})
@Table(name="d_viname")
public class Dviname extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer clickhit;
	public Integer getClickhit() {return this.getInteger("clickhit");}
	public void setClickhit(Integer clickhit) {this.set("clickhit",clickhit);}

	private String img;
	public String getImg() {return this.getString("img");}
	public void setImg(String img) {this.set("img",img);}
  		 private String name;
public String getName() {return this.getString("name");}
public void setName(String name) {this.set("name",name);}
private Integer age;
public Integer getAge() {return this.getInteger("age");}
public void setAge(Integer age) {this.set("age",age);}
private String heigth;
public String getHeigth() {return this.getString("heigth");}
public void setHeigth(String heigth) {this.set("heigth",heigth);}
private String weight;
public String getWeight() {return this.getString("weight");}
public void setWeight(String weight) {this.set("weight",weight);}
private String content;
public String getContent() {return this.getString("content");}
public void setContent(String content) {this.set("content",content);}
private Integer type;
public Integer getType() {return this.getInteger("type");}
public void setType(Integer type) {this.set("type",type);}
private Integer orderno;
public Integer getOrderno() {return this.getInteger("orderno");}
public void setOrderno(Integer orderno) {this.set("orderno",orderno);}
private Integer stat;
public Integer getStat() {return this.getInteger("stat");}
public void setStat(Integer stat) {this.set("stat",stat);}
private Long villageid;
public Long getVillageid() {return this.getLong("villageid");}
public void setVillageid(Long villageid) {this.set("villageid",villageid);}


}
