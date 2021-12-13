/**
 * 拼团促销列表路由
 *
 * @author 伊甸园商城 created on 2019/8/7
 */

import request from '@/utils/request';


/**
 * 分页查询拼团促销列表
 *
 * @param query 查询参数
 */
export function queryGroupMarketingList(query) {
  return request({
    url: 'groupmarketinglist',
    method: 'get',
    params: query
  })
}

/**
 *
 * 批量删除拼团促销
 *
 * @param ids 删除的id集合
 */
export function deleteGroupMarketing(ids) {
  return request({
    url: 'groupmarketing',
    method: 'delete',
    params: ids
  })
}
