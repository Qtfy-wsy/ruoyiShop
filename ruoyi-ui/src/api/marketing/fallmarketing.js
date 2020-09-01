import request from '@/utils/request';

/**
 *分页查询直降促销信息
 * @param query
 */
export function queryFallMarketing(query) {
  return request({
    url: 'fall',
    method: 'get',
    params: query
  })
}

/**
 * 删除直降促销
 * @param params 参数
 */
export function deleteFallByIds(params) {
  return request({
    url: 'fall',
    method: 'delete',
    params: params
  })
}
