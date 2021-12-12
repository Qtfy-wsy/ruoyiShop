import request from '@/utils/request';

/**
 * 分页查询店铺订单
 * @param query 查询参数
 */
export function queryStoreOrders(query) {
  return request({
    url: '/order/OmsOrder/storeorders',
    method: 'get',
    params: query
  })
}

/**
 * 确认订单
 * @param id 订单id
 * @param storeId 店铺id
 * @param reason 原因
 */
export function confirmOrder(id, storeId, reason) {
  return request({
    url: 'confirmstoreorder/' + id + '/' + storeId,
    method: 'put',
    params: reason
  })
}


/**
 * 修改价格
 * @param id 订单id
 * @param storeId 店铺id
 * @param params 修改参数
 */
export function changePrice(id, storeId, params) {
  return request({
    url: 'modifystoreorderprice/' + id + '/' + storeId,
    method: 'put',
    params: params
  })
}


/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder(status) {
  return request({
    url: 'exportstoreallorder',
    method: 'post',
    params: {status: status},
    responseType: 'arraybuffer'
  })
}

/**
 * 导出选择订单
 */
export function exportCheckedOrder(status, ids) {
  return request({
    url: 'exportstorecheckedorder',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}
