import request from '@/utils/request'

// 查询支付流水列表
export function listOmsTransRecords(query) {
  return request({
    url: '/order/OmsTransRecords/list',
    method: 'get',
    params: query
  })
}

// 查询支付流水详细
export function getOmsTransRecords(id) {
  return request({
    url: '/order/OmsTransRecords/' + id,
    method: 'get'
  })
}

// 新增支付流水
export function addOmsTransRecords(data) {
  return request({
    url: '/order/OmsTransRecords',
    method: 'post',
    data: data
  })
}

// 修改支付流水
export function updateOmsTransRecords(data) {
  return request({
    url: '/order/OmsTransRecords',
    method: 'put',
    data: data
  })
}

// 删除支付流水
export function delOmsTransRecords(id) {
  return request({
    url: '/order/OmsTransRecords/' + id,
    method: 'delete'
  })
}

// 导出支付流水
export function exportOmsTransRecords(query) {
  return request({
    url: '/order/OmsTransRecords/export',
    method: 'get',
    params: query
  })
}