import request from '@/utils/request';

/**
 * 查询退单列表
 * @param page 分页参数
 * @param params 条件参数
 */
export function queryStoreBackOrders(params) {
  return request({
    url: '/order/OmsBackOrder/storebackorders',
    method: 'get',
    params: params
  })
}

/**
 * 查询订单详情
 * @param id 订单id
 */
export function queryOrderById(id) {
  return request({
    url: '/store/backorder/orderdetail/' + id,
    method: 'get',
  })
}
