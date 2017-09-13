package com.zsTrade.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.service.BlogTemplateService;
import com.zsTrade.web.prj.model.Cart;
import com.zsTrade.web.prj.model.Order;
import com.zsTrade.web.prj.model.ProductClass;
import com.zsTrade.web.prj.service.CartService;
import com.zsTrade.web.prj.service.OrderService;
import com.zsTrade.web.prj.service.ProductClassService;
import com.zsTrade.web.villeage.model.Dproduct;
import com.zsTrade.web.villeage.model.Dproducttype;
import com.zsTrade.web.villeage.model.Dvillage;
import com.zsTrade.web.villeage.service.DproductService;
import com.zsTrade.web.villeage.service.DproducttypeService;
import com.zsTrade.web.villeage.service.DvillageService;

@Component
public class DgoodsFunction {

	@Resource
	private BlogTemplateService BlogTemplateService;
	@Resource
	private OrderService orderService;
	@Resource
	private ProductClassService ProductClassService;
	@Resource
	private CartService CartService;	
	@Resource
	private DproductService dproductService;
	@Resource
	private DproducttypeService DproductTypeService;
	@Resource
	private DvillageService DvillageService;
	
	// 查询所有村
	public PageInfo<Dvillage> getDvillage(int pageSize){
		Dvillage d=new Dvillage();
		d.setStat(1);
		return DvillageService.selectPage(1, pageSize, d," orderby asc");
	}
	/**
	 * 得到所有产品类型
	 */
	public List<Dproducttype> getProductType(){
		return DproductTypeService.select(new Dproducttype());
	}
	
	public PageInfo<Dproduct> getLatestGoods(int pageSize){
		return dproductService.selectPage(1, pageSize, new Dproduct()," create_date desc");
	}
	public PageInfo<Dproduct> getHitGoods(int pageSize){
		return dproductService.selectPage(1, pageSize, new Dproduct()," clickHit desc");
	}
	public PageInfo<Dproduct> getSellGoods(int pageSize){
		return dproductService.selectPage(1, pageSize, new Dproduct()," sellhit desc");
	}
	
	/**
	 * 拿到推荐
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Dproduct> getCommendGoods(int pageSize){
		Dproduct p=new Dproduct();
		p.setIscom(1);
		return dproductService.selectPage(1, pageSize,p);
	}
	public PageInfo<Dproduct> getTypeGoods(int pageSize,Long typeid){
		Dproduct p=new Dproduct();
		p.setTypeid(typeid);
		return dproductService.selectPage(1, pageSize,p);
	}
	public PageInfo<Dproduct> getMenuTypeGoods(int pageSize,Long typeid){
		Dproduct p=new Dproduct();
		p.setType(typeid);
		return dproductService.selectPage(1, pageSize,p);
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
	
	
	//得到购物车
	public List<Cart> getCartList() {
		 return CartService.selectOwnCart();
		
	 }
}
