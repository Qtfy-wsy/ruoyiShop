import request from '@/utils/request'

// 查询门店订单列表
export function listTStoreOrder(query) {
  return request({
    url: '/store/TStoreOrder/list',
    method: 'get',
    params: query
  })
}

// 查询门店订单详细
export function getTStoreOrder(id) {
  return request({
    url: '/store/TStoreOrder/' + id,
    method: 'get'
  })
}

// 新增门店订单
export function addTStoreOrder(data) {
  return request({
    url: '/store/TStoreOrder',
    method: 'post',
    data: data
  })
}

// 修改门店订单
export function updateTStoreOrder(data) {
  return request({
    url: '/store/TStoreOrder',
    method: 'put',
    data: data
  })
}

// 删除门店订单
export function delTStoreOrder(id) {
  return request({
    url: '/store/TStoreOrder/' + id,
    method: 'delete'
  })
}

// 导出门店订单
export function exportTStoreOrder(query) {
  return request({
    url: '/store/TStoreOrder/export',
    method: 'get',
    params: query
  })
}