import request from '@/utils/request'

// 查询运费方式关联的区域列表
export function listArea(query) {
  return request({
    url: '/goods/area/list',
    method: 'get',
    params: query
  })
}

// 查询运费方式关联的区域详细
export function getArea(id) {
  return request({
    url: '/goods/area/' + id,
    method: 'get'
  })
}

// 新增运费方式关联的区域
export function addArea(data) {
  return request({
    url: '/goods/area',
    method: 'post',
    data: data
  })
}

// 修改运费方式关联的区域
export function updateArea(data) {
  return request({
    url: '/goods/area',
    method: 'put',
    data: data
  })
}

// 删除运费方式关联的区域
export function delArea(id) {
  return request({
    url: '/goods/area/' + id,
    method: 'delete'
  })
}

// 导出运费方式关联的区域
export function exportArea(query) {
  return request({
    url: '/goods/area/export',
    method: 'get',
    params: query
  })
}