import request from '@/utils/request';

/**
 * 分页查询自定义品牌
 *
 * @param query 查询参数
 */
export function queryMySelfBrands(query) {
  return request({
    url: '/goods/brand/custombrandaudit',
    method: 'get',
    params: query
  })
}


/**
 * 通过自定义品牌审核
 * @param id 品牌 ID
 */
export function passMySelfBrandAudit(id) {
  return request({
    url: 'custombrandaudit/pass/' + id,
    method: 'put',
  })
}


/**
 * 拒绝品牌审核
 * @param brandApply 商品审核实例
 */
export function refuseMySelfBrandAudit(brandApply) {
  return request({
    url: 'custombrandaudit/refuse',
    method: 'post',
    data: brandApply
  })
}


/**
 * 批量通过品牌审核
 * @param ids  通过的 id
 */
export function batchPassMySelfBrandAudit(ids) {
  return request({
    url: 'custombrandaudit/batch',
    method: 'put',
    params: ids
  })
}


/**
 * 批量拒绝品牌审核
 * @param param  拒绝参数
 */
export function batchRefuseMySelfBrandAudit(param) {
  return request({
    url: 'custombrandaudit/batchrefuse',
    method: 'put',
    params: param
  })
}

