import request from '@/utils/request';

/**
 * 分页查询满赠促销
 * @param query 查询条件
 */
export function queryFullGiftMarketing(query) {
  return request({
    url: 'fullgift',
    method: 'get',
    params: query
  })
}

/**
 *批量删除满赠促销
 * @param ids 促销id
 */
export function deleteFullGiftByIds(ids) {
  return request({
    url: 'fullgift',
    method: 'delete',
    params: ids
  })
}
