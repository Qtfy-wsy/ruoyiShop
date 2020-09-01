import request from '@/utils/request'

// 查询评论回复列表
export function listReplay(query) {
  return request({
    url: '/goods/replay/list',
    method: 'get',
    params: query
  })
}

// 查询评论回复详细
export function getReplay(id) {
  return request({
    url: '/goods/replay/' + id,
    method: 'get'
  })
}

// 新增评论回复
export function addReplay(data) {
  return request({
    url: '/goods/replay',
    method: 'post',
    data: data
  })
}

// 修改评论回复
export function updateReplay(data) {
  return request({
    url: '/goods/replay',
    method: 'put',
    data: data
  })
}

// 删除评论回复
export function delReplay(id) {
  return request({
    url: '/goods/replay/' + id,
    method: 'delete'
  })
}

// 导出评论回复
export function exportReplay(query) {
  return request({
    url: '/goods/replay/export',
    method: 'get',
    params: query
  })
}