import request from '@/utils/request'

// 查询门店预约列表
export function listTStoreReservation(query) {
  return request({
    url: '/store/TStoreReservation/list',
    method: 'get',
    params: query
  })
}

// 查询门店预约详细
export function getTStoreReservation(id) {
  return request({
    url: '/store/TStoreReservation/' + id,
    method: 'get'
  })
}

// 新增门店预约
export function addTStoreReservation(data) {
  return request({
    url: '/store/TStoreReservation',
    method: 'post',
    data: data
  })
}

// 修改门店预约
export function updateTStoreReservation(data) {
  return request({
    url: '/store/TStoreReservation',
    method: 'put',
    data: data
  })
}

// 删除门店预约
export function delTStoreReservation(id) {
  return request({
    url: '/store/TStoreReservation/' + id,
    method: 'delete'
  })
}

// 导出门店预约
export function exportTStoreReservation(query) {
  return request({
    url: '/store/TStoreReservation/export',
    method: 'get',
    params: query
  })
}