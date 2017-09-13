/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.Product;

 /**
 * 
 * @author zsCat 2016-12-22 9:29:05
 * @Email: [email]
 * @version [version]
 *	项目管理
 */
public interface ProductService extends BaseService<Product>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Product
	 * @return
	 */
	public int saveProduct(Product Product)throws Exception  ;
	/**
	 * 删除
	* @param Product
	* @return
	 */
	public int deleteProduct(Product Product)throws Exception ;

	public PageInfo<Product> findPageInfo(Map<String, Object> params);
	public List<Product> selectProductByFloor(Long id);
	public List<Product> getProductByFloorid(Long tid);
	public PageInfo<Product> selectgoodsListByType(int i, int j, Product g);
	public List<Product> selectRepoer();

}
