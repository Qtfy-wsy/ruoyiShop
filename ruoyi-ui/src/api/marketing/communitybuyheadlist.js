/**
 * 社区团购团长路由
 *
 * @author 魔金商城 created on 2019/8/16
 */

import request from '@/utils/request';


/**
 * 分页查询社区团购团长列表
 *
 * @param query 查询参数
 */
export function queryCommunityBuyHeadList(query) {
  return request({
    url: 'communitybuyheadlist',
    method: 'get',
    params: query
  })
}

/**
 * 根据id查询团长信息
 *
 * @param headId 团长id
 */
export function queryCommunityBuyHeadById(headId) {
  return request({
    url: 'communitybuyhead/' + headId,
    method: 'get',
  })
}

/**
 * 查询所有省份信息
 */
export function queryAllProvinces() {
  return request({
    url: 'communitybuyhead/province',
    method: 'get',
  })
}

/**
 * 根据省份id查询城市信息
 *
 * @param provinceId 省份id
 */
export function queryCityByProvinceId(provinceId) {
  return request({
    url: 'communitybuyhead/city/' + provinceId,
    method: 'get',
  })
}

/**
 * 根据城市id查询区信息
 *
 * @param cityId 城市id
 */
export function queryDistrictByCityId(cityId) {
  return request({
    url: 'communitybuyhead/district/' + cityId,
    method: 'get',
  })
}

/**
 * 编辑团长信息
 *
 * @param communityBuyHead 团长信息
 */
export function updateCommunityBuyHead(communityBuyHead) {
  return request({
    url: 'communitybuyhead',
    method: 'put',
    data: communityBuyHead
  })
}

/**
 * 开除团长
 *
 * @param headId 团长id
 */
export function fireCommunityBuyHead(headId) {
  return request({
    url: 'communitybuyhead/fire/' + headId,
    method: 'put',
  })
}

/**
 * 审核团长
 *
 * @param auditRequest 审核请求信息
 */
export function auditCommunityBuyHead(auditRequest) {
  return request({
    url: 'communitybuyhead/audit',
    method: 'put',
    data: auditRequest
  })
}

/**
 * 导出所有团长信息
 * @param status 团长状态
 */
export function exportAllCommunityBuyHead(status) {
  return request({
    url: 'communitybuyhead/exportall',
    method: 'post',
    params: {status: status},
    responseType: 'arraybuffer'
  })
}

/**
 * 导出选中团长信息
 */
export function exportCheckedCommunityBuyHead(status, ids) {
  return request({
    url: 'communitybuyhead/exportchecked',
    method: 'post',
    params: {ids: ids, status: status},
    responseType: 'arraybuffer'
  })
}
