import request from '@/utils/request'

// 查询首页推荐品牌列表
export function listSmsHomeBrand(query) {
    return request({
        url: '/sms/SmsHomeBrand/list',
        method: 'get',
        params: query
    })
}

// 查询首页推荐品牌详细
export function getSmsHomeBrand(id) {
    return request({
        url: '/sms/SmsHomeBrand/' + id,
        method: 'get'
    })
}

// 新增首页推荐品牌
export function addSmsHomeBrand(data) {
    return request({
        url: '/sms/SmsHomeBrand',
        method: 'post',
        data: data
    })
}

// 修改首页推荐品牌
export function updateSmsHomeBrand(data) {
    return request({
        url: '/sms/SmsHomeBrand',
        method: 'put',
        data: data
    })
}

// 删除首页推荐品牌
export function delSmsHomeBrand(id) {
    return request({
        url: '/sms/SmsHomeBrand/' + id,
        method: 'delete'
    })
}

// 导出首页推荐品牌
export function exportSmsHomeBrand(query) {
    return request({
        url: '/sms/SmsHomeBrand/export',
        method: 'get',
        params: query
    })
}
export function updateRecommendStatus(data) {
  return request({
    url:'/sms/SmsHomeBrand/update/recommendStatus',
    method:'get',
    params:data
  })
}
export function updateHomeBrandSort(params) {
  return request({
    url:'/sms/SmsHomeBrand/update/sort/'+params.id+'/'+params.sort,
    method:'post',
    data:params
  })
}
