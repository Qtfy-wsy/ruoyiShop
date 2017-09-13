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
import com.zsTrade.web.villeage.service.DproducttypeService;
import com.zsTrade.web.villeage.mapper.DproducttypeMapper;

import com.zsTrade.web.villeage.model.Dproducttype;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-19 11:45:49
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Service("DproducttypeService")
public class DproducttypeServiceImpl  extends ServiceMybatis<Dproducttype> implements DproducttypeService {

	@Resource
	private DproducttypeMapper DproducttypeMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Dproducttype
	 * @return
	 */
	public int saveDproducttype(Dproducttype Dproducttype) {
		return this.save(Dproducttype);
	}

	/**
	 * 删除
	* @param Dproducttype
	* @return
	 */
	public int deleteDproducttype(Dproducttype Dproducttype) {
		return this.delete(Dproducttype);
	}

   @Override
	public PageInfo<Dproducttype> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Dproducttype> list = DproducttypeMapper.findPageInfo(params);
		return new PageInfo<Dproducttype>(list);
	}
}
