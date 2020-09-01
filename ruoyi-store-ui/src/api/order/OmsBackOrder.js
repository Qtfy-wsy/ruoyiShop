import request from '@/utils/request'

// 查询退单退款列表
export function listOmsBackOrder(query) {
  return request({
    url: '/order/OmsBackOrder/list',
    method: 'get',
    params: query
  })
}

// 查询退单退款详细
export function getOmsBackOrder(id) {
  return request({
    url: '/order/OmsBackOrder/' + id,
    method: 'get'
  })
}

// 新增退单退款
export function addOmsBackOrder(data) {
  return request({
    url: '/order/OmsBackOrder',
    method: 'post',
    data: data
  })
}

// 修改退单退款
export function updateOmsBackOrder(data) {
  return request({
    url: '/order/OmsBackOrder',
    method: 'put',
    data: data
  })
}

// 删除退单退款
export function delOmsBackOrder(id) {
  return request({
    url: '/order/OmsBackOrder/' + id,
    method: 'delete'
  })
}

// 导出退单退款
export function exportOmsBackOrder(query) {
  return request({
    url: '/order/OmsBackOrder/export',
    method: 'get',
    params: query
  })
}