import request from '@/utils/request'

// 查询区域区列表
export function listLsDistrict(query) {
  return request({
    url: '/setting/LsDistrict/list',
    method: 'get',
    params: query
  })
}

// 查询区域区详细
export function getLsDistrict(id) {
  return request({
    url: '/setting/LsDistrict/' + id,
    method: 'get'
  })
}

// 新增区域区
export function addLsDistrict(data) {
  return request({
    url: '/setting/LsDistrict',
    method: 'post',
    data: data
  })
}

// 修改区域区
export function updateLsDistrict(data) {
  return request({
    url: '/setting/LsDistrict',
    method: 'put',
    data: data
  })
}

// 删除区域区
export function delLsDistrict(id) {
  return request({
    url: '/setting/LsDistrict/' + id,
    method: 'delete'
  })
}

// 导出区域区
export function exportLsDistrict(query) {
  return request({
    url: '/setting/LsDistrict/export',
    method: 'get',
    params: query
  })
}