import request from '@/utils/request'

// 查询社区团购设置列表
export function listSCommunityBuySetting(query) {
  return request({
    url: '/setting/SCommunityBuySetting/list',
    method: 'get',
    params: query
  })
}

// 查询社区团购设置详细
export function getSCommunityBuySetting(id) {
  return request({
    url: '/setting/SCommunityBuySetting/' + id,
    method: 'get'
  })
}

// 新增社区团购设置
export function addSCommunityBuySetting(data) {
  return request({
    url: '/setting/SCommunityBuySetting',
    method: 'post',
    data: data
  })
}

// 修改社区团购设置
export function updateSCommunityBuySetting(data) {
  return request({
    url: '/setting/SCommunityBuySetting',
    method: 'put',
    data: data
  })
}

// 删除社区团购设置
export function delSCommunityBuySetting(id) {
  return request({
    url: '/setting/SCommunityBuySetting/' + id,
    method: 'delete'
  })
}

// 导出社区团购设置
export function exportSCommunityBuySetting(query) {
  return request({
    url: '/setting/SCommunityBuySetting/export',
    method: 'get',
    params: query
  })
}