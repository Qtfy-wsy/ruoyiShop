import request from '@/utils/request'

// 查询店铺分类列表
export function listTStoreCategory(query) {
  return request({
    url: '/store/TStoreCategory/list',
    method: 'get',
    params: query
  })
}

// 查询店铺分类详细
export function getTStoreCategory(id) {
  return request({
    url: '/store/TStoreCategory/' + id,
    method: 'get'
  })
}

// 新增店铺分类
export function addTStoreCategory(data) {
  return request({
    url: '/store/TStoreCategory',
    method: 'post',
    data: data
  })
}

// 修改店铺分类
export function updateTStoreCategory(data) {
  return request({
    url: '/store/TStoreCategory',
    method: 'put',
    data: data
  })
}

// 删除店铺分类
export function delTStoreCategory(id) {
  return request({
    url: '/store/TStoreCategory/' + id,
    method: 'delete'
  })
}

// 导出店铺分类
export function exportTStoreCategory(query) {
  return request({
    url: '/store/TStoreCategory/export',
    method: 'get',
    params: query
  })
}