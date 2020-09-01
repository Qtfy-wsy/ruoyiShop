import request from '@/utils/request'

// 查询店铺评论列表
export function listTStoreComment(query) {
  return request({
    url: '/store/TStoreComment/list',
    method: 'get',
    params: query
  })
}

// 查询店铺评论详细
export function getTStoreComment(id) {
  return request({
    url: '/store/TStoreComment/' + id,
    method: 'get'
  })
}

// 新增店铺评论
export function addTStoreComment(data) {
  return request({
    url: '/store/TStoreComment',
    method: 'post',
    data: data
  })
}

// 修改店铺评论
export function updateTStoreComment(data) {
  return request({
    url: '/store/TStoreComment',
    method: 'put',
    data: data
  })
}

// 删除店铺评论
export function delTStoreComment(id) {
  return request({
    url: '/store/TStoreComment/' + id,
    method: 'delete'
  })
}

// 导出店铺评论
export function exportTStoreComment(query) {
  return request({
    url: '/store/TStoreComment/export',
    method: 'get',
    params: query
  })
}