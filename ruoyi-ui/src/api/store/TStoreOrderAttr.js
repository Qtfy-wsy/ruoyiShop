import request from '@/utils/request'

// 查询门店订单附属信息列表
export function listTStoreOrderAttr(query) {
  return request({
    url: '/store/TStoreOrderAttr/list',
    method: 'get',
    params: query
  })
}

// 查询门店订单附属信息详细
export function getTStoreOrderAttr(id) {
  return request({
    url: '/store/TStoreOrderAttr/' + id,
    method: 'get'
  })
}

// 新增门店订单附属信息
export function addTStoreOrderAttr(data) {
  return request({
    url: '/store/TStoreOrderAttr',
    method: 'post',
    data: data
  })
}

// 修改门店订单附属信息
export function updateTStoreOrderAttr(data) {
  return request({
    url: '/store/TStoreOrderAttr',
    method: 'put',
    data: data
  })
}

// 删除门店订单附属信息
export function delTStoreOrderAttr(id) {
  return request({
    url: '/store/TStoreOrderAttr/' + id,
    method: 'delete'
  })
}

// 导出门店订单附属信息
export function exportTStoreOrderAttr(query) {
  return request({
    url: '/store/TStoreOrderAttr/export',
    method: 'get',
    params: query
  })
}