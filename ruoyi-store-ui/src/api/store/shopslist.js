import request from '@/utils/request';


/**
 * 分页查询门店列表
 *
 * @param query 查询参数
 */
export function queryStoreList(query) {
  return request({
    url: 'storelist',
    method: 'get',
    params: query
  })
}


/**
 * 批量关店
 *
 * @param ids 店铺id
 */
export function closeStores(ids) {
  return request({
    url: 'storelist/close',
    method: 'put',
    params: ids
  })
}


/**
 * 开启门店
 *
 * @param storeId 店铺id
 */
export function openStoreForOutLetStore(storeId) {
  return request({
    url: 'storelist/open/' + storeId,
    method: 'put',
  })
}
