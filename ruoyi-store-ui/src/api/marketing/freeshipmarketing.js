import request from '@/utils/request';

/**
 * 查询包邮设置
 */
export function queryFreeShip() {
  return request({
    url: 'freeship',
    method: 'get',
  })
}


/**
 * 新增包邮促销
 * @param freeship 包邮促销
 */
export function addFreeShip(freeship) {
  return request({
    url: 'freeship',
    data: freeship,
    method: 'post',
  })
}
