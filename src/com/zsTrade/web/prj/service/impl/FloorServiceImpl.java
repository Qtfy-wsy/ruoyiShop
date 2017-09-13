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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.prj.service.FloorService;
import com.zsTrade.web.prj.mapper.FloorMapper;
import com.zsTrade.web.prj.model.Floor;
import com.zsTrade.web.sys.utils.SysUserUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2016-12-22 14:23:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层管理
 */
@Service("FloorService")
public class FloorServiceImpl  extends ServiceMybatis<Floor> implements FloorService {

	@Resource
	private FloorMapper FloorMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Floor
	 * @return
	 */
	public int saveFloor(Floor Floor) {
		return this.save(Floor);
	}

	/**
	 * 删除
	* @param Floor
	* @return
	 */
	public int deleteFloor(Floor Floor) {
		return this.delete(Floor);
	}

   @Override
	public PageInfo<Floor> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Floor> list = FloorMapper.findPageInfo(params);
		return new PageInfo<Floor>(list);
	}

	@Override
	public Integer saveFloorProduct(Floor floor) {
		//旧的绑定的人员id
				List<Long> userIds = FloorMapper.findGoodsIdsByFloorId(floor.getId());
				//当前的要绑定的人员id
				List<Long> curUserIds = Lists.newArrayList(floor.getGoodsIds());
				userIds.addAll(curUserIds);
				ImmutableList<Long> mergeList = ImmutableSet.copyOf(userIds).asList();
				
				FloorMapper.deleteFloorGoodsIdsByFloorId(floor.getId());
				if(floor.getGoodsIds().length>0) {
					FloorMapper.insertFloorGoodsIdsByFloorId(floor);
				}
				//清除缓存
				SysUserUtils.clearAllCachedAuthorizationInfo(mergeList);
				return 1;
	}
}
