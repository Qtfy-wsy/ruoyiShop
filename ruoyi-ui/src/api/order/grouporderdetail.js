/**
 * Created by dujinkai on 2019/6/18.
 */
import request from '@/utils/request';

/**
 * 根据订单id查询拼团订单信息
 * @param id 订单id
 */
export function queryGroupOrderById(id) {
  return request({
    url: 'grouporder/' + id,
    method: 'get',
  })
}
