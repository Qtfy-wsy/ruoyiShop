import request from '@/utils/request'

// 查询商品列表
export function listGoods(query) {
  return request({
    url: '/goods/goods/list',
    method: 'get',
    params: query
  })
}

// 查询商品详细
export function getGoods(id) {
  return request({
    url: '/goods/goods/' + id,
    method: 'get'
  })
}

// 新增商品
export function addGoods(data) {
  return request({
    url: '/goods/goods',
    method: 'post',
    data: data
  })
}

// 修改商品
export function updateGoods(data) {
  return request({
    url: '/goods/goods',
    method: 'put',
    data: data
  })
}

// 删除商品
export function delGoods(id) {
  return request({
    url: '/goods/goods/' + id,
    method: 'delete'
  })
}

// 导出商品
export function exportGoods(query) {
  return request({
    url: '/goods/goods/export',
    method: 'get',
    params: query
  })
}
/**
 * 修改商品的上下架状态
 * @param ids 商品id
 * @param status 状态
 */
export function updateShelvesStatus(ids, status) {
  return request({
    url: '/goods/goods/updateshelvesstatus',
    method: 'put',
    params: {ids: ids, status: status}
  })
}
