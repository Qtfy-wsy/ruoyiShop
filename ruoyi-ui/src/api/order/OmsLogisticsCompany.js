import request from '@/utils/request'

// 查询物流公司列表
export function listOmsLogisticsCompany(query) {
  return request({
    url: '/order/OmsLogisticsCompany/list',
    method: 'get',
    params: query
  })
}

// 查询物流公司详细
export function getOmsLogisticsCompany(id) {
  return request({
    url: '/order/OmsLogisticsCompany/' + id,
    method: 'get'
  })
}

// 新增物流公司
export function addOmsLogisticsCompany(data) {
  return request({
    url: '/order/OmsLogisticsCompany',
    method: 'post',
    data: data
  })
}

// 修改物流公司
export function updateOmsLogisticsCompany(data) {
  return request({
    url: '/order/OmsLogisticsCompany',
    method: 'put',
    data: data
  })
}

// 删除物流公司
export function delOmsLogisticsCompany(id) {
  return request({
    url: '/order/OmsLogisticsCompany/' + id,
    method: 'delete'
  })
}

// 导出物流公司
export function exportOmsLogisticsCompany(query) {
  return request({
    url: '/order/OmsLogisticsCompany/export',
    method: 'get',
    params: query
  })
}