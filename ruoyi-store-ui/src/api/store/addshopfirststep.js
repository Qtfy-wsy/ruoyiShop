import request from '@/utils/request';


/**
 * 查找所有没有开店的用户手机号
 */
export function queryAllCustomerMobileForCreateStore() {
  return request({
    url: '/addshop/customer',
    method: 'get',
  })
}


/**
 * 新增门店时新增会员
 *
 * @params customer 状态
 */
export function addCustomer(customer) {
  return request({
    url: '/addshop/customer',
    method: 'post',
    data: customer
  })
}


/**
 * 校验手机号码是否存在
 *
 * @param mobile 手机号码
 */
export function checkMobileExist(mobile) {
  return request({
    url: '/addshop/checkmobile?mobile=' + mobile,
    method: 'get',
  })
}
