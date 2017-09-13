//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import java.util.List;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Address;

/**
 * 
 * @author
 */

public interface AddressService extends BaseService<Address>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Address
	 * @return
	 */
	public int saveAddress(Address Address) ;
	/**
	 * 删除
	* @param Address
	* @return
	 */
	public int deleteAddress(Address Address);
	public int updateDef(String addressId, String string);
	public List<com.zsTrade.web.bases.model.Address> selectByMemberId();


}
