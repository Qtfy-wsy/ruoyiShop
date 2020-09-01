import request from '@/utils/request';


/**
 * 查询直降的单品
 * @param params 查询参数
 */
export function querySkusForFall(params) {
  return request({
    url: 'fall/skus',
    method: 'get',
    params: params
  })
}


/**
 * 查询直降商品是否有互斥
 * @param marketing 促销
 */
export function queryExclusionMarketingCountForFall(marketing) {
  return request({
    url: 'fall/exclusionmarketingcount',
    method: 'post',
    data: marketing
  })
}

/**
 * 新增直降促销
 * @param fall 直降促销
 */
export function addFallMarketing(fall) {
  return request({
    url: 'fall',
    method: 'post',
    data: fall
  })
}

/**
 *  根据促销id查询直降促销信息
 * @param id 直降促销id
 */
export function queryFallMarketingById(id) {
  return request({
    url: 'fall/' + id,
    method: 'get'
  })
}


/**
 * 查询直降商品是否有互斥（修改直降的时候用）
 * @param marketing 促销
 */
export function queryExclusionMarketingCountForFallUpdate(marketing) {
  return request({
    url: 'fall/update/exclusionmarketingcount',
    method: 'post',
    data: marketing
  })
}

/**
 * 修改直降促销
 * @param marketing
 */
export function updateFallMarketing(marketing) {
  return request({
    url: 'fall',
    method: 'put',
    data: marketing
  })
}


/**
 * 查询直降的单品（修改用）
 * @param params 查询参数
 */
export function querySkusForFallUpdate(params) {
  return request({
    url: 'fall/update/skus',
    method: 'get',
    params: params
  })
}
