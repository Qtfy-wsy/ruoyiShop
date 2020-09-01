import request from '@/utils/request'

// 查询商品组合列表
export function listCombination(query) {
  return request({
    url: '/goods/combination/list',
    method: 'get',
    params: query
  })
}

// 查询商品组合详细
export function getCombination(id) {
  return request({
    url: '/goods/combination/' + id,
    method: 'get'
  })
}

// 新增商品组合
export function addCombination(data) {
  return request({
    url: '/goods/combination',
    method: 'post',
    data: data
  })
}

// 修改商品组合
export function updateCombination(data) {
  return request({
    url: '/goods/combination',
    method: 'put',
    data: data
  })
}

// 删除商品组合
export function delCombination(id) {
  return request({
    url: '/goods/combination/' + id,
    method: 'delete'
  })
}

// 导出商品组合
export function exportCombination(query) {
  return request({
    url: '/goods/combination/export',
    method: 'get',
    params: query
  })
}