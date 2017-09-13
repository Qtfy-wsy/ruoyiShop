package com.zsTrade.web.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.zsTrade.common.base.PigResult;
import com.zsTrade.common.constant.Constant;
import com.zsTrade.common.utils.DateUtils;
import com.zsTrade.common.utils.IPUtils;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.prj.service.ProductService;
import com.zsTrade.web.sys.model.SysUser;
import com.zsTrade.web.sys.service.SysResourceService;
import com.zsTrade.web.sys.service.SysUserService;
import com.zsTrade.web.sys.utils.SysUserUtils;

@Controller
public class LoginController {

	@Resource
	private SysResourceService sysResourceService;
	@Resource
	private SysUserService sysUserService;
	
	
	/**
	 * 管理主页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/index")
	public String toIndex(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("code"); // 清除code
		if( SysUserUtils.getSessionLoginUser() == null || 
				SysUserUtils.getCacheLoginUser() ==null ){
			return "redirect:/login";
		}
		model.addAttribute("menuList", SysUserUtils.getUserMenus());
		return "index";
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String toLogin() {
		if( SysUserUtils.getSessionLoginUser() != null && SysUserUtils.getCacheLoginUser() !=null ){
			return "redirect:/index";
		}
		return "login";
	}
	
	/**
	 * 登录验证
	 * 
	 * @param username
	 * @param password
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkLogin(String username,
			String password, String code, HttpServletRequest request) {

		Map<String, Object> msg = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		code = StringUtils.trim(code);
		username = StringUtils.trim(username);
		password = StringUtils.trim(password);
		Object scode = session.getAttribute("code");
		String sessionCode = null;
		if (scode != null)
			sessionCode = scode.toString();
		if (!StringUtils.equalsIgnoreCase(code, sessionCode)) {
			msg.put("error", "验证码错误");
			return msg;
		}
		SysUser user = sysUserService.checkUser(username, password);
		if (null != user) {
			if(user.getDelFlag().equals("1")){
				msg.put("error", "户名已经被禁用");
			}else{
				session.setAttribute(Constant.SESSION_LOGIN_USER, user);
				
				//缓存用户
				SysUserUtils.cacheLoginUser(user);
				
				//设置并缓存用户认证
				SysUserUtils.setUserAuth();
				
				//TODO 暂时，后续移动到日志中
				//更新用户最后登录ip和date
				SysUser newUser = new SysUser();
				newUser.setLoginDate(new Date());
				newUser.setLoginIp(IPUtils.getClientAddress(request));
				newUser.setId(user.getId());
				sysUserService.updateByPrimaryKeySelective(newUser);
			}
		} else {
			msg.put("error", "用户名或密码错误");
		}
		return msg;
	}

	/**
	 * 用户退出
	 * 
	 * @return 跳转到登录页面
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		SysUserUtils.clearCacheUser(SysUserUtils.getSessionLoginUser().getId());
		request.getSession().invalidate();
		return "login";
	}
	
	@RequestMapping("notauth")
	public String notAuth(){
		return "notauth";
	}
	
	@RequestMapping("notlogin")
	public String notLogin(){
		return "notlogin";
	}

	@RequestMapping(value="chartIndex")
	public @ResponseBody PigResult chart(){
		   Option option = this.selectRemoveCauses();   
		    return new PigResult().build(0, "sucess", option);
	}
	@Resource
	private ProductService ProductService;
	public Option selectRemoveCauses()  {  
	  
	    //数据库查询获取统计数据  
	    List<Product> list = ProductService.select(new Product());
	   
	    //创建Option  
	    Option option = new Option();  
	    option.title().text("平台预约统计").subtext("预约");
        option.tooltip().trigger(Trigger.axis);
        option.legend("预约量");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
       
        option.yAxis(new ValueAxis());

        Bar bar = new Bar("预约量");
        
        bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        CategoryAxis category=new CategoryAxis();
        for(Product ll :list){
        	bar.data(ll.getClickhit());
        	category.data(ll.getTitle());
        }
        if(list ==null || list.size()==0){
        	bar.data(0);
        	category.data(0);
        }
       // bar.data(2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3);
        option.xAxis(category);
        option.series(bar);
	    return option;  
	}
}
