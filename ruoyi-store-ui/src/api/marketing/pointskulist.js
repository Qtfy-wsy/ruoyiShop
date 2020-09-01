/**
 * 积分商品列表路由
 *
 * @author 魔金商城 created on 2019/7/26
 */

import request from '@/utils/request';


/**
 * 分页查询积分商品列表
 *
 * @param query 查询参数
 */
export function queryPointSkuList(query) {
    return request({
        url: 'pointskulist',
        method: 'get',
        params: query
    })
}

/**
 * 查询所有积分商城分类
 */
export function queryAllPointCates() {
  return request({
    url: 'allpointcates',
    method: 'get'
  })
}

/**
 * 删除积分商品
 *
 * @param ids 积分商品id数组
 */
export function deletePointSkus(ids) {
    return request({
        url: 'pointsku',
        method: 'delete',
        params: ids
    })
}
