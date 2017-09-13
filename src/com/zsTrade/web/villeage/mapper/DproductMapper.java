/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.villeage.model.Dproduct;


/**
 * 
 * @author zsCat 2017-1-19 11:45:30
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DproductMapper extends Mapper<Dproduct>{
	public List<Dproduct> findPageInfo(Map<String, Object> params);
	
}
