import request from '@/utils/request';

/**
 * 查询订单详情
 * @param id 订单id
 */
export function queryOrderById(id) {
  return request({
    url: 'crowdfunding/order/' + id,
    method: 'get',
  })
}
