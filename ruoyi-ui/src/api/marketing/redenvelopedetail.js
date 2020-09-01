import request from '@/utils/request';

/**
 * 查询红包详情
 * @param id 红包id
 */
export function queryRedEnvelopeDetails(id) {
  return request({
    url: 'redenvelope/' + id,
    method: 'get',
  })
}
