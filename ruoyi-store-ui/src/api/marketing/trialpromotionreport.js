import request from '@/utils/request';


/**
 * 查询试用报告
 * @param tryApplyId 试用申请id
 */
export function queryTryReport(tryApplyId) {
  return request({
    url: 'tryreport/' + tryApplyId,
    method: 'get',
  })
}

