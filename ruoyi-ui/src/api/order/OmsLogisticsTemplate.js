import request from '@/utils/request'

// 查询物流模版列表
export function listOmsLogisticsTemplate(query) {
  return request({
    url: '/order/OmsLogisticsTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询物流模版详细
export function getOmsLogisticsTemplate(id) {
  return request({
    url: '/order/OmsLogisticsTemplate/' + id,
    method: 'get'
  })
}

// 新增物流模版
export function addOmsLogisticsTemplate(data) {
  return request({
    url: '/order/OmsLogisticsTemplate',
    method: 'post',
    data: data
  })
}

// 修改物流模版
export function updateOmsLogisticsTemplate(data) {
  return request({
    url: '/order/OmsLogisticsTemplate',
    method: 'put',
    data: data
  })
}

// 删除物流模版
export function delOmsLogisticsTemplate(id) {
  return request({
    url: '/order/OmsLogisticsTemplate/' + id,
    method: 'delete'
  })
}

// 导出物流模版
export function exportOmsLogisticsTemplate(query) {
  return request({
    url: '/order/OmsLogisticsTemplate/export',
    method: 'get',
    params: query
  })
}