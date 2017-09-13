/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.sys.mapper.SysUserMapper;
import com.zsTrade.web.sys.model.SysUser;
import com.zsTrade.web.sys.utils.SysUserUtils;
import com.zsTrade.web.villeage.service.DvillageService;
import com.zsTrade.web.villeage.mapper.DvillageMapper;
import com.zsTrade.web.villeage.model.Dvillage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-19 10:17:13
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Service("DvillageService")
public class DvillageServiceImpl  extends ServiceMybatis<Dvillage> implements DvillageService {

	@Resource
	private DvillageMapper DvillageMapper;
	@Resource
	private SysUserMapper SysUserMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Dvillage
	 * @return
	 */
	public int saveDvillage(Dvillage Dvillage) {
		int count = 0;
		if (Dvillage.get("id") == null) {
			count = this.insertSelective(Dvillage);
			SysUser u=SysUserUtils.getCacheLoginUser();
			u.setVillageid(Dvillage.getId());
			SysUserMapper.updateByPrimaryKeySelective(u);
		} else {
			count = this.updateByPrimaryKeySelective(Dvillage);
		}
		return count;
	}

	/**
	 * 删除
	* @param Dvillage
	* @return
	 */
	public int deleteDvillage(Dvillage Dvillage) {
		return this.delete(Dvillage);
	}

   @Override
	public PageInfo<Dvillage> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Dvillage> list = DvillageMapper.findPageInfo(params);
		return new PageInfo<Dvillage>(list);
	}
}
