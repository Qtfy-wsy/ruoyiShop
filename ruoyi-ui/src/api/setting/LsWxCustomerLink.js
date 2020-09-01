import request from '@/utils/request'

// 查询微信登录和商城用户的关联列表
export function listLsWxCustomerLink(query) {
  return request({
    url: '/setting/LsWxCustomerLink/list',
    method: 'get',
    params: query
  })
}

// 查询微信登录和商城用户的关联详细
export function getLsWxCustomerLink(id) {
  return request({
    url: '/setting/LsWxCustomerLink/' + id,
    method: 'get'
  })
}

// 新增微信登录和商城用户的关联
export function addLsWxCustomerLink(data) {
  return request({
    url: '/setting/LsWxCustomerLink',
    method: 'post',
    data: data
  })
}

// 修改微信登录和商城用户的关联
export function updateLsWxCustomerLink(data) {
  return request({
    url: '/setting/LsWxCustomerLink',
    method: 'put',
    data: data
  })
}

// 删除微信登录和商城用户的关联
export function delLsWxCustomerLink(id) {
  return request({
    url: '/setting/LsWxCustomerLink/' + id,
    method: 'delete'
  })
}

// 导出微信登录和商城用户的关联
export function exportLsWxCustomerLink(query) {
  return request({
    url: '/setting/LsWxCustomerLink/export',
    method: 'get',
    params: query
  })
}