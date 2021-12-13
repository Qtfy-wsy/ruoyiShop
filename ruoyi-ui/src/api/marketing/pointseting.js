/**
 * 积分设置路由
 *
 * @author 伊甸园商城 created on 2019/7/31
 */

import request from '@/utils/request';


/**
 * 查询积分设置
 */
export function queryPointSeting() {
  return request({
    url: 'pointseting',
    method: 'get',
  })
}

/**
 * 修改积分设置
 *
 * @param pointSeting 积分设置
 */
export function updatePointSeting(pointSeting) {
  return request({
    url: 'pointseting',
    method: 'put',
    data: pointSeting
  })
}
