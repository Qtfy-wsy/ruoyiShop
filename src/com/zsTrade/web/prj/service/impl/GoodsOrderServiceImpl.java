/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service.impl;

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
import com.zsTrade.web.prj.service.GoodsOrderService;
import com.zsTrade.web.prj.mapper.GoodsOrderMapper;

import com.zsTrade.web.prj.model.GoodsOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-7 16:07:35
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	商品订单管理
 */
@Service("GoodsOrderService")
public class GoodsOrderServiceImpl  extends ServiceMybatis<GoodsOrder> implements GoodsOrderService {

	@Resource
	private GoodsOrderMapper GoodsOrderMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param GoodsOrder
	 * @return
	 */
	public int saveGoodsOrder(GoodsOrder GoodsOrder) {
		return this.save(GoodsOrder);
	}

	/**
	 * 删除
	* @param GoodsOrder
	* @return
	 */
	public int deleteGoodsOrder(GoodsOrder GoodsOrder) {
		return this.delete(GoodsOrder);
	}

   @Override
	public PageInfo<GoodsOrder> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<GoodsOrder> list = GoodsOrderMapper.findPageInfo(params);
		return new PageInfo<GoodsOrder>(list);
	}
}
