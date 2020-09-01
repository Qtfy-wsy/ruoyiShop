import request from '@/utils/request'

// 查询商品属性列表
export function listAttribute(query) {
  return request({
    url: '/goods/attribute/list',
    method: 'get',
    params: query
  })
}

// 查询商品属性详细
export function getAttribute(id) {
  return request({
    url: '/goods/attribute/' + id,
    method: 'get'
  })
}

// 新增商品属性
export function addAttribute(data) {
  return request({
    url: '/goods/attribute',
    method: 'post',
    data: data
  })
}

// 修改商品属性
export function updateAttribute(data) {
  return request({
    url: '/goods/attribute',
    method: 'put',
    data: data
  })
}

// 删除商品属性
export function delAttribute(id) {
  return request({
    url: '/goods/attribute/' + id,
    method: 'delete'
  })
}

// 导出商品属性
export function exportAttribute(query) {
  return request({
    url: '/goods/attribute/export',
    method: 'get',
    params: query
  })
}