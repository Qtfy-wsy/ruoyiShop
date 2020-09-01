import request from '@/utils/request'

// 查询会员等级列表
export function listUmsMemberLevel(query) {
  return request({
    url: '/member/UmsMemberLevel/list',
    method: 'get',
    params: query
  })
}

// 查询会员等级详细
export function getUmsMemberLevel(id) {
  return request({
    url: '/member/UmsMemberLevel/' + id,
    method: 'get'
  })
}

// 新增会员等级
export function addUmsMemberLevel(data) {
  return request({
    url: '/member/UmsMemberLevel',
    method: 'post',
    data: data
  })
}

// 修改会员等级
export function updateUmsMemberLevel(data) {
  return request({
    url: '/member/UmsMemberLevel',
    method: 'put',
    data: data
  })
}

// 删除会员等级
export function delUmsMemberLevel(id) {
  return request({
    url: '/member/UmsMemberLevel/' + id,
    method: 'delete'
  })
}

// 导出会员等级
export function exportUmsMemberLevel(query) {
  return request({
    url: '/member/UmsMemberLevel/export',
    method: 'get',
    params: query
  })
}