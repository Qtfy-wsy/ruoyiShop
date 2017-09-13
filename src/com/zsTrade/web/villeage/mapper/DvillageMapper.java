/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.villeage.model.Dvillage;


/**
 * 
 * @author zsCat 2017-1-19 10:17:13
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DvillageMapper extends Mapper<Dvillage>{
	public List<Dvillage> findPageInfo(Map<String, Object> params);
	
}
