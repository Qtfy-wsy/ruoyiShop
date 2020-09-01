import request from '@/utils/request'

// 查询运费方式列表
export function listMethod(query) {
  return request({
    url: '/goods/method/list',
    method: 'get',
    params: query
  })
}

// 查询运费方式详细
export function getMethod(id) {
  return request({
    url: '/goods/method/' + id,
    method: 'get'
  })
}

// 新增运费方式
export function addMethod(data) {
  return request({
    url: '/goods/method',
    method: 'post',
    data: data
  })
}

// 修改运费方式
export function updateMethod(data) {
  return request({
    url: '/goods/method',
    method: 'put',
    data: data
  })
}

// 删除运费方式
export function delMethod(id) {
  return request({
    url: '/goods/method/' + id,
    method: 'delete'
  })
}

// 导出运费方式
export function exportMethod(query) {
  return request({
    url: '/goods/method/export',
    method: 'get',
    params: query
  })
}