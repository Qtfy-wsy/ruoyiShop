/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.villeage.model.Dviname;


/**
 * 
 * @author zsCat 2017-1-19 10:37:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DvinameMapper extends Mapper<Dviname>{
	public List<Dviname> findPageInfo(Map<String, Object> params);
	
}
