import request from '@/utils/request'

// 查询人气推荐商品列表
export function listSmsHomeRecommendProduct(query) {
    return request({
        url: '/sms/SmsHomeRecommendProduct/list',
        method: 'get',
        params: query
    })
}

// 查询人气推荐商品详细
export function getSmsHomeRecommendProduct(id) {
    return request({
        url: '/sms/SmsHomeRecommendProduct/' + id,
        method: 'get'
    })
}

// 新增人气推荐商品
export function addSmsHomeRecommendProduct(data) {
    return request({
        url: '/sms/SmsHomeRecommendProduct',
        method: 'post',
        data: data
    })
}

// 修改人气推荐商品
export function updateSmsHomeRecommendProduct(data) {
    return request({
        url: '/sms/SmsHomeRecommendProduct',
        method: 'put',
        data: data
    })
}

// 删除人气推荐商品
export function delSmsHomeRecommendProduct(id) {
    return request({
        url: '/sms/SmsHomeRecommendProduct/' + id,
        method: 'delete'
    })
}

// 导出人气推荐商品
export function exportSmsHomeRecommendProduct(query) {
    return request({
        url: '/sms/SmsHomeRecommendProduct/export',
        method: 'get',
        params: query
    })
}
export function updateRecommendStatus(data) {
  return request({
    url:'/sms/SmsHomeRecommendProduct/update/recommendStatus',
    method:'get',
    params:data
  })
}
export function updateHotProductSort(params) {
  return request({
    url:'/sms/SmsHomeRecommendProduct/update/sort/'+params.id+'/'+params.sort,
    method:'post',
    data:params
  })
}
