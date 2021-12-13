/**
 * Created by 伊甸园商城 on 2019/6/25.
 */
import request from '@/utils/request';

/**
 * 查询退款详情
 */
export function queryBackOrderById(id) {
  return request({
    url: '/refund/backorderdetail/' + id,
    method: 'get',
  })
}

