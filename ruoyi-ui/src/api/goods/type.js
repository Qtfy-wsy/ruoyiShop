import request from '@/utils/request'

// 查询商品类型列表
export function listType(query) {
  return request({
    url: '/goods/type/list',
    method: 'get',
    params: query
  })
}

// 查询商品类型详细
export function getType(id) {
  return request({
    url: '/goods/type/' + id,
    method: 'get'
  })
}

// 新增商品类型
export function addType(data) {
  return request({
    url: '/goods/type',
    method: 'post',
    data: data
  })
}

// 修改商品类型
export function updateType(data) {
  return request({
    url: '/goods/type',
    method: 'put',
    data: data
  })
}

// 删除商品类型
export function delType(id) {
  return request({
    url: '/goods/type/' + id,
    method: 'delete'
  })
}

// 导出商品类型
export function exportType(query) {
  return request({
    url: '/goods/type/export',
    method: 'get',
    params: query
  })
}

/**
 * 检查类型是否关联了商品  queryThirdCategory
 * @param id 类型id
 */
export function checkTypeAssociated(id) {
  return request({
    url: '/goods/type/checktypeassociated/' + id,
    method: 'get',
  })
}
  /**
   *   判断类型是否可以删除，如果类型被三级分类关联 则不能删除
   * @param id 类型id
   */
  export function queryThirdCategory() {
    return request({
      url: '/goods/type/thirdcategorys/',
      method: 'get',
    })
}
