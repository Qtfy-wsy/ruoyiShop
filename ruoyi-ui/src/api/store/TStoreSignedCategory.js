import request from '@/utils/request'

// 查询店铺的签约分类列表
export function listTStoreSignedCategory(query) {
  return request({
    url: '/store/TStoreSignedCategory/list',
    method: 'get',
    params: query
  })
}

// 查询店铺的签约分类详细
export function getTStoreSignedCategory(id) {
  return request({
    url: '/store/TStoreSignedCategory/' + id,
    method: 'get'
  })
}

// 新增店铺的签约分类
export function addTStoreSignedCategory(data) {
  return request({
    url: '/store/TStoreSignedCategory',
    method: 'post',
    data: data
  })
}

// 修改店铺的签约分类
export function updateTStoreSignedCategory(data) {
  return request({
    url: '/store/TStoreSignedCategory',
    method: 'put',
    data: data
  })
}

// 删除店铺的签约分类
export function delTStoreSignedCategory(id) {
  return request({
    url: '/store/TStoreSignedCategory/' + id,
    method: 'delete'
  })
}

// 导出店铺的签约分类
export function exportTStoreSignedCategory(query) {
  return request({
    url: '/store/TStoreSignedCategory/export',
    method: 'get',
    params: query
  })
}