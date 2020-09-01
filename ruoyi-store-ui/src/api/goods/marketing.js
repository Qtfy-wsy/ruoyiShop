import request from '@/utils/request'

// 查询单品和营销的关联列表
export function listMarketing(query) {
  return request({
    url: '/goods/marketing/list',
    method: 'get',
    params: query
  })
}

// 查询单品和营销的关联详细
export function getMarketing(id) {
  return request({
    url: '/goods/marketing/' + id,
    method: 'get'
  })
}

// 新增单品和营销的关联
export function addMarketing(data) {
  return request({
    url: '/goods/marketing',
    method: 'post',
    data: data
  })
}

// 修改单品和营销的关联
export function updateMarketing(data) {
  return request({
    url: '/goods/marketing',
    method: 'put',
    data: data
  })
}

// 删除单品和营销的关联
export function delMarketing(id) {
  return request({
    url: '/goods/marketing/' + id,
    method: 'delete'
  })
}

// 导出单品和营销的关联
export function exportMarketing(query) {
  return request({
    url: '/goods/marketing/export',
    method: 'get',
    params: query
  })
}