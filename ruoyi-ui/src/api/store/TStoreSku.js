import request from '@/utils/request'

// 查询门店单品列表
export function listTStoreSku(query) {
  return request({
    url: '/store/TStoreSku/list',
    method: 'get',
    params: query
  })
}

// 查询门店单品详细
export function getTStoreSku(id) {
  return request({
    url: '/store/TStoreSku/' + id,
    method: 'get'
  })
}

// 新增门店单品
export function addTStoreSku(data) {
  return request({
    url: '/store/TStoreSku',
    method: 'post',
    data: data
  })
}

// 修改门店单品
export function updateTStoreSku(data) {
  return request({
    url: '/store/TStoreSku',
    method: 'put',
    data: data
  })
}

// 删除门店单品
export function delTStoreSku(id) {
  return request({
    url: '/store/TStoreSku/' + id,
    method: 'delete'
  })
}

// 导出门店单品
export function exportTStoreSku(query) {
  return request({
    url: '/store/TStoreSku/export',
    method: 'get',
    params: query
  })
}