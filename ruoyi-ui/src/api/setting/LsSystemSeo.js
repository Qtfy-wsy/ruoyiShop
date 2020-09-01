import request from '@/utils/request'

// 查询系统seo设置列表
export function listLsSystemSeo(query) {
  return request({
    url: '/setting/LsSystemSeo/list',
    method: 'get',
    params: query
  })
}

// 查询系统seo设置详细
export function getLsSystemSeo(id) {
  return request({
    url: '/setting/LsSystemSeo/' + id,
    method: 'get'
  })
}

// 新增系统seo设置
export function addLsSystemSeo(data) {
  return request({
    url: '/setting/LsSystemSeo',
    method: 'post',
    data: data
  })
}

// 修改系统seo设置
export function updateLsSystemSeo(data) {
  return request({
    url: '/setting/LsSystemSeo',
    method: 'put',
    data: data
  })
}

// 删除系统seo设置
export function delLsSystemSeo(id) {
  return request({
    url: '/setting/LsSystemSeo/' + id,
    method: 'delete'
  })
}

// 导出系统seo设置
export function exportLsSystemSeo(query) {
  return request({
    url: '/setting/LsSystemSeo/export',
    method: 'get',
    params: query
  })
}