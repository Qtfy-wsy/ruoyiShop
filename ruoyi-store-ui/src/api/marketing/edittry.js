import request from '@/utils/request';


/**
 *  查询优惠券
 * @param params 查询参数
 */
export function queryCouponsForTry() {
  return request({
    url: 'try/coupon',
    method: 'get',
  })
}


/**
 * 查询试用分类
 */
export function queryMarketingCatesByTypeForTry() {
  return request({
    url: 'try/cate',
    method: 'get',
  })
}

/**
 * 分页查询单品信息
 * @param params 查询参数
 */
export function querySkusForTry(params) {
  return request({
    url: 'try/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的互斥促销数量（试用）
 * @param marketing 试用促销
 */
export function queryExclusionMarketingCountForTry(marketing) {
  return request({
    url: 'try/exclusion',
    method: 'post',
    data: marketing
  })
}

/**
 * 添加试用促销
 * @param marketing 试用促销
 */
export function addTryMarketing(marketing) {
  return request({
    url: 'try',
    method: 'post',
    data: marketing
  })
}


/**
 *  查询优惠券(更新试用)
 * @param params 查询参数
 */
export function queryCouponsForTryUpdate() {
  return request({
    url: 'try/update/coupon',
    method: 'get',
  })
}


/**
 * 查询试用分类(更新试用)
 */
export function queryMarketingCatesByTypeForTryUpdate() {
  return request({
    url: 'try/update/cate',
    method: 'get',
  })
}

/**
 * 分页查询单品信息(更新试用)
 * @param params 查询参数
 */
export function querySkusForTryUpdate(params) {
  return request({
    url: 'try/update/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的互斥促销数量(更新试用)
 * @param marketing 试用促销
 */
export function queryExclusionMarketingCountForTryUpdate(marketing) {
  return request({
    url: 'try/update/exclusion',
    method: 'post',
    data: marketing
  })
}

/**
 *  根据促销id查询试用促销信息
 * @param id 促销id
 */
export function queryTryMarketingById(id) {
  return request({
    url: 'try/' + id,
    method: 'get',
  })
}

/**
 * 更新试用
 * @param marketing 试用促销
 */
export function updateTry(marketing) {
  return request({
    url: 'try',
    method: 'put',
    data: marketing
  })
}
