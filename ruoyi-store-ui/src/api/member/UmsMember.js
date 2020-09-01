import request from '@/utils/request'

// 查询会员列表
export function listUmsMember(query) {
  return request({
    url: '/member/UmsMember/list',
    method: 'get',
    params: query
  })
}

// 查询会员详细
export function getUmsMember(id) {
  return request({
    url: '/member/UmsMember/' + id,
    method: 'get'
  })
}

// 新增会员
export function addUmsMember(data) {
  return request({
    url: '/member/UmsMember',
    method: 'post',
    data: data
  })
}

// 修改会员
export function updateUmsMember(data) {
  return request({
    url: '/member/UmsMember',
    method: 'put',
    data: data
  })
}

// 删除会员
export function delUmsMember(id) {
  return request({
    url: '/member/UmsMember/' + id,
    method: 'delete'
  })
}

// 导出会员
export function exportUmsMember(query) {
  return request({
    url: '/member/UmsMember/export',
    method: 'get',
    params: query
  })
}
/**
 * 检查手机号码是否存在
 * @param mobile 手机号码
 */
export function checkMobileExist(mobile) {
  return request({
    url: '/member/UmsMember/checkmobileexist/' + mobile,
    method: 'get',
  })
}

/**
 * 检查邮箱是否存在
 * @param email 邮箱
 */
export function checkEmailExist(email) {
  return request({
    url: '/member/UmsMember/checkemailexist/' + email,
    method: 'get',
  })
}
/**
 * 发送通知
 * @param letters 通知
 */
export function addstationLetters(letters) {
  return request({
    url: '/member/UmsMember/stationletters',
    method: 'post',
    data: letters
  })
}
