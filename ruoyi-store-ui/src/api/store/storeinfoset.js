import request from '@/utils/request';


/**
 * 根据店铺id查询店铺详情信息
 * @param query 查询参数
 */
export function storeDetailInfoByStoreId(query) {
  return request({
    url: 'storeinfoset',
    method: 'get',
    params: query
  })
}


/**
 * 根据父级id查询所有子分类
 * @param id 父类 ID
 */
export function queryCategoryByParentId(id) {
  return request({
    url: 'storeinfoset/category/' + id,
    method: 'get',
  })
}


/**
 * 添加签约分类
 * @param param 添加参数
 */
export function addStoreSignedCategory(param) {
  return request({
    url: 'storeinfoset',
    method: 'post',
    params: param
  })
}


/**
 * 根据店铺id和分类id删除签约分类
 * @param param 删除参数
 */
export function deleteStoreSingedCategoryById(param) {
  return request({
    url: 'storeinfoset',
    method: 'delete',
    params: param
  })
}

/**
 * 编辑店铺有效期,结算周期,是否关店
 * @param param 参数
 */
export function editStoreTimeAndIsClose(storeInfo) {
  return request({
    url: 'storeinfoset',
    method: 'put',
    data: storeInfo
  })
}

