import request from '@/utils/request'

// 查询单品的规格值列表
export function listValue(query) {
  return request({
    url: '/goods/value/list',
    method: 'get',
    params: query
  })
}

// 查询单品的规格值详细
export function getValue(id) {
  return request({
    url: '/goods/value/' + id,
    method: 'get'
  })
}

// 新增单品的规格值
export function addValue(data) {
  return request({
    url: '/goods/value',
    method: 'post',
    data: data
  })
}

// 修改单品的规格值
export function updateValue(data) {
  return request({
    url: '/goods/value',
    method: 'put',
    data: data
  })
}

// 删除单品的规格值
export function delValue(id) {
  return request({
    url: '/goods/value/' + id,
    method: 'delete'
  })
}

// 导出单品的规格值
export function exportValue(query) {
  return request({
    url: '/goods/value/export',
    method: 'get',
    params: query
  })
}