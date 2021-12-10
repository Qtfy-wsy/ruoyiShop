/**
 * Created by 商城
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
