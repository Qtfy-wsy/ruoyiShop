import request from '@/utils/request';


/**
 * 根据店铺id查询店铺详情信息
 *
 * @param query 店铺id
 * @param status 状态
 */
export function storeDetailInfoByStoreId(storeId) {
  return request({
    url: 'shopdetails',
    method: 'get',
    params: {storeId: storeId}
  })
}
