import request from '@/utils/request';

/**
 * 查询定金预售
 * @param params 查询参数
 */
export function queryDepositPresale(params) {
  return request({
    url: 'depositpresale',
    method: 'get',
    params: params
  })
}

/**
 * 查询全款预售
 * @param params 查询参数
 */
export function queryFullPresale(params) {
  return request({
    url: 'fullpresale',
    method: 'get',
    params: params
  })
}

/**
 * 批量删除定金预售促销
 * @param ids 促销id
 */
export function deleteDepositPresaleByIds(ids) {
  return request({
    url: 'depositpresale',
    method: 'delete',
    params: ids
  })
}

/**
 * 批量删除全款预售
 * @param ids 促销id
 */
export function deleteFullPresaleByIds(ids) {
  return request({
    url: 'fullpresale',
    method: 'delete',
    params: ids
  })
}
