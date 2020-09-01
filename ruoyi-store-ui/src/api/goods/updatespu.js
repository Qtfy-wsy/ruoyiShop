import request from '@/utils/request';

/**
 * 根据商品id查询商品信息
 * @param parentId 商品id
 */
export function querySpuById(id) {
  return request({
    url: 'goods/goods/spu/' + id,
    method: 'get',
  })
}


/**
 * 查询所有品牌
 */
export function queryAllBrands() {
  return request({
    url: 'goods/goods/spu/update/brands',
    method: 'get',
  })
}

/**
 * 查询所有的物流模版
 */
export function queryAllLogisticsTemplates() {
  return request({
    url: 'goods/goods/spu/update/alllogisticstemplates',
    method: 'get',
  })
}

/**
 *  查询所有的会员等级
 */
export function queryAllLevels() {
  return request({
    url: 'goods/goods/spu/update/levels',
    method: 'get',
  })
}

/**
 * 查询所有服务支持
 */
export function queryAllServiceSupport() {
  return request({
    url: 'goods/goods/spu/update/servciesupports',
    method: 'get',
  })
}


/**
 *  查询类型详情
 * @param id 类型id
 */
export function queryTypeDetail(id) {
  return request({
    url: 'goods/goods/spu/update/type/' + id,
    method: 'get',
  })
}


/**
 * 更新商品信息
 * @param spu 商品信息
 */
export function updateSpu(spu) {
  return request({
    url: 'goods/goods/spu',
    method: 'put',
    data: spu
  })
}


