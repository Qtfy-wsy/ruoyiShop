import request from '@/utils/request'

// 查询店铺信息列表
export function listTStoreInfo(query) {
  return request({
    url: '/store/TStoreInfo/list',
    method: 'get',
    params: query
  })
}

// 查询店铺信息详细
export function getTStoreInfo(id) {
  return request({
    url: '/store/TStoreInfo/' + id,
    method: 'get'
  })
}

// 新增店铺信息
export function addTStoreInfo(data) {
  return request({
    url: '/store/TStoreInfo',
    method: 'post',
    data: data
  })
}

// 修改店铺信息
export function updateTStoreInfo(data) {
  return request({
    url: '/store/TStoreInfo',
    method: 'put',
    data: data
  })
}

// 删除店铺信息
export function delTStoreInfo(id) {
  return request({
    url: '/store/TStoreInfo/' + id,
    method: 'delete'
  })
}

// 导出店铺信息
export function exportTStoreInfo(query) {
  return request({
    url: '/store/TStoreInfo/export',
    method: 'get',
    params: query
  })
}

/**
 * 分页查询审核商家列表
 * @param query 查询参数
 */
export function querySellerAduitList(query) {
  return request({
    url: '/store/TStoreInfo/auditseller',
    method: 'get',
    params: query
  })
}


/**
 * 删除商家
 * @param id 商家 id
 */
export function deleteStore(id) {
  return request({
    url: '/store/TStoreInfo/auditseller/' + id,
    method: 'delete',
  })
}

/**
 * 商家通过审核
 * @param param 参数
 */
export function passStoreAudit(param) {
  return request({
    url: '/store/TStoreInfo/auditseller',
    method: 'put',
    data: param
  })
}


/**
 * 拒绝商家
 * @param param 参数
 */
export function refuseStoreAudit(param) {
  return request({
    url: '/store/TStoreInfo/auditseller/refuse',
    method: 'put',
    data: param
  })
}

/**
 * 批量关店
 * @param ids 参数
 */
export function closeStores(ids) {
  return request({
    url: '/store/TStoreInfo/auditseller/closestores',
    method: 'put',
    params: ids
  })
}

/**
 * 根据店铺id查询店铺详情信息
 * @param query 查询参数
 */
export function storeDetailInfoByStoreId(query) {
  return request({
    url: '/store/TStoreInfo/storedetails',
    method: 'get',
    params: query
  })
}
