import request from '@/utils/request'

// 查询商品关注列表
export function listAttention(query) {
  return request({
    url: '/goods/attention/list',
    method: 'get',
    params: query
  })
}

// 查询商品关注详细
export function getAttention(id) {
  return request({
    url: '/goods/attention/' + id,
    method: 'get'
  })
}

// 新增商品关注
export function addAttention(data) {
  return request({
    url: '/goods/attention',
    method: 'post',
    data: data
  })
}

// 修改商品关注
export function updateAttention(data) {
  return request({
    url: '/goods/attention',
    method: 'put',
    data: data
  })
}

// 删除商品关注
export function delAttention(id) {
  return request({
    url: '/goods/attention/' + id,
    method: 'delete'
  })
}

// 导出商品关注
export function exportAttention(query) {
  return request({
    url: '/goods/attention/export',
    method: 'get',
    params: query
  })
}