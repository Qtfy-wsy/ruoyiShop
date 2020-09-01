import request from '@/utils/request';


/**
 * 查询店铺地区统计
 * @param query 查询参数
 */
export function queryStoreInfoAreaStatistics(query) {
  return request({
    url: 'storeregion/statistics',
    method: 'get',
    params: query
  })
}
