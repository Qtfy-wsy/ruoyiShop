import request from '@/utils/request';


/**
 * 查询限时抢购数据
 * @param params 查询参数
 */
export function queryTryMarketList(params) {
  return request({
    url: 'try',
    method: 'get',
    params: params
  })
}

/**
 * 删除试用促销
 * @param id 试用促销id
 */
export function deleteTryById(id) {
  return request({
    url: 'try/' + id,
    method: 'delete',
  })
}
