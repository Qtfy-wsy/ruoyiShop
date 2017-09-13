//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.PaymentMapper;
import com.zsTrade.web.bases.model.Payment;
import com.zsTrade.web.bases.service.PaymentService;

/**
 * 
 * @author
 */

@Service("PaymentService")
public class PaymentServiceImpl  extends ServiceMybatis<Payment> implements PaymentService {

	@Resource
	private PaymentMapper PaymentMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Payment
	 * @return
	 */
	public int savePayment(Payment Payment) {
		return this.save(Payment);
	}

	/**
	 * 删除
	* @param Payment
	* @return
	 */
	public int deletePayment(Payment Payment) {
		return this.delete(Payment);
	}


}
