import request from '@/utils/request';

/**
 * 分页查询店铺众筹信息
 */
export function queryStoreCrowdFundingMarketList(params) {
  return request({
    url: 'store/crowdfunding',
    method: 'get',
    params: params
  })
}
