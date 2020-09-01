import request from '@/utils/request';

/**
 * 分页查询单品信息
 * @param params 查询参数
 */
export function querySkusForFullGift(params) {
  return request({
    url: 'fullgift/skus',
    method: 'get',
    params: params
  })
}

/**
 * 添加满赠促销
 * @param fullGift 满赠促销
 */
export function addFullGiftMarketing(fullGift) {
  return request({
    url: 'fullgift',
    method: 'post',
    data: fullGift
  })
}

/**
 *  根据促销id查询满赠促销信息
 * @param id 满赠id
 */
export function queryFullGiftMarketingById(id) {
  return request({
    url: 'fullgift/' + id,
    method: 'get',
  })
}


/**
 * 分页查询单品信息(修改满赠用)
 * @param params 查询参数
 */
export function querySkusForFullGiftUpdate(params) {
  return request({
    url: 'fullgift/update/skus',
    method: 'get',
    params: params
  })
}

/**
 * 修改满赠促销
 * @param fullGift 满赠促销
 */
export function updateFullGift(fullGift) {
  return request({
    url: 'fullgift',
    method: 'put',
    data: fullGift
  })
}
