import request from '@/utils/request'

// 查询订单属性列表
export function listOmsOrderAttr(query) {
  return request({
    url: '/order/OmsOrderAttr/list',
    method: 'get',
    params: query
  })
}

// 查询订单属性详细
export function getOmsOrderAttr(id) {
  return request({
    url: '/order/OmsOrderAttr/' + id,
    method: 'get'
  })
}

// 新增订单属性
export function addOmsOrderAttr(data) {
  return request({
    url: '/order/OmsOrderAttr',
    method: 'post',
    data: data
  })
}

// 修改订单属性
export function updateOmsOrderAttr(data) {
  return request({
    url: '/order/OmsOrderAttr',
    method: 'put',
    data: data
  })
}

// 删除订单属性
export function delOmsOrderAttr(id) {
  return request({
    url: '/order/OmsOrderAttr/' + id,
    method: 'delete'
  })
}

// 导出订单属性
export function exportOmsOrderAttr(query) {
  return request({
    url: '/order/OmsOrderAttr/export',
    method: 'get',
    params: query
  })
}