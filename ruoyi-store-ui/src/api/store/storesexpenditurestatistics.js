import request from '@/utils/request';


/**
 * 分页查询店铺销售额
 * @param query 查询参数
 */
export function queryStoreSaleAmout(query) {
  return request({
    url: 'storesales/saleamount',
    method: 'get',
    params: query
  })
}
