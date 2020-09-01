import request from '@/utils/request'

// 查询邮箱设置列表
export function listLsEmailSetting(query) {
  return request({
    url: '/setting/LsEmailSetting/list',
    method: 'get',
    params: query
  })
}

// 查询邮箱设置详细
export function getLsEmailSetting(id) {
  return request({
    url: '/setting/LsEmailSetting/' + id,
    method: 'get'
  })
}

// 新增邮箱设置
export function addLsEmailSetting(data) {
  return request({
    url: '/setting/LsEmailSetting',
    method: 'post',
    data: data
  })
}

// 修改邮箱设置
export function updateLsEmailSetting(data) {
  return request({
    url: '/setting/LsEmailSetting',
    method: 'put',
    data: data
  })
}

// 删除邮箱设置
export function delLsEmailSetting(id) {
  return request({
    url: '/setting/LsEmailSetting/' + id,
    method: 'delete'
  })
}

// 导出邮箱设置
export function exportLsEmailSetting(query) {
  return request({
    url: '/setting/LsEmailSetting/export',
    method: 'get',
    params: query
  })
}