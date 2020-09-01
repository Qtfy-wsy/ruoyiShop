import request from '@/utils/request'

// 查询佣金记录列表
export function listOmsCommissionRecords(query) {
  return request({
    url: '/order/OmsCommissionRecords/list',
    method: 'get',
    params: query
  })
}

// 查询佣金记录详细
export function getOmsCommissionRecords(id) {
  return request({
    url: '/order/OmsCommissionRecords/' + id,
    method: 'get'
  })
}

// 新增佣金记录
export function addOmsCommissionRecords(data) {
  return request({
    url: '/order/OmsCommissionRecords',
    method: 'post',
    data: data
  })
}

// 修改佣金记录
export function updateOmsCommissionRecords(data) {
  return request({
    url: '/order/OmsCommissionRecords',
    method: 'put',
    data: data
  })
}

// 删除佣金记录
export function delOmsCommissionRecords(id) {
  return request({
    url: '/order/OmsCommissionRecords/' + id,
    method: 'delete'
  })
}

// 导出佣金记录
export function exportOmsCommissionRecords(query) {
  return request({
    url: '/order/OmsCommissionRecords/export',
    method: 'get',
    params: query
  })
}