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
import com.zsTrade.web.prj.service.ReplyService;
import com.zsTrade.web.prj.mapper.ReplyMapper;

import com.zsTrade.web.prj.model.Reply;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2017-1-7 16:15:50
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
@Service("ReplyService")
public class ReplyServiceImpl  extends ServiceMybatis<Reply> implements ReplyService {

	@Resource
	private ReplyMapper ReplyMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Reply
	 * @return
	 */
	public int saveReply(Reply Reply) {
		return this.save(Reply);
	}

	/**
	 * 删除
	* @param Reply
	* @return
	 */
	public int deleteReply(Reply Reply) {
		return this.delete(Reply);
	}

   @Override
	public PageInfo<Reply> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Reply> list = ReplyMapper.findPageInfo(params);
		return new PageInfo<Reply>(list);
	}
}
