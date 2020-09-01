import request from '@/utils/request'

// 查询站内信列表
export function listLsStationLetter(query) {
  return request({
    url: '/setting/LsStationLetter/list',
    method: 'get',
    params: query
  })
}

// 查询站内信详细
export function getLsStationLetter(id) {
  return request({
    url: '/setting/LsStationLetter/' + id,
    method: 'get'
  })
}

// 新增站内信
export function addLsStationLetter(data) {
  return request({
    url: '/setting/LsStationLetter',
    method: 'post',
    data: data
  })
}

// 修改站内信
export function updateLsStationLetter(data) {
  return request({
    url: '/setting/LsStationLetter',
    method: 'put',
    data: data
  })
}

// 删除站内信
export function delLsStationLetter(id) {
  return request({
    url: '/setting/LsStationLetter/' + id,
    method: 'delete'
  })
}

// 导出站内信
export function exportLsStationLetter(query) {
  return request({
    url: '/setting/LsStationLetter/export',
    method: 'get',
    params: query
  })
}