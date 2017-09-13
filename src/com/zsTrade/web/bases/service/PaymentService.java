//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Payment;

/**
 * 
 * @author
 */

public interface PaymentService extends BaseService<Payment>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Payment
	 * @return
	 */
	public int savePayment(Payment Payment) ;
	/**
	 * 删除
	* @param Payment
	* @return
	 */
	public int deletePayment(Payment Payment);


}
