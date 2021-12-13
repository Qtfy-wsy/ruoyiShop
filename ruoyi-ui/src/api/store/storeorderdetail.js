/**
 * Created by 伊甸园商城 on 2019/6/24.
 */
import request from '@/utils/request';

/**
 * 查询订单详情
 * @param id 订单id
 */
export function queryOrderById(id) {
  return request({
    url: '/store/orderdetail/' + id,
    method: 'get',
  })
}
