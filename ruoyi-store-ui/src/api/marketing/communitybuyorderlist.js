/**
 * 社区团购订单路由
 *
 * @author 魔金商城 created on 2019/8/21
 */

import request from '@/utils/request';


/**
 * 分页查询社区团购订单列表
 *
 * @param query 查询参数
 */
export function queryCommunityBuyOrderList(query) {
  return request({
    url: 'communitybuyorderlist',
    method: 'get',
    params: query
  })
}

/**
 * 导出所有订单信息
 */
export function exportAllCommunityBuyOrder() {
  return request({
    url: 'communitybuyorder/exportall',
    method: 'post',
    responseType: 'arraybuffer'
  })
}

/**
 * 导出选中订单信息
 */
export function exportCheckedCommunityBuyOrder(ids) {
  return request({
    url: 'communitybuyorder/exportchecked',
    method: 'post',
    params: {ids: ids},
    responseType: 'arraybuffer'
  })
}
