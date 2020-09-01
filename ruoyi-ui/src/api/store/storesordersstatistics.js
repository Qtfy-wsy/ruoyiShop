import request from '@/utils/request';


/**
 * 分页查询店铺销量
 * @param query 查询参数
 */
export function queryStoreSaleVolume(query) {
  return request({
    url: 'storesales/salevolume',
    method: 'get',
    params: query
  })
}
