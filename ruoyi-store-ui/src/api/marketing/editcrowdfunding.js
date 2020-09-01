import request from '@/utils/request';

/**
 * 分页查询单品信息
 * @param params 查询参数
 */
export function querySimpleSpus(params) {
  return request({
    url: 'crowdfunding/spus',
    method: 'get',
    params: params
  })
}

/**
 *  根据商品id查询单品信息
 * @param spuId 商品id
 */
export function querySkusBySpuId(spuId) {
  return request({
    url: 'crowdfunding/skus/' + spuId,
    method: 'get',
  })
}

/**
 * 查询众筹分类
 */
export function queryMarketingCatesByTypeForCrowdfunding() {
  return request({
    url: 'crowdfunding/cates',
    method: 'get',
  })
}

/**
 *查询交叉时间内含有相同单品的互斥促销数量（众筹用）
 * @param crowdfunding 众筹促销
 */
export function queryExclusionMarketingCountForCrowdfunding(crowdfunding) {
  return request({
    url: 'crowdfunding/exclusion',
    method: 'post',
    data: crowdfunding
  })
}

/**
 * 添加众筹促销
 * @param crowdfunding 众筹促销a
 */
export function addCrowdFundingMarketing(crowdfunding) {
  return request({
    url: 'crowdfunding',
    method: 'post',
    data: crowdfunding
  })
}

/**
 * 根据促销id查询众筹促销信息
 * @param id 粗细id
 */
export function queryCrowdfundingMarketingById(id) {
  return request({
    url: 'crowdfunding/' + id,
    method: 'get',
  })
}


/**
 * 查询众筹分类(修改众筹用)
 */
export function queryMarketingCatesByTypeForCrowdfundingUpdate() {
  return request({
    url: 'crowdfunding/update/cates',
    method: 'get',
  })
}

/**
 * 分页查询单品信息(修改用)
 * @param params 查询参数
 */
export function querySimpleSpusUpdate(params) {
  return request({
    url: 'crowdfunding/update/spus',
    method: 'get',
    params: params
  })
}

/**
 *  根据商品id查询单品信息(修改用)
 * @param spuId 商品id
 */
export function querySkusBySpuIdUpdate(spuId) {
  return request({
    url: 'crowdfunding/update/skus/' + spuId,
    method: 'get',
  })
}


/**
 *查询交叉时间内含有相同单品的互斥促销数量（修改众筹用）
 * @param crowdfunding 众筹促销
 */
export function queryExclusionMarketingCountForCrowdfundingUpdate(crowdfunding) {
  return request({
    url: 'crowdfunding/update/exclusion',
    method: 'post',
    data: crowdfunding
  })
}

/**
 * 更新众筹
 * @param crowdfunding 众筹
 */
export function updateCrowdFunding(crowdfunding) {
  return request({
    url: 'crowdfunding',
    method: 'put',
    data: crowdfunding
  })
}
