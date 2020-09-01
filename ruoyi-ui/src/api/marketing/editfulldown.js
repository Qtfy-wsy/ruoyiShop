import request from '@/utils/request';

/**
 * 分页查询单品信息
 * @param params 查询参数
 */
export function querySkusForFullDown(params) {
  return request({
    url: 'fulldown/skus',
    method: 'get',
    params: params
  })
}

/**
 * 添加满减促销
 * @param fullDown 满减促销
 */
export function addFullDownMarketing(fullDown) {
  return request({
    url: 'fulldown',
    method: 'post',
    data: fullDown
  })
}

/**
 * 查询满减促销
 * @param id 满减促销id
 */
export function queryFullDownMarketingById(id) {
  return request({
    url: 'fulldown/' + id,
    method: 'get',
  })
}

/**
 * 更新满减促销
 * @param fullDown 满减促销
 */
export function updateFullDown(fullDown) {
  return request({
    url: 'fulldown',
    method: 'put',
    data: fullDown
  })
}

/**
 * 分页查询单品信息(修改用)
 * @param params 查询参数
 */
export function querySkusForFullDownUpdate(params) {
  return request({
    url: 'fulldown/update/skus',
    method: 'get',
    params: params
  })
}
