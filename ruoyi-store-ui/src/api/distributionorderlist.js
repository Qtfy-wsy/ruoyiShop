import request from '@/utils/request';


/**
 * 分页查询分销订单信息
 * @param 查询参数
 */
export function spreadOrdersList(query) {
  return request({
    url: 'spreadorder',
    method: 'get',
    params: query
  })
}
