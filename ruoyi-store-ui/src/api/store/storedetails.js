import request from '@/utils/request';


/**
 * 根据店铺id查询店铺详情信息
 * @param query 查询参数
 */
export function storeDetailInfoByStoreId(query) {
  return request({
    url: 'storedetails',
    method: 'get',
    params: query
  })
}
