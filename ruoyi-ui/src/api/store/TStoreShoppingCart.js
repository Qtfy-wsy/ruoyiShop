import request from '@/utils/request'

// 查询门店购物车列表
export function listTStoreShoppingCart(query) {
  return request({
    url: '/store/TStoreShoppingCart/list',
    method: 'get',
    params: query
  })
}

// 查询门店购物车详细
export function getTStoreShoppingCart(id) {
  return request({
    url: '/store/TStoreShoppingCart/' + id,
    method: 'get'
  })
}

// 新增门店购物车
export function addTStoreShoppingCart(data) {
  return request({
    url: '/store/TStoreShoppingCart',
    method: 'post',
    data: data
  })
}

// 修改门店购物车
export function updateTStoreShoppingCart(data) {
  return request({
    url: '/store/TStoreShoppingCart',
    method: 'put',
    data: data
  })
}

// 删除门店购物车
export function delTStoreShoppingCart(id) {
  return request({
    url: '/store/TStoreShoppingCart/' + id,
    method: 'delete'
  })
}

// 导出门店购物车
export function exportTStoreShoppingCart(query) {
  return request({
    url: '/store/TStoreShoppingCart/export',
    method: 'get',
    params: query
  })
}