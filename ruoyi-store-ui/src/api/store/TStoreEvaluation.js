import request from '@/utils/request'

// 查询门店评价列表
export function listTStoreEvaluation(query) {
  return request({
    url: '/store/TStoreEvaluation/list',
    method: 'get',
    params: query
  })
}

// 查询门店评价详细
export function getTStoreEvaluation(id) {
  return request({
    url: '/store/TStoreEvaluation/' + id,
    method: 'get'
  })
}

// 新增门店评价
export function addTStoreEvaluation(data) {
  return request({
    url: '/store/TStoreEvaluation',
    method: 'post',
    data: data
  })
}

// 修改门店评价
export function updateTStoreEvaluation(data) {
  return request({
    url: '/store/TStoreEvaluation',
    method: 'put',
    data: data
  })
}

// 删除门店评价
export function delTStoreEvaluation(id) {
  return request({
    url: '/store/TStoreEvaluation/' + id,
    method: 'delete'
  })
}

// 导出门店评价
export function exportTStoreEvaluation(query) {
  return request({
    url: '/store/TStoreEvaluation/export',
    method: 'get',
    params: query
  })
}