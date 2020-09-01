import request from '@/utils/request'

// 查询短信接口设置列表
export function listLsSmsSetting(query) {
  return request({
    url: '/setting/LsSmsSetting/list',
    method: 'get',
    params: query
  })
}

// 查询短信接口设置详细
export function getLsSmsSetting(id) {
  return request({
    url: '/setting/LsSmsSetting/' + id,
    method: 'get'
  })
}

// 新增短信接口设置
export function addLsSmsSetting(data) {
  return request({
    url: '/setting/LsSmsSetting',
    method: 'post',
    data: data
  })
}

// 修改短信接口设置
export function updateLsSmsSetting(data) {
  return request({
    url: '/setting/LsSmsSetting',
    method: 'put',
    data: data
  })
}

// 删除短信接口设置
export function delLsSmsSetting(id) {
  return request({
    url: '/setting/LsSmsSetting/' + id,
    method: 'delete'
  })
}

// 导出短信接口设置
export function exportLsSmsSetting(query) {
  return request({
    url: '/setting/LsSmsSetting/export',
    method: 'get',
    params: query
  })
}