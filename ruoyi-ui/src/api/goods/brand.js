import request from '@/utils/request'

// 查询品牌列表
export function listBrand(query) {
  return request({
    url: '/goods/brand/list',
    method: 'get',
    params: query
  })
}

// 查询品牌详细
export function getBrand(id) {
  return request({
    url: '/goods/brand/' + id,
    method: 'get'
  })
}

// 新增品牌
export function addBrand(data) {
  return request({
    url: '/goods/brand',
    method: 'post',
    data: data
  })
}

// 修改品牌
export function updateBrand(data) {
  return request({
    url: '/goods/brand',
    method: 'put',
    data: data
  })
}

// 删除品牌
export function delBrand(id) {
  return request({
    url: '/goods/brand/' + id,
    method: 'delete'
  })
}

// 导出品牌
export function exportBrand(query) {
  return request({
    url: '/goods/brand/export',
    method: 'get',
    params: query
  })
}