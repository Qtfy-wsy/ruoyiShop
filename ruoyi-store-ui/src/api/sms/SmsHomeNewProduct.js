import request from '@/utils/request'

// 查询新鲜好物列表
export function listSmsHomeNewProduct(query) {
    return request({
        url: '/sms/SmsHomeNewProduct/list',
        method: 'get',
        params: query
    })
}

// 查询新鲜好物详细
export function getSmsHomeNewProduct(id) {
    return request({
        url: '/sms/SmsHomeNewProduct/' + id,
        method: 'get'
    })
}

// 新增新鲜好物
export function addSmsHomeNewProduct(data) {
    return request({
        url: '/sms/SmsHomeNewProduct',
        method: 'post',
        data: data
    })
}

// 修改新鲜好物
export function updateSmsHomeNewProduct(data) {
    return request({
        url: '/sms/SmsHomeNewProduct',
        method: 'put',
        data: data
    })
}

// 删除新鲜好物
export function delSmsHomeNewProduct(id) {
    return request({
        url: '/sms/SmsHomeNewProduct/' + id,
        method: 'delete'
    })
}

// 导出新鲜好物
export function exportSmsHomeNewProduct(query) {
    return request({
        url: '/sms/SmsHomeNewProduct/export',
        method: 'get',
        params: query
    })
}
export function updateRecommendStatus(data) {
  return request({
    url:'/sms/SmsHomeNewProduct/update/recommendStatus',
    method:'get',
    params:data
  })
}
export function updateNewProductSort(params) {
  return request({
    url:'/sms/SmsHomeNewProduct/update/sort/'+params.id+'/'+params.sort,
    method:'post',
    data:params
  })
}
