import request from '@/utils/request';


/**
 * 分页查询提现记录
 * @param 查询参数
 */
export function withdrawRecordsList(query) {
  return request({
    url: 'communitywithdraw',
    method: 'get',
    params: query
  })
}


/**
 * 审核通过
 * @param id 提现申请id
 */
export function passApply(id) {
  return request({
    url: 'communitywithdraw/pass/' + id,
    method: 'put',
  })
}

/**
 * 拒绝(驳回)提现申请
 * @param id 提现申请id
 */
export function refuseApply(id) {
  return request({
    url: 'communitywithdraw/refuse/' + id,
    method: 'put',
  })
}

/**
 * 发放金额
 * @param id 提现申请id
 */
export function releaseMoney(id) {
  return request({
    url: 'communitywithdraw/release/' + id,
    method: 'put',
  })
}
