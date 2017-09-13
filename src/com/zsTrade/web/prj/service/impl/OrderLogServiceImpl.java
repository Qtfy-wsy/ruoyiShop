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
import com.zsTrade.web.prj.service.OrderLogService;
import com.zsTrade.web.prj.mapper.OrderLogMapper;

import com.zsTrade.web.prj.model.OrderLog;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-7 16:57:31
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单日志管理
 */
@Service("OrderLogService")
public class OrderLogServiceImpl  extends ServiceMybatis<OrderLog> implements OrderLogService {

	@Resource
	private OrderLogMapper OrderLogMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param OrderLog
	 * @return
	 */
	public int saveOrderLog(OrderLog OrderLog) {
		return this.save(OrderLog);
	}

	/**
	 * 删除
	* @param OrderLog
	* @return
	 */
	public int deleteOrderLog(OrderLog OrderLog) {
		return this.delete(OrderLog);
	}

   @Override
	public PageInfo<OrderLog> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<OrderLog> list = OrderLogMapper.findPageInfo(params);
		return new PageInfo<OrderLog>(list);
	}
}
