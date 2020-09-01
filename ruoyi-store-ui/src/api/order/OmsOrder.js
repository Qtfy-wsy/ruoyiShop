import request from '@/utils/request'

// 查询订单列表
export function listOmsOrder(query) {
  return request({
    url: '/order/OmsOrder/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOmsOrder(id) {
  return request({
    url: '/order/OmsOrder/' + id,
    method: 'get'
  })
}

// 新增订单
export function addOmsOrder(data) {
  return request({
    url: '/order/OmsOrder',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOmsOrder(data) {
  return request({
    url: '/order/OmsOrder',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOmsOrder(id) {
  return request({
    url: '/order/OmsOrder/' + id,
    method: 'delete'
  })
}

// 导出订单
export function exportOmsOrder(query) {
  return request({
    url: '/order/OmsOrder/export',
    method: 'get',
    params: query
  })
}
/**
 * 取消订单
 * @param id 订单id
 */
export function cancelOrder(id) {
  return request({
    url: '/order/OmsOrder/order/' + id,
    method: 'delete',
  })
}


/**
 * 确认订单
 * @param id 订单id
 * @param reason 原因
 */
export function confirmOrder(id, reason) {
  return request({
    url: '/order/OmsOrder/confirmorder/' + id,
    method: 'put',
    params: reason
  })
}


/**
 * 修改价格
 * @param id 订单id
 * @param params 修改参数
 */
export function changePrice(id, params) {
  return request({
    url: '/order/OmsOrder/modifyprice/' + id,
    method: 'put',
    params: params
  })
}


/**
 * 发货
 * @param id 订单id
 * @param params 修改参数
 */
export function deliverOrder(params) {
  return request({
    url: '/order/OmsOrder/deliverorder/' + params.id + "/" + params.waybillCode + "/" + params.companyId,
    method: 'put',
  })
}


/**
 *查询订单的物流模版信息
 * @param id 订单id
 */
export function queryLogisticsCompanys(id) {
  return request({
    url: '/order/OmsOrder/order/logisticscompanys',
    method: 'get',
  })
}

/**
 * 核销虚拟订单
 * @param id 订单id
 * @param writeOffCode 核销码
 */
export function chargeOffVirtualOrder(id, writeOffCode) {
  return request({
    url: '/order/OmsOrder/chargeoffvirtualorder/' + id + "/" + writeOffCode,
    method: 'put',
  })
}

/**
 * 修改物流单号
 */
export function changeExpressNo(params) {
  return request({
    url: '/order/OmsOrder/changeexpressno/' + params.id + "/" + params.waybillCode + "/" + params.companyId,
    method: 'put',
  })
}

/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder(status) {
  return request({
    url: '/order/OmsOrder/exportallorder',
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
    url: '/order/OmsOrder/exportcheckedorder',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}
