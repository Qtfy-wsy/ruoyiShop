import request from '@/utils/request';


/**
 * 查询子分销客户信息
 * @param customerId
 */
export function querySpreadCustomerByCustomerId(customerId) {
  return request({
    url: 'subdistributioncustomer/' + customerId,
    method: 'get'
  })
}
