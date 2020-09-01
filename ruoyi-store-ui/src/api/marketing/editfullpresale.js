import request from '@/utils/request';

/**
 * 查询全款预售促销分类
 * @param query 查询参数
 */
export function queryMarketingCatesByTypeForFullPresale() {
  return request({
    url: 'fullpresale/cates',
    method: 'get',
  })
}

/**
 * 分页查询单品信息（全款预售用）
 * @param params 查询参数
 */
export function querySkusForFullPresale(params) {
  return request({
    url: 'fullpresale/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的 互斥促销数量
 * @param marketing 全款预售
 */
export function queryExclusionMarketingCountForFullPresale(marketing) {
  return request({
    url: 'fullpresale/exclusion',
    method: 'post',
    data: marketing
  })
}

/**
 * 添加全款预售促销
 * @param marketing 全款预售
 */
export function addFullPreSale(marketing) {
  return request({
    url: 'fullpresale',
    method: 'post',
    data: marketing
  })
}

/**
 * 根据促销id查询全款预售促销信息
 * @param id 全款预售id
 */
export function queryFullPresaleMarketingById(id) {
  return request({
    url: 'fullpresale/' + id,
    method: 'get'
  })
}

/**
 * 查询全款预售促销分类（修改全款预售用）
 */
export function queryMarketingCatesByTypeForFullPresaleUpdate() {
  return request({
    url: 'fullpresale/update/cates',
    method: 'get',
  })
}

/**
 * 分页查询单品信息（修改全款预售用）
 * @param params 查询参数
 */
export function querySkusForFullPresaleUpdate(params) {
  return request({
    url: 'fullpresale/update/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的 互斥促销数量
 * @param marketing 全款预售
 */
export function queryExclusionMarketingCountForFullPresaleUpdate(marketing) {
  return request({
    url: 'fullpresale/update/exclusion',
    method: 'post',
    data: marketing
  })
}

/**
 * 更新全款预售
 * @param marketing
 */
export function updatefullpresale(marketing) {
  return request({
    url: 'fullpresale',
    method: 'put',
    data: marketing
  })
}
