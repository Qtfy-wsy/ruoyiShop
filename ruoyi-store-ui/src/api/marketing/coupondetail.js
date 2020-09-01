import request from '@/utils/request';

/**
 * 分页查询
 * @param id 优惠券的id
 */
export function queryCouponDetail(id) {
  return request({
    url: 'coupon/' + id,
    method: 'get',
  })
}
