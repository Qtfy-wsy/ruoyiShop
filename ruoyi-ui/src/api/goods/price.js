import request from '@/utils/request'

// 查询单品的会员价列表
export function listPrice(query) {
  return request({
    url: '/goods/price/list',
    method: 'get',
    params: query
  })
}

// 查询单品的会员价详细
export function getPrice(id) {
  return request({
    url: '/goods/price/' + id,
    method: 'get'
  })
}

// 新增单品的会员价
export function addPrice(data) {
  return request({
    url: '/goods/price',
    method: 'post',
    data: data
  })
}

// 修改单品的会员价
export function updatePrice(data) {
  return request({
    url: '/goods/price',
    method: 'put',
    data: data
  })
}

// 删除单品的会员价
export function delPrice(id) {
  return request({
    url: '/goods/price/' + id,
    method: 'delete'
  })
}

// 导出单品的会员价
export function exportPrice(query) {
  return request({
    url: '/goods/price/export',
    method: 'get',
    params: query
  })
}