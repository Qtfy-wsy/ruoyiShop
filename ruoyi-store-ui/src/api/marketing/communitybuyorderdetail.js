/**
 * 社区团购订单详情路由
 *
 * @author 魔金商城 created on 2019/8/22
 */

import request from '@/utils/request';


/**
 * 根据id查询社区团购订单详情
 * @param id 订单id
 */
export function queryCommunityOrderById(id) {
  return request({
    url: 'communitybuyorder/' + id,
    method: 'get',
  })
}
