import request from '@/utils/request'

// 查询单品列表
export function listSku(query) {
  return request({
    url: '/goods/sku/list',
    method: 'get',
    params: query
  })
}

// 查询单品详细
export function getSku(id) {
  return request({
    url: '/goods/sku/' + id,
    method: 'get'
  })
}

// 新增单品
export function addSku(data) {
  return request({
    url: '/goods/sku',
    method: 'post',
    data: data
  })
}

// 修改单品
export function updateSku(data) {
  return request({
    url: '/goods/sku',
    method: 'put',
    data: data
  })
}

// 删除单品
export function delSku(id) {
  return request({
    url: '/goods/sku/' + id,
    method: 'delete'
  })
}

// 导出单品
export function exportSku(query) {
  return request({
    url: '/goods/sku/export',
    method: 'get',
    params: query
  })
}