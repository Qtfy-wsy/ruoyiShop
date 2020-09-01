import request from '@/utils/request';

/**
 * 分页查询店铺拼团订单
 *
 * @param query 查询参数
 */
export function queryStoreGroupOrders(query) {
  return request({
    url: 'storegrouporders',
    method: 'get',
    params: query,
  })
}


/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder(status) {
  return request({
    url: 'exportstoreallgrouporder',
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
    url: 'exportstorecheckedgrouporder',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}
