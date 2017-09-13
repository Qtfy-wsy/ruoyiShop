package com.zsTrade.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.service.BlogTemplateService;
import com.zsTrade.web.prj.model.Article;
import com.zsTrade.web.prj.model.Cart;
import com.zsTrade.web.prj.model.Order;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.prj.model.ProductClass;
import com.zsTrade.web.prj.service.ArticleService;
import com.zsTrade.web.prj.service.CartService;
import com.zsTrade.web.prj.service.OrderService;
import com.zsTrade.web.prj.service.ProductClassService;
import com.zsTrade.web.prj.service.ProductService;

@Component
public class GoodsFunction {

	@Resource
	private BlogTemplateService BlogTemplateService;
	@Resource
	private OrderService orderService;
	@Resource
	private ProductClassService ProductClassService;
	@Resource
	private CartService CartService;	
	@Resource
	private ProductService productService;
	@Resource
	private ArticleService articleService;
	
	public PageInfo<Product> getLatestGoods(int pageSize){
		return productService.selectPage(1, pageSize, new Product()," create_date desc");
	}
	public PageInfo<Product> getHitGoods(int pageSize){
		return productService.selectPage(1, pageSize, new Product()," clickHit desc");
	}
	public PageInfo<Product> getSellGoods(int pageSize){
		return productService.selectPage(1, pageSize, new Product()," sellhit desc");
	}
	
	/**
	 * 拿到推荐
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Product> getCommendGoods(int pageSize){
		Product p=new Product();
		p.setIscom(1);
		return productService.selectPage(1, pageSize,p);
	}
	public PageInfo<Product> getTypeGoods(int pageSize,Long typeid){
		Product p=new Product();
		p.setTypeid(typeid);
		return productService.selectPage(1, pageSize,p);
	}
	public PageInfo<Product> getMenuTypeGoods(int pageSize,Long typeid){
		Product p=new Product();
		p.setType(typeid);
		return productService.selectPage(1, pageSize,p);
	}
	//活动最新订单
	public PageInfo<Order> getLatestOrder(int pageSize){
		return orderService.selectPage(1, pageSize, new Order());
	}
	//得到菜单类别
	public PageInfo<ProductClass> getProductClass(int pageSize,Long parentId){
		 ProductClass gc=new ProductClass();
         gc.setParentId(parentId);
		return ProductClassService.selectPage(1, pageSize, gc);
	}
	public List<ProductClass> getAllProductClass(){
		 ProductClass gc=new ProductClass();
		return ProductClassService.select(gc);
	}
	
	//得到购物车
	public List<Cart> getCartList() {
		 return CartService.selectOwnCart();
	 }
	
	//活动最新订单
		public PageInfo<Article> getAdvArticle(int pageSize){
			return articleService.selectPage(1, pageSize, new Article());
		}
}
