import request from '@/utils/request'

// 查询会员积分详情列表
export function listUmsMemberPoint(query) {
  return request({
    url: '/member/UmsMemberPoint/list',
    method: 'get',
    params: query
  })
}

// 查询会员积分详情详细
export function getUmsMemberPoint(id) {
  return request({
    url: '/member/UmsMemberPoint/' + id,
    method: 'get'
  })
}

// 新增会员积分详情
export function addUmsMemberPoint(data) {
  return request({
    url: '/member/UmsMemberPoint',
    method: 'post',
    data: data
  })
}

// 修改会员积分详情
export function updateUmsMemberPoint(data) {
  return request({
    url: '/member/UmsMemberPoint',
    method: 'put',
    data: data
  })
}

// 删除会员积分详情
export function delUmsMemberPoint(id) {
  return request({
    url: '/member/UmsMemberPoint/' + id,
    method: 'delete'
  })
}

// 导出会员积分详情
export function exportUmsMemberPoint(query) {
  return request({
    url: '/member/UmsMemberPoint/export',
    method: 'get',
    params: query
  })
}