import request from '@/utils/request';

/**
 * 分页查询待审核品牌
 *
 * @param query 查询参数
 */
export function queryBrandToBeAudit(query) {
  return request({
    url: 'brandaudit',
    method: 'get',
    params: query
  })
}


/**
 * 通过品牌审核
 * @param id 品牌 ID
 */
export function passBrandAudit(id) {
  return request({
    url: 'brandaudit/pass/' + id,
    method: 'put',
  })
}


/**
 * 拒绝品牌审核
 * @param brandApply 商品审核实例
 */
export function refuseBrandAudit(brandApply) {
  return request({
    url: 'brandaudit/refuse',
    method: 'post',
    data: brandApply
  })
}


/**
 * 批量通过品牌审核
 * @param ids  通过的 id
 */
export function batchPassBrandAudit(ids) {
  return request({
    url: 'brandaudit/batchpass',
    method: 'put',
    params: ids
  })
}


/**
 * 批量拒绝品牌审核
 * @param param  拒绝参数
 */
export function batchRefuseBrandAudit(param) {
  return request({
    url: 'brandaudit/batchrefuse',
    method: 'put',
    params: param
  })
}

