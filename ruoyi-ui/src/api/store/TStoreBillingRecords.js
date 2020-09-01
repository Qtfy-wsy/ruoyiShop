import request from '@/utils/request'

// 查询门店账单收入支出列表
export function listTStoreBillingRecords(query) {
  return request({
    url: '/store/TStoreBillingRecords/list',
    method: 'get',
    params: query
  })
}

// 查询门店账单收入支出详细
export function getTStoreBillingRecords(id) {
  return request({
    url: '/store/TStoreBillingRecords/' + id,
    method: 'get'
  })
}

// 新增门店账单收入支出
export function addTStoreBillingRecords(data) {
  return request({
    url: '/store/TStoreBillingRecords',
    method: 'post',
    data: data
  })
}

// 修改门店账单收入支出
export function updateTStoreBillingRecords(data) {
  return request({
    url: '/store/TStoreBillingRecords',
    method: 'put',
    data: data
  })
}

// 删除门店账单收入支出
export function delTStoreBillingRecords(id) {
  return request({
    url: '/store/TStoreBillingRecords/' + id,
    method: 'delete'
  })
}

// 导出门店账单收入支出
export function exportTStoreBillingRecords(query) {
  return request({
    url: '/store/TStoreBillingRecords/export',
    method: 'get',
    params: query
  })
}