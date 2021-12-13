/**
 * Created by 伊甸园商城 on 2019/7/1.
 */
import request from '@/utils/request';

/**
 * 根据订单id查询拼团订单信息
 * @param id 订单id
 */
export function queryGroupOrderById(id) {
  return request({
    url: 'queryorderbyidforstoregroup/' + id,
    method: 'get',
  })
}
