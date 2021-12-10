/**
 * Created by 商城
 */
import request from '@/utils/request';

/**
 * 查询订单信息
 * @param ids订单ids
 */
export function queryOrders(ids) {
  return request({
    url: 'printorderdetails',
    method: 'get',
    params: {ids: ids}
  })
}
