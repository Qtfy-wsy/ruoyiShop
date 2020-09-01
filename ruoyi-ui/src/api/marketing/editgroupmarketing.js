/**
 * 编辑拼团促销路由
 *
 * @author 魔金商城 created on 2019/8/7
 */

import request from '@/utils/request';


/**
 * 分页查询单品信息（新增拼团促销用）
 * @param params 查询参数
 */
export function querySkusForAddGroup(params) {
  return request({
    url: 'addgroupmarketing/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的互斥促销数量（新增拼团促销用）
 * @param marketing 促销信息
 */
export function queryExclusionMarketingCountForAddGroup(marketing) {
  return request({
    url: 'addgroupmarketing/exclusionmarketingcount',
    method: 'post',
    data: marketing
  })
}

/**
 * 新增拼团促销
 * @param marketing 促销信息
 */
export function addGroupMarketing(marketing) {
  return request({
    url: 'groupmarketing',
    method: 'post',
    data: marketing
  })
}

/**
 * 根据id查询拼团促销（修改拼团促销用）
 * @param id 促销id
 */
export function queryGroupMarketingById(id) {
  return request({
    url: 'groupmarketing/' + id,
    method: 'get',
  })
}

/**
 * 分页查询单品信息（修改拼团促销用）
 * @param params 查询参数
 */
export function querySkusForUpdateGroup(params) {
  return request({
    url: 'updategroupmarketing/skus',
    method: 'get',
    params: params
  })
}

/**
 * 查询交叉时间内含有相同单品的互斥促销数量（修改拼团促销用）
 * @param marketing 促销信息
 */
export function queryExclusionMarketingCountForUpdateGroup(marketing) {
  return request({
    url: 'updategroupmarketing/exclusionmarketingcount',
    method: 'post',
    data: marketing
  })
}

/**
 * 修改拼团促销
 * @param marketing 促销信息
 */
export function updateGroup(marketing) {
  return request({
    url: 'groupmarketing',
    method: 'put',
    data: marketing
  })
}

/**
 * 根据id查询拼团促销（查询拼团促销详情用）
 * @param id 促销id
 */
export function queryGroupMarketingDetailById(id) {
  return request({
    url: 'groupmarketingdetail/' + id,
    method: 'get',
  })
}
