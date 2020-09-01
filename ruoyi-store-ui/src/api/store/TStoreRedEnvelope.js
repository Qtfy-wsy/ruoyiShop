import request from '@/utils/request'

// 查询门店红包列表
export function listTStoreRedEnvelope(query) {
  return request({
    url: '/store/TStoreRedEnvelope/list',
    method: 'get',
    params: query
  })
}

// 查询门店红包详细
export function getTStoreRedEnvelope(id) {
  return request({
    url: '/store/TStoreRedEnvelope/' + id,
    method: 'get'
  })
}

// 新增门店红包
export function addTStoreRedEnvelope(data) {
  return request({
    url: '/store/TStoreRedEnvelope',
    method: 'post',
    data: data
  })
}

// 修改门店红包
export function updateTStoreRedEnvelope(data) {
  return request({
    url: '/store/TStoreRedEnvelope',
    method: 'put',
    data: data
  })
}

// 删除门店红包
export function delTStoreRedEnvelope(id) {
  return request({
    url: '/store/TStoreRedEnvelope/' + id,
    method: 'delete'
  })
}

// 导出门店红包
export function exportTStoreRedEnvelope(query) {
  return request({
    url: '/store/TStoreRedEnvelope/export',
    method: 'get',
    params: query
  })
}