import request from '@/utils/request'

// 查询区域省列表
export function listLsProvince(query) {
  return request({
    url: '/setting/LsProvince/list',
    method: 'get',
    params: query
  })
}

// 查询区域省详细
export function getLsProvince(id) {
  return request({
    url: '/setting/LsProvince/' + id,
    method: 'get'
  })
}

// 新增区域省
export function addLsProvince(data) {
  return request({
    url: '/setting/LsProvince',
    method: 'post',
    data: data
  })
}

// 修改区域省
export function updateLsProvince(data) {
  return request({
    url: '/setting/LsProvince',
    method: 'put',
    data: data
  })
}

// 删除区域省
export function delLsProvince(id) {
  return request({
    url: '/setting/LsProvince/' + id,
    method: 'delete'
  })
}

// 导出区域省
export function exportLsProvince(query) {
  return request({
    url: '/setting/LsProvince/export',
    method: 'get',
    params: query
  })
}