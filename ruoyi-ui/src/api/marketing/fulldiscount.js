import request from '@/utils/request';

/**
 * 分页查询满减促销
 * @param query 查询条件
 */
export function queryFullDiscountMarketing(query) {
  return request({
    url: 'fulldiscount',
    method: 'get',
    params: query
  })
}


/**
 * 删除满折促销
 * @param ids 满减促销id
 */
export function deleteFullDiscountByIds(ids) {
  return request({
    url: 'fulldiscount',
    method: 'delete',
    params: ids
  })
}
