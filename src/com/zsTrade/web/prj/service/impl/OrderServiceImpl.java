/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.common.constant.Constant;
import com.zsTrade.common.utils.RandomString;
import com.zsTrade.web.prj.mapper.CartMapper;
import com.zsTrade.web.prj.mapper.GoodsOrderMapper;
import com.zsTrade.web.prj.mapper.OrderLogMapper;
import com.zsTrade.web.prj.mapper.OrderMapper;
import com.zsTrade.web.prj.model.Cart;
import com.zsTrade.web.prj.model.GoodsOrder;
import com.zsTrade.web.prj.model.Order;
import com.zsTrade.web.prj.model.OrderLog;
import com.zsTrade.web.prj.service.OrderService;
import com.zsTrade.web.sys.utils.SysUserUtils;
/**
 * 
 * @author zsCat 2017-1-7 16:06:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单管理
 */
@Service("OrderService")
public class OrderServiceImpl  extends ServiceMybatis<Order> implements OrderService {

	@Resource
	private OrderMapper OrderMapper;
	
	@Resource
	private GoodsOrderMapper GoodsOrderMapper;
	@Resource
	private  CartMapper CartMapper;
	@Resource
	private OrderLogMapper  OrderLogMapper;
	
	/**
	 * 保存或更新
	 * 
	 * @param Order
	 * @return
	 */
	public int saveOrder(Order Order) {
		return this.save(Order);
	}

	/**
	 * 删除
	* @param Order
	* @return
	 */
	public int deleteOrder(Order Order) {
		return this.delete(Order);
	}

   @Override
	public PageInfo<Order> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Order> list = OrderMapper.findPageInfo(params);
		return new PageInfo<Order>(list);
	}
	@Override
	public Order insertOrder(String[] cartIds,Long addressid, Long paymentid, String usercontent) {
		Order order=new Order();
		if(cartIds!=null && cartIds.length>0){
			int count=0;
			BigDecimal total=BigDecimal.ZERO;
			for(String cartId:cartIds){
				Cart cart=CartMapper.selectByPrimaryKey(Long.parseLong(cartId));
				if(cart==null){
					return null;
				}
				count+=cart.getCount();
				 total =total.add(BigDecimal.valueOf(Double.valueOf(cart.getPrice())).multiply(BigDecimal.valueOf(cart.getCount())));
				GoodsOrder go=new GoodsOrder();
				go.setGoodsid(cart.getGoodsid());
				go.setOrderid(order.getId());
				GoodsOrderMapper.insertSelective(go);
				CartMapper.deleteByPrimaryKey(cart);
			}
			order.setOrdersn(RandomString.generateRandomString(8));
			order.setCreatedate(new Date());
			order.setStatus(1);
			order.setUserid(SysUserUtils.getSessionLoginUser().getId());
			order.setUsername(SysUserUtils.getSessionLoginUser().getUsername());
			order.setPaymentid(paymentid);
			order.setUsercontent(usercontent);
			order.setAddressid(addressid);
			//order.setOrderTotalPrice();
			OrderMapper.insertSelective(order);
			
			OrderLog log=new OrderLog();
			log.setOrderId(order.getId());
			log.setOrderState("1");
			log.setStateInfo("提交订单");
			log.setCreateTime(new Date().getTime());
			log.setOperator(SysUserUtils.getSessionLoginUser().getUsername());
			OrderLogMapper.insertSelective(log);
			
			order.setTotalcount(count);
			order.setTotalprice(total);
			OrderMapper.updateByPrimaryKey(order);
		}
		return order;
		
	}

}
