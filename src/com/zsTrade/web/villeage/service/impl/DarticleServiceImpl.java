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
import com.zsTrade.web.villeage.service.DarticleService;
import com.zsTrade.web.villeage.mapper.DarticleMapper;

import com.zsTrade.web.villeage.model.Darticle;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-19 10:16:56
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Service("DarticleService")
public class DarticleServiceImpl  extends ServiceMybatis<Darticle> implements DarticleService {

	@Resource
	private DarticleMapper DarticleMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Darticle
	 * @return
	 */
	public int saveDarticle(Darticle Darticle) {
		return this.save(Darticle);
	}

	/**
	 * 删除
	* @param Darticle
	* @return
	 */
	public int deleteDarticle(Darticle Darticle) {
		return this.delete(Darticle);
	}

   @Override
	public PageInfo<Darticle> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Darticle> list = DarticleMapper.findPageInfo(params);
		return new PageInfo<Darticle>(list);
	}
}
