import request from '@/utils/request'

// 查询门店订单单品信息列表
export function listTStoreOrderSku(query) {
  return request({
    url: '/store/TStoreOrderSku/list',
    method: 'get',
    params: query
  })
}

// 查询门店订单单品信息详细
export function getTStoreOrderSku(id) {
  return request({
    url: '/store/TStoreOrderSku/' + id,
    method: 'get'
  })
}

// 新增门店订单单品信息
export function addTStoreOrderSku(data) {
  return request({
    url: '/store/TStoreOrderSku',
    method: 'post',
    data: data
  })
}

// 修改门店订单单品信息
export function updateTStoreOrderSku(data) {
  return request({
    url: '/store/TStoreOrderSku',
    method: 'put',
    data: data
  })
}

// 删除门店订单单品信息
export function delTStoreOrderSku(id) {
  return request({
    url: '/store/TStoreOrderSku/' + id,
    method: 'delete'
  })
}

// 导出门店订单单品信息
export function exportTStoreOrderSku(query) {
  return request({
    url: '/store/TStoreOrderSku/export',
    method: 'get',
    params: query
  })
}