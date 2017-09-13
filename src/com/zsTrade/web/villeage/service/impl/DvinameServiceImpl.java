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
import com.zsTrade.web.villeage.service.DvinameService;
import com.zsTrade.web.villeage.mapper.DvinameMapper;

import com.zsTrade.web.villeage.model.Dviname;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-19 10:37:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Service("DvinameService")
public class DvinameServiceImpl  extends ServiceMybatis<Dviname> implements DvinameService {

	@Resource
	private DvinameMapper DvinameMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Dviname
	 * @return
	 */
	public int saveDviname(Dviname Dviname) {
		return this.save(Dviname);
	}

	/**
	 * 删除
	* @param Dviname
	* @return
	 */
	public int deleteDviname(Dviname Dviname) {
		return this.delete(Dviname);
	}

   @Override
	public PageInfo<Dviname> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Dviname> list = DvinameMapper.findPageInfo(params);
		return new PageInfo<Dviname>(list);
	}
}
