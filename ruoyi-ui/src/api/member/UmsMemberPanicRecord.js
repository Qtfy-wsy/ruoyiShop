import request from '@/utils/request'

// 查询用户抢购记录列表
export function listUmsMemberPanicRecord(query) {
  return request({
    url: '/member/UmsMemberPanicRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户抢购记录详细
export function getUmsMemberPanicRecord(id) {
  return request({
    url: '/member/UmsMemberPanicRecord/' + id,
    method: 'get'
  })
}

// 新增用户抢购记录
export function addUmsMemberPanicRecord(data) {
  return request({
    url: '/member/UmsMemberPanicRecord',
    method: 'post',
    data: data
  })
}

// 修改用户抢购记录
export function updateUmsMemberPanicRecord(data) {
  return request({
    url: '/member/UmsMemberPanicRecord',
    method: 'put',
    data: data
  })
}

// 删除用户抢购记录
export function delUmsMemberPanicRecord(id) {
  return request({
    url: '/member/UmsMemberPanicRecord/' + id,
    method: 'delete'
  })
}

// 导出用户抢购记录
export function exportUmsMemberPanicRecord(query) {
  return request({
    url: '/member/UmsMemberPanicRecord/export',
    method: 'get',
    params: query
  })
}