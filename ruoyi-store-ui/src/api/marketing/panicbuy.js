import request from '@/utils/request';


/**
 * 查询限时抢购数据
 * @param params 查询参数
 */
export function queryPanicBuyMarketing(params) {
  return request({
    url: 'panicbuy',
    method: 'get',
    params: params
  })
}

/**
 *
 * 删除限时抢购
 * @param ids 删除的id集合
 */
export function deletePanicBuyByIds(ids) {
  return request({
    url: 'panicbuy',
    method: 'delete',
    params: ids
  })
}
