import request from '@/utils/request';

/**
 * 分页查询品牌信息
 * @param 查询参数
 */
export function queryGoodsCombination(query) {
  return request({
    url: 'goodscombination',
    method: 'get',
    params: query
  })
}

/**
 * 删除商品组合
 * @param ids 商品组合id
 */
export function deleteGoodsCombination(ids) {
  return request({
    url: 'goodscombination',
    method: 'delete',
    params: ids
  })
}

/**
 * 添加商品组合
 * @param combination 商品组合
 */
export function addGoodsCombination(combination) {
  return request({
    url: 'goodscombination',
    method: 'post',
    data: combination
  })
}

/**
 * 修改商品组合
 * @param combination 商品组合
 */
export function updateGoodsCombination(combination) {
  return request({
    url: 'goodscombination',
    method: 'put',
    data: combination
  })
}

/**
 * 根据id查询商品组合
 * @param id 商品组合id
 */
export function queryGoodsCombinationById(id) {
  return request({
    url: 'goodscombination/' + id,
    method: 'get',
  })
}
