import request from '@/utils/request';

/**
 *  查询众筹进度
 * @param id
 */
export function queryCrowdfundingProgressByMarketingId(id) {
  return request({
    url: 'crowdfunding/schedule/' + id,
    method: 'get'
  })
}

/**
 *新增众筹进度
 * @param schedlue
 */
export function addCrowdfundingProgress(schedlue) {
  return request({
    url: 'crowdfunding/schedule',
    method: 'post',
    data: schedlue
  })
}

/**
 * 删除众筹进度
 */
export function deleteCrowdfundingProgress(id, marketingId) {
  return request({
    url: 'crowdfunding/schedule/' + id + '/' + marketingId,
    method: 'delete'
  })
}

/**
 * 根据众筹进度id查询众筹进度
 */
export function queryCrowdfundingProgressById(id, marketingId) {
  return request({
    url: 'crowdfunding/schedule/' + id + '/' + marketingId,
    method: 'get'
  })
}

/**
 *修改众筹进度
 * @param schedlue
 */
export function updateCrowdfundingProgress(schedlue) {
  return request({
    url: 'crowdfunding/schedule',
    method: 'put',
    data: schedlue
  })
}
