import request from '@/utils/request';

/**
 * 查询定金预售
 * @param params 查询参数
 */
export function queryDepositPresale(params) {
  return request({
    url: 'depositpresale',
    method: 'get',
    params: params
  })
}

/**
 * 查询全款预售
 * @param params 查询参数
 */
export function queryFullPresale(params) {
  return request({
    url: 'fullpresale',
    method: 'get',
    params: params
  })
}

/**
 * 批量删除定金预售促销
 * @param ids 促销id
 */
export function deleteDepositPresaleByIds(ids) {
  return request({
    url: 'depositpresale',
    method: 'delete',
    params: ids
  })
}

/**
 * 批量删除全款预售
 * @param ids 促销id
 */
export function deleteFullPresaleByIds(ids) {
  return request({
    url: 'fullpresale',
    method: 'delete',
    params: ids
  })
}
/**
 * 查询全款预售
 * @param params 查询参数
 */
export function queryPreSaleCates(params) {
  return request({
    url: 'presale/cates',
    method: 'get',
    params: params
  })
}
/**
 * 添加分类
 * @param marketing 定金预售
 */
export function addDepositPreSale(marketing) {
  return request({
    url: 'presale/cate',
    method: 'post',
    data: marketing
  })
}
export function updateDepositPreSale(marketing) {
  return request({
    url: 'presale/cate',
    method: 'put',
    data: marketing
  })
}
export function deletePreSaleCate(ids) {
  return request({
    url: 'presale/cates',
    method: 'delete',
    params: ids
  })
}
export function queryPreSaleCate(id) {
  return request({
    url: 'presale/cate/'+ id,
    method: 'get',
  })
}
