import request from '@/utils/request';

/**
 * 根据商品id查询商品信息
 * @param 商品id
 */
export function querySpuByIdForStore(id) {
  return request({
    url: 'store/spu/' + id,
    method: 'get',
  })
}

/**
 * 查询所有服务支持
 */
export function queryAllServiceSupportForstore() {
  return request({
    url: 'store/spu/servicesupport',
    method: 'get',
  })
}

