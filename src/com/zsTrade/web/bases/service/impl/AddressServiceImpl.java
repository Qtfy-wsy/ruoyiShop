//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.AddressMapper;
import com.zsTrade.web.bases.model.Address;
import com.zsTrade.web.bases.service.AddressService;
import com.zsTrade.web.sys.utils.SysUserUtils;

/**
 * 
 * @author
 */

@Service("AddressService")
public class AddressServiceImpl  extends ServiceMybatis<Address> implements AddressService {

	@Resource
	private AddressMapper AddressMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Address
	 * @return
	 */
	public int saveAddress(Address Address) {
		return this.save(Address);
	}

	/**
	 * 删除
	* @param Address
	* @return
	 */
	public int deleteAddress(Address Address) {
		return this.delete(Address);
	}

	@Override
	public int updateDef(String addressId, String memberId) {
		int result = 0;
        if (addressId != null) {
            Address address = new Address();
            address.setMemberId(Long.valueOf(memberId));
            address.setIsDefault("0");
            AddressMapper.updateByMemberId(address);
            address.setMemberId(null);
            address.setId(Long.valueOf(addressId));
            address.setIsDefault("1");
            this.updateByPrimaryKeySelective(address);
            result = 1;
        }
        return result;
	}

	@Override
	public List<Address> selectByMemberId() {
		 Address Address=new Address();
		 Address.setMemberId(SysUserUtils.getSessionLoginUser().getId());
		return  this.select(Address);
	}



}
