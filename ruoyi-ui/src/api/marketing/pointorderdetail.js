/**
 * 积分商城订单详情路由
 *
 * @author 伊甸园商城 created on 2019/8/1
 */

import request from '@/utils/request';


/**
 * 根据id查询积分商城订单详情
 *
 * @param id 积分商城订单id
 */
export function queryPointOrderById(id) {
  return request({
    url: 'pointorder/' + id,
    method: 'get',
  })
}
