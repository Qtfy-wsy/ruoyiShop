import request from '@/utils/request';


/**
 * 查询满折的单品
 * @param params 查询参数
 */
export function querySkusForFullDiscount(params) {
  return request({
    url: 'fulldiscount/skus',
    method: 'get',
    params: params
  })
}


/**
 * 添加满折促销
 * @param fullDiscount 满折促销
 */
export function addFullDiscount(fullDiscount) {
  return request({
    url: 'fulldiscount',
    method: 'post',
    data: fullDiscount
  })
}


/**
 *   根据促销id查询满折促销信息
 * @param id 满折促销id
 */
export function queryFullDiscountMarketingById(id) {
  return request({
    url: 'fulldiscount/' + id,
    method: 'get'
  })
}


/**
 * 查询满折的单品
 * @param params 查询参数
 */
export function querySkusForFullDiscountUpdate(params) {
  return request({
    url: 'fulldiscount/update/skus',
    method: 'get',
    params: params
  })
}


/**
 * 修改满折促销
 * @param fullDiscount 满折促销
 */
export function updateFullDiscount(fullDiscount) {
  return request({
    url: 'fulldiscount',
    method: 'put',
    data: fullDiscount
  })
}
