/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.luence.ProductIndex;
import com.zsTrade.web.prj.mapper.ProductMapper;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.prj.service.ProductService;
/**
 * 
 * @author zsCat 2016-12-22 9:29:05
 * @Email: [email]
 * @version [version]
 *	项目管理
 */
@Service("ProductService")
public class ProductServiceImpl  extends ServiceMybatis<Product> implements ProductService {
	// 商品索引
	private ProductIndex productIndex=new ProductIndex();
	@Resource
	private ProductMapper ProductMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Product
	 * @return
	 * @throws Exception 
	 */
	public int saveProduct(Product Product) throws Exception {
		int count = 0;
		if (Product.get("id") == null) {
			count = this.insertSelective(Product);
			productIndex.addIndex(Product);
		} else {
			productIndex.updateIndex(Product);
			count = this.updateByPrimaryKeySelective(Product);
		}
		return count;
		
	}

	/**
	 * 删除
	* @param Product
	* @return
	 * @throws Exception 
	 */
	public int deleteProduct(Product Product) throws Exception {
		productIndex.deleteIndex(Product.getId());
		return this.delete(Product);
	}

   @Override
	public PageInfo<Product> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Product> list = ProductMapper.findPageInfo(params);
		return new PageInfo<Product>(list);
	}

@Override
public List<Product> selectProductByFloor(Long id) {
	return ProductMapper.selectProductByFloor(id);
}

@Override
public List<Product> getProductByFloorid(Long tid) {
	// TODO Auto-generated method stub
	return ProductMapper.getProductByFloorid(tid);
}

@Override
public PageInfo<Product> selectgoodsListByType(int i, int j, Product g) {
	PageHelper.startPage(i, j);
	List<Product> list = ProductMapper.selectgoodsListByType(g);
	return new PageInfo<Product>(list);
}

@Override
public List<Product> selectRepoer() {
	// TODO Auto-generated method stub
	return ProductMapper.selectRepoer();
}
}
