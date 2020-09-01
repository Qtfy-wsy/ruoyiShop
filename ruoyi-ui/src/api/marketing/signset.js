import request from '@/utils/request';

/**
 * 查询签到规则
 */
export function queryqueryPointSignRule() {
  return request({
    url: 'pointsignrule',
    method: 'get',
  })
}


/**
 * 设置签到规则
 * @param signset 签到规则
 */
export function updatePointSignRule(signset) {
  return request({
    url: 'pointsignrule',
    method: 'put',
    data: signset
  })
}
