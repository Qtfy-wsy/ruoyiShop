import request from '@/utils/request'

// 查询门店支付类型列表
export function listTStorePaytype(query) {
  return request({
    url: '/store/TStorePaytype/list',
    method: 'get',
    params: query
  })
}

// 查询门店支付类型详细
export function getTStorePaytype(id) {
  return request({
    url: '/store/TStorePaytype/' + id,
    method: 'get'
  })
}

// 新增门店支付类型
export function addTStorePaytype(data) {
  return request({
    url: '/store/TStorePaytype',
    method: 'post',
    data: data
  })
}

// 修改门店支付类型
export function updateTStorePaytype(data) {
  return request({
    url: '/store/TStorePaytype',
    method: 'put',
    data: data
  })
}

// 删除门店支付类型
export function delTStorePaytype(id) {
  return request({
    url: '/store/TStorePaytype/' + id,
    method: 'delete'
  })
}

// 导出门店支付类型
export function exportTStorePaytype(query) {
  return request({
    url: '/store/TStorePaytype/export',
    method: 'get',
    params: query
  })
}