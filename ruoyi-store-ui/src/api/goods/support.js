import request from '@/utils/request'

// 查询商品和服务支持的关联列表
export function listSupport(query) {
  return request({
    url: '/goods/support/list',
    method: 'get',
    params: query
  })
}

// 查询商品和服务支持的关联详细
export function getSupport(id) {
  return request({
    url: '/goods/support/' + id,
    method: 'get'
  })
}

// 新增商品和服务支持的关联
export function addSupport(data) {
  return request({
    url: '/goods/support',
    method: 'post',
    data: data
  })
}

// 修改商品和服务支持的关联
export function updateSupport(data) {
  return request({
    url: '/goods/support',
    method: 'put',
    data: data
  })
}

// 删除商品和服务支持的关联
export function delSupport(id) {
  return request({
    url: '/goods/support/' + id,
    method: 'delete'
  })
}

// 导出商品和服务支持的关联
export function exportSupport(query) {
  return request({
    url: '/goods/support/export',
    method: 'get',
    params: query
  })
}