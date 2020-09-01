import request from '@/utils/request'

// 查询订单操作日志列表
export function listOmsOrderOperationLog(query) {
  return request({
    url: '/order/OmsOrderOperationLog/list',
    method: 'get',
    params: query
  })
}

// 查询订单操作日志详细
export function getOmsOrderOperationLog(id) {
  return request({
    url: '/order/OmsOrderOperationLog/' + id,
    method: 'get'
  })
}

// 新增订单操作日志
export function addOmsOrderOperationLog(data) {
  return request({
    url: '/order/OmsOrderOperationLog',
    method: 'post',
    data: data
  })
}

// 修改订单操作日志
export function updateOmsOrderOperationLog(data) {
  return request({
    url: '/order/OmsOrderOperationLog',
    method: 'put',
    data: data
  })
}

// 删除订单操作日志
export function delOmsOrderOperationLog(id) {
  return request({
    url: '/order/OmsOrderOperationLog/' + id,
    method: 'delete'
  })
}

// 导出订单操作日志
export function exportOmsOrderOperationLog(query) {
  return request({
    url: '/order/OmsOrderOperationLog/export',
    method: 'get',
    params: query
  })
}