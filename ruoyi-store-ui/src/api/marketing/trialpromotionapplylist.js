import request from '@/utils/request';


/**
 * 分页查询试用申请列表
 * @param query 查询参数
 */
export function queryTryApplyList(query) {
  return request({
    url: 'tryapply',
    method: 'get',
    params: query
  })
}

