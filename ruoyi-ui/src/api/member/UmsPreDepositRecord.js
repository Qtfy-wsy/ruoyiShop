import request from '@/utils/request'

// 查询会员预存款记录列表
export function listUmsPreDepositRecord(query) {
  return request({
    url: '/member/UmsPreDepositRecord/list',
    method: 'get',
    params: query
  })
}

// 查询会员预存款记录详细
export function getUmsPreDepositRecord(id) {
  return request({
    url: '/member/UmsPreDepositRecord/' + id,
    method: 'get'
  })
}

// 新增会员预存款记录
export function addUmsPreDepositRecord(data) {
  return request({
    url: '/member/UmsPreDepositRecord',
    method: 'post',
    data: data
  })
}

// 修改会员预存款记录
export function updateUmsPreDepositRecord(data) {
  return request({
    url: '/member/UmsPreDepositRecord',
    method: 'put',
    data: data
  })
}

// 删除会员预存款记录
export function delUmsPreDepositRecord(id) {
  return request({
    url: '/member/UmsPreDepositRecord/' + id,
    method: 'delete'
  })
}

// 导出会员预存款记录
export function exportUmsPreDepositRecord(query) {
  return request({
    url: '/member/UmsPreDepositRecord/export',
    method: 'get',
    params: query
  })
}