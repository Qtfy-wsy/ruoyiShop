import request from '@/utils/request'

// 查询门店订单操作日志列表
export function listTStoreOrderOperationLog(query) {
  return request({
    url: '/store/TStoreOrderOperationLog/list',
    method: 'get',
    params: query
  })
}

// 查询门店订单操作日志详细
export function getTStoreOrderOperationLog(id) {
  return request({
    url: '/store/TStoreOrderOperationLog/' + id,
    method: 'get'
  })
}

// 新增门店订单操作日志
export function addTStoreOrderOperationLog(data) {
  return request({
    url: '/store/TStoreOrderOperationLog',
    method: 'post',
    data: data
  })
}

// 修改门店订单操作日志
export function updateTStoreOrderOperationLog(data) {
  return request({
    url: '/store/TStoreOrderOperationLog',
    method: 'put',
    data: data
  })
}

// 删除门店订单操作日志
export function delTStoreOrderOperationLog(id) {
  return request({
    url: '/store/TStoreOrderOperationLog/' + id,
    method: 'delete'
  })
}

// 导出门店订单操作日志
export function exportTStoreOrderOperationLog(query) {
  return request({
    url: '/store/TStoreOrderOperationLog/export',
    method: 'get',
    params: query
  })
}