import request from '@/utils/request'

// 查询单品和图片的关联列表
export function listImage(query) {
  return request({
    url: '/goods/image/list',
    method: 'get',
    params: query
  })
}

// 查询单品和图片的关联详细
export function getImage(id) {
  return request({
    url: '/goods/image/' + id,
    method: 'get'
  })
}

// 新增单品和图片的关联
export function addImage(data) {
  return request({
    url: '/goods/image',
    method: 'post',
    data: data
  })
}

// 修改单品和图片的关联
export function updateImage(data) {
  return request({
    url: '/goods/image',
    method: 'put',
    data: data
  })
}

// 删除单品和图片的关联
export function delImage(id) {
  return request({
    url: '/goods/image/' + id,
    method: 'delete'
  })
}

// 导出单品和图片的关联
export function exportImage(query) {
  return request({
    url: '/goods/image/export',
    method: 'get',
    params: query
  })
}