/**
 * Created by 魔金商城 on 2019/5/17.
 */
import request from '@/utils/request';

/**
 * 分页查询品牌信息
 * @param 查询参数
 */
export function queryCategoryByParentId(parentId) {
  return request({
    url: '/goods/category/categorybyparentid/' + parentId,
    method: 'get'
  })
}


/**
 * 添加分类
 * @param category 添加分类
 */
export function addCategory(category) {
  return request({
    url: '/goods/category/category',
    method: 'post',
    data: category
  })
}

/**
 * 查询所有的类型
 */
export function queryAllTypes() {
  return request({
    url: '/goods/category/category/alltypes',
    method: 'get'
  })
}

/**
 * 查询所有的规格
 */
export function queryAllSpecs() {
  return request({
    url: '/goods/category/category/allspecs',
    method: 'get'
  })
}

/**
 * 删除分类
 * @param id 分类id
 */
export function deleteCategory(id) {
  return request({
    url: '/goods/category/category/' + id,
    method: 'delete'
  })
}

/**
 * 根据分类id查询分类信息
 * @param id 分类id
 */
export function querCategoryById(id) {
  return request({
    url: '/goods/category/category/' + id,
    method: 'get'
  })
}

/**
 * 更新分类
 * @param category 分类信息
 */
export function updateCategory(category) {
  return request({
    url: '/goods/category/category',
    method: 'put',
    data: category
  })
}

/**
 * 判断三级分类是否关联了商品
 * @param id 三级分类id
 */
export function queryThirdCategoryHasSpuById(id) {
  return request({
    url: '/goods/category/thirdcategoryhasspu/' + id,
    method: 'get'
  })
}
