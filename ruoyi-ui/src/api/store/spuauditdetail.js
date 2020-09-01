import request from '@/utils/request';


/**
 * 根据商品id查询商品信息
 * @param 商品id
 */
export function querySpuByIdForStore(id) {
  return request({
    url: 'store/audit/spu/' + id,
    method: 'get',
  })
}

/**
 * 查询所有服务支持
 */
export function queryAllServiceSupportForstore() {
  return request({
    url: 'store/audit/servicesupport',
    method: 'get',
  })
}

// 审核通过
export function auditPass(spuId) {
  return request({
    url: 'store/audit/' + spuId,
    method: 'put',
  })
}

// 审核不通过
export function auditRefuse(spuId, reason) {
  return request({
    url: 'store/audit/refuse/' + spuId,
    method: 'put',
    params: {reason: reason}
  })
}

