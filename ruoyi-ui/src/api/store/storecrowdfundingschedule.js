import request from '@/utils/request';

/**
 *  查询众筹进度
 * @param id
 */
export function queryStoreCrowdfundingProgressByMarketingId(id) {
  return request({
    url: 'store/crowdfunding/schedule/' + id,
    method: 'get'
  })
}
