import request from '@/utils/request'

// 查询规格列表
export function listSpec(query) {
  return request({
    url: '/goods/spec/list',
    method: 'get',
    params: query
  })
}

// 查询规格详细
export function getSpec(id) {
  return request({
    url: '/goods/spec/' + id,
    method: 'get'
  })
}

// 新增规格
export function addSpec(data) {
  return request({
    url: '/goods/spec',
    method: 'post',
    data: data
  })
}

// 修改规格
export function updateSpec(data) {
  return request({
    url: '/goods/spec',
    method: 'put',
    data: data
  })
}

// 删除规格
export function delSpec(id) {
  return request({
    url: '/goods/spec/' + id,
    method: 'delete'
  })
}

// 导出规格
export function exportSpec(query) {
  return request({
    url: '/goods/spec/export',
    method: 'get',
    params: query
  })
}

/**
 * 查询规格值是否可以删除
 *
 * @param specValueId 规格值id
 * @return true 可以删除 false 不可以删除
 */
export function isSpecValueCanDelete(specValueId) {
  return request({
    url: '/goods/spec/isspecvaluecandelete/' + specValueId,
    method: 'get',
  })
}
