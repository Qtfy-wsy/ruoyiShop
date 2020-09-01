import request from '@/utils/request'

// 查询单品起批价格标列表
export function listBatch(query) {
  return request({
    url: '/goods/batch/list',
    method: 'get',
    params: query
  })
}

// 查询单品起批价格标详细
export function getBatch(id) {
  return request({
    url: '/goods/batch/' + id,
    method: 'get'
  })
}

// 新增单品起批价格标
export function addBatch(data) {
  return request({
    url: '/goods/batch',
    method: 'post',
    data: data
  })
}

// 修改单品起批价格标
export function updateBatch(data) {
  return request({
    url: '/goods/batch',
    method: 'put',
    data: data
  })
}

// 删除单品起批价格标
export function delBatch(id) {
  return request({
    url: '/goods/batch/' + id,
    method: 'delete'
  })
}

// 导出单品起批价格标
export function exportBatch(query) {
  return request({
    url: '/goods/batch/export',
    method: 'get',
    params: query
  })
}