import request from '@/utils/request';


/**
 * 分页查询所有店铺的商品信息
 * @param query 查询参数
 */
export function queryAllStoreSpus(query) {
  return request({
    url: '/goods/goods/storespu',
    method: 'get',
    params: query
  })
}

/**
 * 根据分类id查询所有子分类信息
 * @param id 父类ID
 */
export function queryCategoryByParentId(id) {
  return request({
    url: 'storespu/category/' + id,
    method: 'get',
  })
}

/**
 * 违规下架
 * @param ids 选中的 id 集合
 */
export function updateShelvesStatusIllegal(ids) {
  return request({
    url: 'storespu',
    method: 'put',
    params: ids
  })
}


/**
 * 导出所有店铺商品信息
 */
export function exportStoreAllSpu() {
  return request({
    url: 'storespu/all',
    method: 'post',
    responseType: 'arraybuffer'
  })
}

/**
 * 导出选中的店铺商品信息
 * @param ids 选中的 id 集合
 */
export function exportStoreCheckedSpu(ids) {
  return request({
    url: 'storespu/checked',
    method: 'post',
    params: {ids: ids},
    responseType: 'arraybuffer'
  })
}
