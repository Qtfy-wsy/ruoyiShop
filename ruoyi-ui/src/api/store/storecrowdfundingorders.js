import request from '@/utils/request';

/**
 * 分页查询店铺众筹订单
 * @param 查询参数
 */
export function orderList(query) {
  return request({
    url: 'store/crowdfunding/orders',
    method: 'get',
    params: query
  })
}

/**
 * 确认订单
 * @param id 订单id
 * @param reason 原因
 */
export function confirmOrder(id, storeId, reason) {
  return request({
    url: 'store/crowdfunding/confirmorder/' + id + '/' + storeId,
    method: 'put',
    params: reason
  })
}


/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder(status, marketingId) {
  return request({
    url: 'store/crowdfunding/exportallorder',
    method: 'post',
    params: {status: status, marketingId: marketingId},
    responseType: 'arraybuffer'
  })
}

/**
 * 导出选择订单
 */
export function exportCheckedOrder(status, ids) {
  return request({
    url: 'store/crowdfunding/exportcheckedorder',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}

