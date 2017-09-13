//Powered By if, Since 2014 - 2020

package com.zsTrade.web.sys.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.common.constant.Constant;
import com.zsTrade.common.utils.CacheUtils;
import com.zsTrade.common.utils.PasswordEncoder;
import com.zsTrade.web.sys.mapper.SysOfficeMapper;
import com.zsTrade.web.sys.mapper.SysRoleMapper;
import com.zsTrade.web.sys.mapper.SysUserMapper;
import com.zsTrade.web.sys.model.SysOffice;
import com.zsTrade.web.sys.model.SysUser;
import com.zsTrade.web.sys.utils.SysUserUtils;

/**
 * 
 * @author 
 */

@Service("sysUserService")
public class SysUserService extends ServiceMybatis<SysUser>{

	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private SysOfficeMapper sysOfficeMapper;
	
	public int insertUserRoleByUserId(SysUser sysUser){
		return sysRoleMapper.insertUserRoleByUserId(sysUser);
	}
	/**
	 * 添加或更新用户
	* @param sysUser
	* @return
	 */
	public int saveSysUser(SysUser sysUser){
		int count = 0;
		SysOffice sysOffice = sysOfficeMapper.findOfficeCompanyIdByDepId(sysUser.getOfficeId());
		Long companyId = sysUser.getOfficeId();
		if(sysOffice != null){
			companyId = sysOffice.getId();
		}
		sysUser.setCompanyId(companyId);
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			String encryptPwd = PasswordEncoder.encrypt(sysUser.getPassword(), sysUser.getUsername());
			sysUser.setPassword(encryptPwd);
		}else{
			sysUser.remove("password");
		}
		if(null == sysUser.getId()){
			count = this.insertSelective(sysUser);
		}else{
			sysRoleMapper.deleteUserRoleByUserId(sysUser.getId());
			count = this.updateByPrimaryKeySelective(sysUser);
			//清除缓存
			SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(sysUser.getId()));
			if(CacheUtils.isCacheByKey(Constant.CACHE_SYS_USER, sysUser.getId().toString())){
				String userType = this.selectByPrimaryKey(sysUser.getId()).getUserType();
				sysUser.setUserType(userType);
				SysUserUtils.cacheLoginUser(sysUser);
			}
		}
		if(sysUser.getRoleIds()!=null) sysRoleMapper.insertUserRoleByUserId(sysUser);
		return count;
	}
	
	/**
	 * 删除用户
	* @param userId
	* @return
	 */
	public int deleteUser(Long userId){
		sysRoleMapper.deleteUserRoleByUserId(userId);
		SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(userId));
		SysUserUtils.clearCacheUser(userId);
		return this.updateDelFlagToDelStatusById(SysUser.class, userId);
	}
	
	/**
	 * 用户列表
	* @param params
	* @return
	 */
	public PageInfo<SysUser> findPageInfo(Map<String, Object> params) {
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
		params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		return new PageInfo<SysUser>(list);
	}
	
	/**
	 * 验证用户
	* @param username 用户名
	* @param password 密码
	* @return user
	 */
	public SysUser checkUser(String username,String password){
		SysUser sysUser = new SysUser();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
	//	sysUser.setDelFlag(Constant.DEL_FLAG_NORMAL);
		List<SysUser> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}

	public SysUser selectByClientId(String licenseCode,String id) {
		SysUser sysUser = new SysUser();
		sysUser.setLicenseCode(licenseCode);
		sysUser.setId(Long.parseLong(id));
		return sysUserMapper.selectOne(sysUser);
	}
	
}
