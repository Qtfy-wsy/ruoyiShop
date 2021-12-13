/**
 * 新增积分商品路由
 *
 * @author 伊甸园商城 created on 2019/7/26
 */

import request from '@/utils/request';


/**
 * 查询所有积分商城分类
 */
export function queryAllPointCatesForAdd() {
  return request({
    url: 'allpointcatesforadd',
    method: 'get'
  })
}

/**
 * 新增积分商品
 *
 * @param pointSku 积分商品信息
 */
export function addPointSku(pointSku) {
    return request({
        url: 'pointsku',
        method: 'post',
        data: pointSku
    })
}
