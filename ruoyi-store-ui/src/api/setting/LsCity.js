import request from '@/utils/request'

// 查询区域市列表
export function listLsCity(query) {
  return request({
    url: '/setting/LsCity/list',
    method: 'get',
    params: query
  })
}

// 查询区域市详细
export function getLsCity(id) {
  return request({
    url: '/setting/LsCity/' + id,
    method: 'get'
  })
}

// 新增区域市
export function addLsCity(data) {
  return request({
    url: '/setting/LsCity',
    method: 'post',
    data: data
  })
}

// 修改区域市
export function updateLsCity(data) {
  return request({
    url: '/setting/LsCity',
    method: 'put',
    data: data
  })
}

// 删除区域市
export function delLsCity(id) {
  return request({
    url: '/setting/LsCity/' + id,
    method: 'delete'
  })
}

// 导出区域市
export function exportLsCity(query) {
  return request({
    url: '/setting/LsCity/export',
    method: 'get',
    params: query
  })
}