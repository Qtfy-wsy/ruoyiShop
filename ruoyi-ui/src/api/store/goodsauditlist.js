import request from '@/utils/request';

/**
 * 分页查询所有店铺待审核的商品
 * @param query 查询参数
 */
export function queryAllStoreToBeAuditdSpus(query) {
  return request({
    url: '/goods/goods/goodsaudit',
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
    url: 'goodsaudit/category/' + id,
    method: 'get',
  })
}
