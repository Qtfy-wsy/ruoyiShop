/**
 * Created by 魔金商城 on 2019/6/24.
 */
import request from '@/utils/request';

/**
 * 查询订单信息
 * @param ids订单ids
 */
export function queryPrintStoreOrderDetails(ids) {
  return request({
    url: 'printstoreorderdetails',
    method: 'get',
    params: {ids: ids}
  })
}
