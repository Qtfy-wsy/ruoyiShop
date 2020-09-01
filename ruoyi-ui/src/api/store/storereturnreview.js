/**
 * Created by 魔金商城 on 2019/6/25.
 */
import request from '@/utils/request';

/**
 * 查询退货详情
 */
export function queryBackOrderById(id) {
  return request({
    url: '/return/backorderdetail/' + id,
    method: 'get',
  })
}
