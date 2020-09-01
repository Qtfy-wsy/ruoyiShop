import request from '@/utils/request'

// 查询退款退货操作日志列表
export function listOmsBackOrderLog(query) {
  return request({
    url: '/order/OmsBackOrderLog/list',
    method: 'get',
    params: query
  })
}

// 查询退款退货操作日志详细
export function getOmsBackOrderLog(id) {
  return request({
    url: '/order/OmsBackOrderLog/' + id,
    method: 'get'
  })
}

// 新增退款退货操作日志
export function addOmsBackOrderLog(data) {
  return request({
    url: '/order/OmsBackOrderLog',
    method: 'post',
    data: data
  })
}

// 修改退款退货操作日志
export function updateOmsBackOrderLog(data) {
  return request({
    url: '/order/OmsBackOrderLog',
    method: 'put',
    data: data
  })
}

// 删除退款退货操作日志
export function delOmsBackOrderLog(id) {
  return request({
    url: '/order/OmsBackOrderLog/' + id,
    method: 'delete'
  })
}

// 导出退款退货操作日志
export function exportOmsBackOrderLog(query) {
  return request({
    url: '/order/OmsBackOrderLog/export',
    method: 'get',
    params: query
  })
}