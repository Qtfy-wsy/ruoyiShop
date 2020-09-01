import request from '@/utils/request'

// 查询店铺自定义品牌列列表
export function listTStoreCustomizeBrand(query) {
  return request({
    url: '/store/TStoreCustomizeBrand/list',
    method: 'get',
    params: query
  })
}

// 查询店铺自定义品牌列详细
export function getTStoreCustomizeBrand(id) {
  return request({
    url: '/store/TStoreCustomizeBrand/' + id,
    method: 'get'
  })
}

// 新增店铺自定义品牌列
export function addTStoreCustomizeBrand(data) {
  return request({
    url: '/store/TStoreCustomizeBrand',
    method: 'post',
    data: data
  })
}

// 修改店铺自定义品牌列
export function updateTStoreCustomizeBrand(data) {
  return request({
    url: '/store/TStoreCustomizeBrand',
    method: 'put',
    data: data
  })
}

// 删除店铺自定义品牌列
export function delTStoreCustomizeBrand(id) {
  return request({
    url: '/store/TStoreCustomizeBrand/' + id,
    method: 'delete'
  })
}

// 导出店铺自定义品牌列
export function exportTStoreCustomizeBrand(query) {
  return request({
    url: '/store/TStoreCustomizeBrand/export',
    method: 'get',
    params: query
  })
}