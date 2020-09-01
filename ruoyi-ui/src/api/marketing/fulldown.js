import request from '@/utils/request';

/**
 * 分页查询满减促销
 * @param query 查询条件
 */
export function queryFullDownMarketing(query) {
  return request({
    url: 'fulldown',
    method: 'get',
    params: query
  })
}

/**
 * 删除满减促销
 * @param ids 满减促销id
 */
export function deleteFullDownByIds(ids) {
  return request({
    url: 'fulldown',
    method: 'delete',
    params: ids
  })
}
