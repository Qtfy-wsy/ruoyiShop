import request from '@/utils/request';


/**
 * 查询所有省份信息
 */
export function queryAllProvinces() {
  return request({
    url: '/addshop/provinces',
    method: 'get',
  })
}

/**
 * 根据省份id查询市
 */
export function queryCityByProvinceId(parentId) {
  return request({
    url: '/addshop/city' + parentId,
    method: 'get',
  })
}

/**
 * 查询所有省份信息
 */
export function queryDistrictByCityId(parentId) {
  return request({
    url: '/addshop/district/' + parentId,
    method: 'get',
  })
}


/**
 * 校验店铺名是否存在
 */
export function checkStoreNameExist(storeName) {
  return request({
    url: '/addshop/storename',
    method: 'get',
    params: {storeName: storeName}
  })
}

/**
 * 校验公司名是否存在
 */
export function checkCompanyNameExist(companyName) {
  return request({
    url: '/addshop/companyname',
    method: 'get',
    params: {companyName: companyName}
  })
}


/**
 * 新增店铺
 */
export function fillAllStoreInfo(storeBusiness) {
  return request({
    url: '/addshop',
    method: 'post',
    data: storeBusiness
  })
}


