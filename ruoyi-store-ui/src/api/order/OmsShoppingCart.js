import request from '@/utils/request'

// 查询购物车列表
export function listOmsShoppingCart(query) {
  return request({
    url: '/order/OmsShoppingCart/list',
    method: 'get',
    params: query
  })
}

// 查询购物车详细
export function getOmsShoppingCart(id) {
  return request({
    url: '/order/OmsShoppingCart/' + id,
    method: 'get'
  })
}

// 新增购物车
export function addOmsShoppingCart(data) {
  return request({
    url: '/order/OmsShoppingCart',
    method: 'post',
    data: data
  })
}

// 修改购物车
export function updateOmsShoppingCart(data) {
  return request({
    url: '/order/OmsShoppingCart',
    method: 'put',
    data: data
  })
}

// 删除购物车
export function delOmsShoppingCart(id) {
  return request({
    url: '/order/OmsShoppingCart/' + id,
    method: 'delete'
  })
}

// 导出购物车
export function exportOmsShoppingCart(query) {
  return request({
    url: '/order/OmsShoppingCart/export',
    method: 'get',
    params: query
  })
}