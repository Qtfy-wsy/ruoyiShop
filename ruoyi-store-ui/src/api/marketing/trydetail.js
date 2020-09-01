import request from '@/utils/request';


/**
 *  根据促销id查询试用促销信息
 * @param id 促销id
 */
export function queryTryMarketingById(id) {
  return request({
    url: 'try/detail/' + id,
    method: 'get',
  })
}


/**
 *  查询优惠券
 * @param params 查询参数
 */
export function queryCouponsForTry() {
  return request({
    url: 'try/detail/coupon',
    method: 'get',
  })
}


/**
 * 查询试用分类
 */
export function queryMarketingCatesByTypeForTry() {
  return request({
    url: 'try/detail/cate',
    method: 'get',
  })
}
