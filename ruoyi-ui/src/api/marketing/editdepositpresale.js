import request from '@/utils/request';

/**
 * 查询定金预售促销分类
 * @param query 查询参数
 */
export function queryMarketingCatesByTypeForPresale(params) {
  return request({
    url: 'marketingcates',
    method: 'get',
    params: params
  })
}

/**
 * 分页查询单品信息（定金预售用）
 * @param params 查询参数
 */
export function querySkusForDepositPresale(params) {
  return request({
    url: 'depositpresale/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的互斥促销数量（定金预售用）
 * @param marketing 定金预售
 */
export function queryExclusionMarketingCountForDepositPresale(marketing) {
  return request({
    url: 'depositpresale/exclusion',
    method: 'post',
    data: marketing
  })
}

/**
 * 添加定金预售
 * @param marketing 定金预售
 */
export function addDepositPreSale(marketing) {
  return request({
    url: 'depositpresale',
    method: 'post',
    data: marketing
  })
}


/**
 * 根据促销id查询定金预售促销信息
 * @param id 促销id
 */
export function queryDepositPresaleMarketingById(id) {
  return request({
    url: 'depositpresale/' + id,
    method: 'get'
  })
}

/**
 *  查询定金预售促销分类（修改定金预售用）
 */
export function queryMarketingCatesByTypeForPresaleUpdate(params) {
  return request({
    url: 'marketingcates',
    method: 'get',
    params: params
  })
}


/**
 * 分页查询单品信息（修改定金预售用）
 * @param params 查询参数
 */
export function querySkusForDepositPresaleUpdate(params) {
  return request({
    url: 'depositpresale/update/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的互斥促销数量（修改定金预售用）
 * @param marketing
 */
export function queryExclusionMarketingCountForDepositPresaleUpdate(marketing) {
  return request({
    url: 'depositpresale/update/exclusion',
    method: 'post',
    data: marketing
  })
}

/**
 *  更新定金预售
 * @param marketing
 */
export function updateDepositPreSale(marketing) {
  return request({
    url: 'depositpresale',
    method: 'put',
    data: marketing
  })
}

