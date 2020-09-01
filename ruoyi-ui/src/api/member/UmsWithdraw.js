import request from '@/utils/request'

// 查询提现记录列表
export function listUmsWithdraw(query) {
  return request({
    url: '/member/UmsWithdraw/list',
    method: 'get',
    params: query
  })
}

// 查询提现记录详细
export function getUmsWithdraw(id) {
  return request({
    url: '/member/UmsWithdraw/' + id,
    method: 'get'
  })
}

// 新增提现记录
export function addUmsWithdraw(data) {
  return request({
    url: '/member/UmsWithdraw',
    method: 'post',
    data: data
  })
}

// 修改提现记录
export function updateUmsWithdraw(data) {
  return request({
    url: '/member/UmsWithdraw',
    method: 'put',
    data: data
  })
}

// 删除提现记录
export function delUmsWithdraw(id) {
  return request({
    url: '/member/UmsWithdraw/' + id,
    method: 'delete'
  })
}

// 导出提现记录
export function exportUmsWithdraw(query) {
  return request({
    url: '/member/UmsWithdraw/export',
    method: 'get',
    params: query
  })
}