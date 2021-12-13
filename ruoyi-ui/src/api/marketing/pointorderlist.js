/**
 * 积分商城订单列表路由
 *
 * @author 伊甸园商城 created on 2019/8/1
 */

import request from '@/utils/request';


/**
 * 分页查询积分商城订单
 *
 * @param query 查询参数
 */
export function queryPointOrderList(query) {
  return request({
    url: 'pointorderlist',
    method: 'get',
    params: query
  })
}

/**
 * 查询所有物流公司
 */
export function queryAllLogisticsCompanys() {
  return request({
    url: 'alllogisticscompanys',
    method: 'get'
  })
}

/**
 * 积分商城订单发货
 *
 * @param deliverRequest 积分商城订单发货请求实体类
 */
export function deliverPointOrder(deliverRequest) {
  return request({
    url: 'deliverpointorder',
    method: 'put',
    data: deliverRequest
  })
}
