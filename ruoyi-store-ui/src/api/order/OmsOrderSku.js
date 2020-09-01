import request from '@/utils/request'

// 查询订单单品列表
export function listOmsOrderSku(query) {
  return request({
    url: '/order/OmsOrderSku/list',
    method: 'get',
    params: query
  })
}

// 查询订单单品详细
export function getOmsOrderSku(id) {
  return request({
    url: '/order/OmsOrderSku/' + id,
    method: 'get'
  })
}

// 新增订单单品
export function addOmsOrderSku(data) {
  return request({
    url: '/order/OmsOrderSku',
    method: 'post',
    data: data
  })
}

// 修改订单单品
export function updateOmsOrderSku(data) {
  return request({
    url: '/order/OmsOrderSku',
    method: 'put',
    data: data
  })
}

// 删除订单单品
export function delOmsOrderSku(id) {
  return request({
    url: '/order/OmsOrderSku/' + id,
    method: 'delete'
  })
}

// 导出订单单品
export function exportOmsOrderSku(query) {
  return request({
    url: '/order/OmsOrderSku/export',
    method: 'get',
    params: query
  })
}