/**
 * 团购订单
 */
import request from '@/utils/request';

/**
 * 分页查询拼团订单
 *
 * @param query 查询参数
 */
export function queryGroupOrders(query) {
  return request({
    url: 'grouporders',
    method: 'get',
    params: query
  })
}


/**
 * 导出所有订单
 * @param status 订单状态
 */
export function exportAllOrder(status) {
  return request({
    url: 'exportallgrouporder',
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
    url: 'exportcheckedgrouporder',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}

/**
 * 查询拼团订单的物流模版信息
 * @param id 订单id
 */
export function queryLogisticsCompanys(id) {
  return request({
    url: 'grouporder/logisticscompanys',
    method: 'get'
  })
}


/**
 * 发货
 * @param id 订单id
 * @param params 修改参数
 */
export function deliverOrder(params) {
  return request({
    url: 'delivergrouporder/' + params.id + "/" + params.waybillCode + "/" + params.companyId,
    method: 'put',
  })
}
