/**
 * 团购订单
 */
import request from '@/utils/request';

/**
 * 分页查询拼团未付款订单
 *
 * @param query 查询参数
 */
export function queryNotPayGroupOrders(query) {
  return request({
    url: 'nopaygrouporders',
    method: 'get',
    params: query
  })
}

/**
 * 取消拼团订单
 * @param id 订单id
 */
export function cancelGroupOrder(id) {
  return request({
    url: 'cancelgrouporder/' + id,
    method: 'put',
  })
}


/**
 * 确认订单
 * @param id 订单id
 * @param reason 原因
 */
export function confirmGroupOrder(id, reason) {
  return request({
    url: 'confirmgrouporder/' + id,
    method: 'put',
    params: reason
  })
}

/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder() {
  return request({
    url: '/nopay/exportallgrouporder',
    method: 'post',
    responseType: 'arraybuffer'
  })
}

/**
 * 导出选择订单
 */
export function exportCheckedGroupOrder(ids) {
  return request({
    url: 'nopay/exportcheckedgrouporder',
    method: 'post',
    params: {ids: ids},
    responseType: 'arraybuffer'
  })
}

