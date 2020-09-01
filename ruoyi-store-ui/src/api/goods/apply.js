import request from '@/utils/request'

// 查询品牌申请列表
export function listApply(query) {
  return request({
    url: '/goods/apply/list',
    method: 'get',
    params: query
  })
}

// 查询品牌申请详细
export function getApply(id) {
  return request({
    url: '/goods/apply/' + id,
    method: 'get'
  })
}

// 新增品牌申请
export function addApply(data) {
  return request({
    url: '/goods/apply',
    method: 'post',
    data: data
  })
}

// 修改品牌申请
export function updateApply(data) {
  return request({
    url: '/goods/apply',
    method: 'put',
    data: data
  })
}

// 删除品牌申请
export function delApply(id) {
  return request({
    url: '/goods/apply/' + id,
    method: 'delete'
  })
}

// 导出品牌申请
export function exportApply(query) {
  return request({
    url: '/goods/apply/export',
    method: 'get',
    params: query
  })
}