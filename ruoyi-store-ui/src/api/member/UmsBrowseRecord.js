import request from '@/utils/request'

// 查询会员浏览记录列表
export function listUmsBrowseRecord(query) {
  return request({
    url: '/member/UmsBrowseRecord/list',
    method: 'get',
    params: query
  })
}

// 查询会员浏览记录详细
export function getUmsBrowseRecord(id) {
  return request({
    url: '/member/UmsBrowseRecord/' + id,
    method: 'get'
  })
}

// 新增会员浏览记录
export function addUmsBrowseRecord(data) {
  return request({
    url: '/member/UmsBrowseRecord',
    method: 'post',
    data: data
  })
}

// 修改会员浏览记录
export function updateUmsBrowseRecord(data) {
  return request({
    url: '/member/UmsBrowseRecord',
    method: 'put',
    data: data
  })
}

// 删除会员浏览记录
export function delUmsBrowseRecord(id) {
  return request({
    url: '/member/UmsBrowseRecord/' + id,
    method: 'delete'
  })
}

// 导出会员浏览记录
export function exportUmsBrowseRecord(query) {
  return request({
    url: '/member/UmsBrowseRecord/export',
    method: 'get',
    params: query
  })
}