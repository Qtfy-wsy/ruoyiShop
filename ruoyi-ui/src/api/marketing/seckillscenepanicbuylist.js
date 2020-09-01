/**
 * 秒杀活动折扣路由
 *
 * @author 魔金商城 created on 2020/5/15
 */

import request from '@/utils/request';


/**
 * 分页查询秒杀场次折扣
 *
 * @param query 查询参数
 * @return 返回秒杀场次折扣列表
 */
export function querySeckillScenePanicbuyList(query) {
  return request({
    url: 'seckillscenepanicbuylist',
    method: 'get',
    params: query
  })
}

/**
 * 修改秒杀活动折扣
 *
 * @param params 修改参数
 * @return 成功1 否则失败
 */
export function updateSeckillScenePanicbuy(params) {
  return request({
    url: 'seckillscenepanicbuy/update/' + params.id + '/' + params.isShow + '/' + params.sort,
    method: 'put',
  })
}

/**
 * 批量设置秒杀活动折扣展示
 *
 * @param ids 秒杀活动折扣id数组
 * @return 成功1 否则失败
 */
export function batchShowSeckillScenePanicbuy(ids) {
  return request({
    url: 'seckillscenepanicbuy/batchshow',
    method: 'put',
    params: ids
  })
}

/**
 * 批量设置秒杀活动折扣不展示
 *
 * @param ids 秒杀活动折扣id数组
 * @return 成功1 否则失败
 */
export function batchHideSeckillScenePanicbuy(ids) {
  return request({
    url: 'seckillscenepanicbuy/batchhide',
    method: 'put',
    params: ids
  })
}
