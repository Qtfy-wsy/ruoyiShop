/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.ProductType;


/**
 * 
 * @author zsCat 2016-12-22 9:29:55
 * @Email: [email]
 * @version [version]
 *	项目类别管理
 */
public interface ProductTypeMapper extends Mapper<ProductType>{
	public List<ProductType> findPageInfo(Map<String, Object> params);
	
}
