import request from '@/utils/request'

// 查询首页轮播广告列表
export function listSmsHomeAdvertise(query) {
    return request({
        url: '/sms/SmsHomeAdvertise/list',
        method: 'get',
        params: query
    })
}

// 查询首页轮播广告详细
export function getSmsHomeAdvertise(id) {
    return request({
        url: '/sms/SmsHomeAdvertise/' + id,
        method: 'get'
    })
}

// 新增首页轮播广告
export function addSmsHomeAdvertise(data) {
    return request({
        url: '/sms/SmsHomeAdvertise',
        method: 'post',
        data: data
    })
}

// 修改首页轮播广告
export function updateSmsHomeAdvertise(data) {
    return request({
        url: '/sms/SmsHomeAdvertise',
        method: 'put',
        data: data
    })
}

// 删除首页轮播广告
export function delSmsHomeAdvertise(id) {
    return request({
        url: '/sms/SmsHomeAdvertise/' + id,
        method: 'delete'
    })
}

// 导出首页轮播广告
export function exportSmsHomeAdvertise(query) {
    return request({
        url: '/sms/SmsHomeAdvertise/export',
        method: 'get',
        params: query
    })
}