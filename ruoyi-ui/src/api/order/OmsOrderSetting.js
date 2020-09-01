import request from '@/utils/request'

// 查询订单设置列表
export function listOmsOrderSetting(query) {
  return request({
    url: '/order/OmsOrderSetting/list',
    method: 'get',
    params: query
  })
}

// 查询订单设置详细
export function getOmsOrderSetting(id) {
  return request({
    url: '/order/OmsOrderSetting/' + id,
    method: 'get'
  })
}

// 新增订单设置
export function addOmsOrderSetting(data) {
  return request({
    url: '/order/OmsOrderSetting',
    method: 'post',
    data: data
  })
}

// 修改订单设置
export function updateOmsOrderSetting(data) {
  return request({
    url: '/order/OmsOrderSetting',
    method: 'put',
    data: data
  })
}

// 删除订单设置
export function delOmsOrderSetting(id) {
  return request({
    url: '/order/OmsOrderSetting/' + id,
    method: 'delete'
  })
}

// 导出订单设置
export function exportOmsOrderSetting(query) {
  return request({
    url: '/order/OmsOrderSetting/export',
    method: 'get',
    params: query
  })
}