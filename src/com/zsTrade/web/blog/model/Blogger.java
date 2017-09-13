//Powered By if, Since 2014 - 2020


package com.zsTrade.web.blog.model;

import javax.persistence.Table;

import com.zsTrade.common.base.BaseEntity;

/**
 * 
 * @author zs 2016-5-5 11:34:28
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */
@SuppressWarnings({ "unused"})
@Table(name="t_blogger")
public class Blogger extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String username;
	public String getUsername() {return this.getString("username");}
	public void setUsername(String username) {this.set("username",username);}
	private String password;
	public String getPassword() {return this.getString("password");}
	public void setPassword(String password) {this.set("password",password);}
	private String profile;
	public String getProfile() {return this.getString("profile");}
	public void setProfile(String profile) {this.set("profile",profile);}
	private String nickname;
	public String getNickname() {return this.getString("nickname");}
	public void setNickname(String nickname) {this.set("nickname",nickname);}
	private String sign;
	public String getSign() {return this.getString("sign");}
	public void setSign(String sign) {this.set("sign",sign);}
	private String imagename;
	public String getImagename() {return this.getString("imagename");}
	public void setImagename(String imagename) {this.set("imagename",imagename);}
   

}
