import request from '@/utils/request'

// 查询门店红包卷吗列表
export function listTStoreRedEnvelopeCode(query) {
  return request({
    url: '/store/TStoreRedEnvelopeCode/list',
    method: 'get',
    params: query
  })
}

// 查询门店红包卷吗详细
export function getTStoreRedEnvelopeCode(id) {
  return request({
    url: '/store/TStoreRedEnvelopeCode/' + id,
    method: 'get'
  })
}

// 新增门店红包卷吗
export function addTStoreRedEnvelopeCode(data) {
  return request({
    url: '/store/TStoreRedEnvelopeCode',
    method: 'post',
    data: data
  })
}

// 修改门店红包卷吗
export function updateTStoreRedEnvelopeCode(data) {
  return request({
    url: '/store/TStoreRedEnvelopeCode',
    method: 'put',
    data: data
  })
}

// 删除门店红包卷吗
export function delTStoreRedEnvelopeCode(id) {
  return request({
    url: '/store/TStoreRedEnvelopeCode/' + id,
    method: 'delete'
  })
}

// 导出门店红包卷吗
export function exportTStoreRedEnvelopeCode(query) {
  return request({
    url: '/store/TStoreRedEnvelopeCode/export',
    method: 'get',
    params: query
  })
}