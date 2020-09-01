import request from '@/utils/request'

// 查询用户收货地址列表
export function listUmsMemberAddress(query) {
  return request({
    url: '/member/UmsMemberAddress/list',
    method: 'get',
    params: query
  })
}

// 查询用户收货地址详细
export function getUmsMemberAddress(id) {
  return request({
    url: '/member/UmsMemberAddress/' + id,
    method: 'get'
  })
}

// 新增用户收货地址
export function addUmsMemberAddress(data) {
  return request({
    url: '/member/UmsMemberAddress',
    method: 'post',
    data: data
  })
}

// 修改用户收货地址
export function updateUmsMemberAddress(data) {
  return request({
    url: '/member/UmsMemberAddress',
    method: 'put',
    data: data
  })
}

// 删除用户收货地址
export function delUmsMemberAddress(id) {
  return request({
    url: '/member/UmsMemberAddress/' + id,
    method: 'delete'
  })
}

// 导出用户收货地址
export function exportUmsMemberAddress(query) {
  return request({
    url: '/member/UmsMemberAddress/export',
    method: 'get',
    params: query
  })
}