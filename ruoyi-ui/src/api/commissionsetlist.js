import request from '@/utils/request';


/**
 * 分页查询商品列表（佣金）
 * @param query 查询参数
 */
export function commissionSettingList(query) {
  return request({
    url: 'commission',
    method: 'get',
    params: query
  })
}

/**
 * 查询所有品牌信息
 */
export function queryBrand() {
  return request({
    url: 'commission/brand',
    method: 'get'
  })
}

/**
 * 根据分类id查询所有子分类信息
 * @param id 父类ID
 */
export function querycategorybyparentid(id) {
  return request({
    url: 'commission/category/' + id,
    method: 'get',
  })
}

/**
 * 设置佣金比例
 * @param params 参数
 */
export function updatecommission(params) {
  return request({
    url: 'commission',
    method: 'put',
    params: params
  })
}

/**
 * 批量设置佣金比例
 * @param params 参数
 */
export function updatecommissions(params) {
  return request({
    url: 'commission/batch',
    method: 'put',
    params: params
  })
}

/**
 * 按ID查询
 * @param params 参数
 */
export function queryspusforcommissionbyid(id) {
  return request({
    url: 'commission/spu/' + id,
    method: 'get',
  })
}
