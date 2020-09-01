import request from '@/utils/request';


/**
 * 分页查询有下级的会员信息
 * @param query 查询参数
 */
export function spreadCustomerList(query) {
  return request({
    url: 'spreadcustomer',
    method: 'get',
    params: query
  })
}
