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
import com.zsTrade.web.prj.service.ArticleService;
import com.zsTrade.web.prj.mapper.ArticleMapper;

import com.zsTrade.web.prj.model.Article;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2016-12-22 16:15:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	文章管理
 */
@Service("ArticleService")
public class ArticleServiceImpl  extends ServiceMybatis<Article> implements ArticleService {

	@Resource
	private ArticleMapper ArticleMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Article
	 * @return
	 */
	public int saveArticle(Article Article) {
		return this.save(Article);
	}

	/**
	 * 删除
	* @param Article
	* @return
	 */
	public int deleteArticle(Article Article) {
		return this.delete(Article);
	}

   @Override
	public PageInfo<Article> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Article> list = ArticleMapper.findPageInfo(params);
		return new PageInfo<Article>(list);
	}
}
