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
import com.zsTrade.web.villeage.service.DproductService;
import com.zsTrade.web.villeage.mapper.DproductMapper;

import com.zsTrade.web.villeage.model.Dproduct;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-19 11:45:30
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Service("DproductService")
public class DproductServiceImpl  extends ServiceMybatis<Dproduct> implements DproductService {

	@Resource
	private DproductMapper DproductMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Dproduct
	 * @return
	 */
	public int saveDproduct(Dproduct Dproduct) {
		return this.save(Dproduct);
	}

	/**
	 * 删除
	* @param Dproduct
	* @return
	 */
	public int deleteDproduct(Dproduct Dproduct) {
		return this.delete(Dproduct);
	}

   @Override
	public PageInfo<Dproduct> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Dproduct> list = DproductMapper.findPageInfo(params);
		return new PageInfo<Dproduct>(list);
	}
}
