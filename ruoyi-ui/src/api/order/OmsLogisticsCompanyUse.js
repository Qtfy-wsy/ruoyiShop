import request from '@/utils/request'

// 查询店铺使用的物流公司列表
export function listOmsLogisticsCompanyUse(query) {
  return request({
    url: '/order/OmsLogisticsCompanyUse/list',
    method: 'get',
    params: query
  })
}

// 查询店铺使用的物流公司详细
export function getOmsLogisticsCompanyUse(id) {
  return request({
    url: '/order/OmsLogisticsCompanyUse/' + id,
    method: 'get'
  })
}

// 新增店铺使用的物流公司
export function addOmsLogisticsCompanyUse(data) {
  return request({
    url: '/order/OmsLogisticsCompanyUse',
    method: 'post',
    data: data
  })
}

// 修改店铺使用的物流公司
export function updateOmsLogisticsCompanyUse(data) {
  return request({
    url: '/order/OmsLogisticsCompanyUse',
    method: 'put',
    data: data
  })
}

// 删除店铺使用的物流公司
export function delOmsLogisticsCompanyUse(id) {
  return request({
    url: '/order/OmsLogisticsCompanyUse/' + id,
    method: 'delete'
  })
}

// 导出店铺使用的物流公司
export function exportOmsLogisticsCompanyUse(query) {
  return request({
    url: '/order/OmsLogisticsCompanyUse/export',
    method: 'get',
    params: query
  })
}