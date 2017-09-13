//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.prj.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.ProductClass;


/**
 * 
 * @author zsCat 2016-12-22 11:28:44
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	test管理
 */
public interface ProductClassMapper extends Mapper<ProductClass>{
	public List<ProductClass> findPageInfo(Map<String, Object> params);
	
}
