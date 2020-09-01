import request from '@/utils/request'

// 查询首页推荐专题列表
export function listSmsHomeRecommendSubject(query) {
    return request({
        url: '/sms/SmsHomeRecommendSubject/list',
        method: 'get',
        params: query
    })
}

// 查询首页推荐专题详细
export function getSmsHomeRecommendSubject(id) {
    return request({
        url: '/sms/SmsHomeRecommendSubject/' + id,
        method: 'get'
    })
}

// 新增首页推荐专题
export function addSmsHomeRecommendSubject(data) {
    return request({
        url: '/sms/SmsHomeRecommendSubject',
        method: 'post',
        data: data
    })
}

// 修改首页推荐专题
export function updateSmsHomeRecommendSubject(data) {
    return request({
        url: '/sms/SmsHomeRecommendSubject',
        method: 'put',
        data: data
    })
}

// 删除首页推荐专题
export function delSmsHomeRecommendSubject(id) {
    return request({
        url: '/sms/SmsHomeRecommendSubject/' + id,
        method: 'delete'
    })
}

// 导出首页推荐专题
export function exportSmsHomeRecommendSubject(query) {
    return request({
        url: '/sms/SmsHomeRecommendSubject/export',
        method: 'get',
        params: query
    })
}
export function updateRecommendStatus(data) {
  return request({
    url:'/sms/SmsHomeRecommendSubject/update/recommendStatus',
    method:'get',
    params:data
  })
}
export function updateHomeSubjectSort(params) {
  return request({
    url:'/sms/SmsHomeRecommendSubject/update/sort/'+params.id+'/'+params.sort,
    method:'post',
    data:params
  })
}
