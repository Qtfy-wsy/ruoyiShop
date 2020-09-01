import request from '@/utils/request'

// 查询单品评论列表
export function listComment(query) {
  return request({
    url: '/goods/comment/list',
    method: 'get',
    params: query
  })
}

// 查询单品评论详细
export function getComment(id) {
  return request({
    url: '/goods/comment/' + id,
    method: 'get'
  })
}

// 新增单品评论
export function addComment(data) {
  return request({
    url: '/goods/comment',
    method: 'post',
    data: data
  })
}

// 修改单品评论
export function updateComment(data) {
  return request({
    url: '/goods/comment',
    method: 'put',
    data: data
  })
}

// 删除单品评论
export function delComment(id) {
  return request({
    url: '/goods/comment/' + id,
    method: 'delete'
  })
}

// 导出单品评论
export function exportComment(query) {
  return request({
    url: '/goods/comment/export',
    method: 'get',
    params: query
  })
}