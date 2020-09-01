import request from '@/utils/request'

// 查询运费模版包邮列表
export function listFreeship(query) {
  return request({
    url: '/goods/freeship/list',
    method: 'get',
    params: query
  })
}

// 查询运费模版包邮详细
export function getFreeship(id) {
  return request({
    url: '/goods/freeship/' + id,
    method: 'get'
  })
}

// 新增运费模版包邮
export function addFreeship(data) {
  return request({
    url: '/goods/freeship',
    method: 'post',
    data: data
  })
}

// 修改运费模版包邮
export function updateFreeship(data) {
  return request({
    url: '/goods/freeship',
    method: 'put',
    data: data
  })
}

// 删除运费模版包邮
export function delFreeship(id) {
  return request({
    url: '/goods/freeship/' + id,
    method: 'delete'
  })
}

// 导出运费模版包邮
export function exportFreeship(query) {
  return request({
    url: '/goods/freeship/export',
    method: 'get',
    params: query
  })
}