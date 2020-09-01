import request from '@/utils/request'

// 查询账单记录列表
export function listOmsBillingRecords(query) {
  return request({
    url: '/order/OmsBillingRecords/list',
    method: 'get',
    params: query
  })
}

// 查询账单记录详细
export function getOmsBillingRecords(id) {
  return request({
    url: '/order/OmsBillingRecords/' + id,
    method: 'get'
  })
}

// 新增账单记录
export function addOmsBillingRecords(data) {
  return request({
    url: '/order/OmsBillingRecords',
    method: 'post',
    data: data
  })
}

// 修改账单记录
export function updateOmsBillingRecords(data) {
  return request({
    url: '/order/OmsBillingRecords',
    method: 'put',
    data: data
  })
}

// 删除账单记录
export function delOmsBillingRecords(id) {
  return request({
    url: '/order/OmsBillingRecords/' + id,
    method: 'delete'
  })
}

// 导出账单记录
export function exportOmsBillingRecords(query) {
  return request({
    url: '/order/OmsBillingRecords/export',
    method: 'get',
    params: query
  })
}