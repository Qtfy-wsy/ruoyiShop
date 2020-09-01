import request from '@/utils/request';

/**
 * 分页查询众筹信息
 */
export function queryCrowdFundingMarketList(params) {
  return request({
    url: 'crowdfunding',
    method: 'get',
    params: params
  })
}


/**
 * 删除众筹促销
 * @param id 众筹促销id
 */
export function deleteCrowdFundingById(id) {
  return request({
    url: 'crowdfunding/' + id,
    method: 'delete',
  })
}
