/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.Product;


/**
 * 
 * @author zsCat 2016-12-22 9:29:05
 * @Email: [email]
 * @version [version]
 *	项目管理
 */
public interface ProductMapper extends Mapper<Product>{
	public List<Product> findPageInfo(Map<String, Object> params);

	public List<Product> selectProductByFloor(Long id);

	public List<Product> getProductByFloorid(Long tid);

	public List<Product> selectgoodsListByType(Product g);

	public List<Product> selectRepoer();
	
}
