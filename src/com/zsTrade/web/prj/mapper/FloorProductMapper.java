/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.FloorProduct;


/**
 * 
 * @author zsCat 2016-12-22 14:23:22
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层产品管理
 */
public interface FloorProductMapper extends Mapper<FloorProduct>{
	public List<FloorProduct> findPageInfo(Map<String, Object> params);
	
}
