/**
 * 修改积分商品路由
 *
 * @author 商城 created on 2019/7/26
 */

import request from '@/utils/request';


/**
 * 查询所有积分商城分类
 */
export function queryAllPointCatesForUpdate() {
  return request({
    url: 'allpointcatesforupdate',
    method: 'get'
  })
}

/**
 * 根据id查询积分商品
 *
 * @param id 积分商品id
 */
export function queryPointSkuById(id) {
  return request({
    url: 'pointsku/' + id,
    method: 'get',
  })
}

/**
 * 修改积分商品
 *
 * @param pointSku 积分商品信息
 */
export function updatePointSku(pointSku) {
  return request({
    url: 'pointsku',
    method: 'put',
    data: pointSku
  })
}
