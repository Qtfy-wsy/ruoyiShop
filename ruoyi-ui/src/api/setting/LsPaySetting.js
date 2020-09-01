import request from '@/utils/request'

// 查询支付设置列表
export function listLsPaySetting(query) {
  return request({
    url: '/setting/LsPaySetting/list',
    method: 'get',
    params: query
  })
}

// 查询支付设置详细
export function getLsPaySetting(id) {
  return request({
    url: '/setting/LsPaySetting/' + id,
    method: 'get'
  })
}

// 新增支付设置
export function addLsPaySetting(data) {
  return request({
    url: '/setting/LsPaySetting',
    method: 'post',
    data: data
  })
}

// 修改支付设置
export function updateLsPaySetting(data) {
  return request({
    url: '/setting/LsPaySetting',
    method: 'put',
    data: data
  })
}

// 删除支付设置
export function delLsPaySetting(id) {
  return request({
    url: '/setting/LsPaySetting/' + id,
    method: 'delete'
  })
}

// 导出支付设置
export function exportLsPaySetting(query) {
  return request({
    url: '/setting/LsPaySetting/export',
    method: 'get',
    params: query
  })
}

/**
 * 查询支付设置
 */
export function queryPaySet() {
  return request({
    url: '/setting/LsPaySetting/payset',
    method: 'get',
  })
}

/**
 * 修改支付设置
 *
 * @param paySetCommon 支付设置信息
 * @param codeType 支付设置类型 1 支付宝 2 微信 3 银联
 */
export function updatePaySet(paySetCommon, codeType) {
  return request({
    url: '/setting/LsPaySetting/payset/' + codeType,
    method: 'post',
    data: paySetCommon
  })
}
