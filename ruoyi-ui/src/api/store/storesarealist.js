import request from '@/utils/request';


/**
 * 查询地区店铺信息
 * @param query 查询参数
 */
export function queryAreaStoreInfo(query) {
  return request({
    url: 'storeregion',
    method: 'get',
    params: query
  })
}
