import request from '@/utils/request';

/**
 * 分页查询众筹订单
 * @param 查询参数
 */
export function orderList(query) {
  return request({
    url: 'crowdfunding/orders',
    method: 'get',
    params: query
  })
}

/**
 * 确认订单
 * @param id 订单id
 * @param reason 原因
 */
export function confirmOrder(id, reason) {
  return request({
    url: 'crowdfunding/confirmorder/' + id,
    method: 'put',
    params: reason
  })
}

/**
 *查询店铺使用的物流信息
 */
export function queryLogisticsCompanys() {
  return request({
    url: 'crowdfunding/logisticscompanys',
    method: 'get',
  })
}


/**
 * 发货
 * @param id 订单id
 * @param params 修改参数
 */
export function deliverOrder(params) {
  return request({
    url: 'crowdfunding/deliverorder/' + params.id + "/" + params.waybillCode + "/" + params.companyId,
    method: 'put',
  })
}

/**
 * 取消订单
 * @param id 订单id
 */
export function cancelOrder(id) {
  return request({
    url: 'crowdfunding/order/' + id,
    method: 'delete',
  })
}


/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder(status, marketingId) {
  return request({
    url: 'crowdfunding/exportallorder',
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
    url: 'crowdfunding/exportcheckedorder',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}


/**
 * 修改物流单号
 */
export function changeExpressNo(params) {
  return request({
    url: 'crowdfunding/changeexpressno/' + params.id + "/" + params.waybillCode + "/" + params.companyId,
    method: 'put',
  })
}
