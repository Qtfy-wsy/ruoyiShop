import request from '@/utils/request';


/**
 * 查询限时折扣的单品
 * @param params 查询参数
 */
export function querySkusForPanicBuy(params) {
  return request({
    url: 'panicbuy/skus',
    method: 'get',
    params: params
  })
}

/**
 * 添加折扣促销
 * @param panic 折扣促销
 */
export function addPanicMarketing(panic) {
  return request({
    url: 'panicbuy',
    method: 'post',
    data: panic
  })
}

/**
 * 查询折扣促销的单品是否冲突
 * @param marketing 促销新信息
 */
export function queryExclusionMarketingCountForPanicBuy(marketing) {
  return request({
    url: 'panicbuy/exclusionmarketingcount',
    method: 'post',
    data: marketing
  })
}

/**
 * 查询限时折扣信息
 * @param id  限时折扣id
 */
export function queryPanicBuy(id) {
  return request({
    url: 'panicbuy/' + id,
    method: 'get',
  })
}

/**
 * 查询限时折扣信息
 * @param id  限时折扣id
 */
export function queryPanicBuyForDetail(id) {
  return request({
    url: 'panicbuy/detail/' + id,
    method: 'get',
  })
}


/**
 * 查询限时折扣的单品(修改)
 * @param params 查询参数
 */
export function querySkusForPanicBuyUpdate(params) {
  return request({
    url: 'panicbuy/update/skus',
    method: 'get',
    params: params
  })
}


/**
 * 查询折扣促销的单品是否冲突(修改用)
 * @param marketing 促销新信息
 */
export function queryExclusionMarketingCountForPanicBuyUpdate(marketing) {
  return request({
    url: 'panicbuy/update/exclusionmarketingcount',
    method: 'post',
    data: marketing
  })
}

/**
 * 更新限时折扣
 * @param panic 限时折扣
 */
export function updatePanic(panic) {
  return request({
    url: 'panicbuy',
    method: 'put',
    data: panic
  })
}
