import request from '@/utils/request';

/**
 * 查询注册促销
 */
export function queryRegisterMarketing() {
  return request({
    url: '/registermarketing',
    method: 'get',
  })
}

/**
 * 修改注册促销
 * @param registerMarketing 注册促销
 */
export function updateRegisterMarketing(registerMarketing) {
  return request({
    url: '/registermarketing',
    data: registerMarketing,
    method: 'put',
  })
}
