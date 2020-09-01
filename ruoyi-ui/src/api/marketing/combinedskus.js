import request from '@/utils/request';

/**
 * 分页查找商品组合下的商品
 * @param 查询参数
 */
export function queryGoodsOfGoodsCombination(query, id) {
  return request({
    url: 'goodscombination/skus/' + id,
    method: 'get',
    params: query
  })
}

/**
 * 删除商品组合下的单品
 * @param ids 单品编号集合
 */
export function deletecombinationsku(ids) {
  return request({
    url: 'goodscombination/skus',
    method: 'delete',
    params: ids
  })
}

/**
 * 查询单品信息
 * @param query 查询条件
 */
export function querySkusForGoodsCombination(query) {
  return request({
    url: 'goodscombination/skus',
    method: 'get',
    params: query
  })
}

/**
 * 含有相同单品的有效互斥促销数量
 * @param skuIds 单品id
 */
export function queryExclusionMarketingCountBySkuId(skuIds) {
  return request({
    url: 'goodscombination/exclusionmarketingcountbyskuid',
    method: 'get',
    params: skuIds
  })
}

/**
 * 添加商品组合下的单品
 * @param combinationSkus 商品组合下的单品
 */
export function addCombinationSku(combinationSkus) {
  return request({
    url: 'goodscombination/skus',
    method: 'post',
    data: combinationSkus
  })
}
