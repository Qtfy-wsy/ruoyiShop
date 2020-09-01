/**
 * 帮助列表路由
 *
 * @author Caizize created on 2019/6/3
 */

import request from '@/utils/request';


/**
 * 分页查询帮助列表
 *
 * @param query 查询参数
 */
export function queryHelpList(query) {
  return request({
    url: 'helplist',
    method: 'get',
    params: query
  })
}

/**
 * 删除帮助
 *
 * @param ids 帮助id数组
 */
export function deleteHelp(ids) {
  return request({
    url: 'help',
    method: 'delete',
    params: ids
  })
}

/**
 * 查询帮助分类列表（新增用）
 */
export function queryAllHelpCategoryForAdd() {
  return request({
    url: 'addhelp/helpcatelist',
    method: 'get',
  })
}

/**
 * 新增帮助
 *
 * @param helpList 帮助信息
 */
export function addHelp(helpList) {
  return request({
    url: 'help',
    method: 'post',
    data: helpList
  })
}

/**
 * 查询帮助分类列表（修改用）
 */
export function queryAllHelpCategoryForUpdate() {
  return request({
    url: 'updatehelp/helpcatelist',
    method: 'get',
  })
}

/**
 * 根据id查询帮助
 *
 * @param id 帮助id
 */
export function queryHelpById(id) {
  return request({
    url: 'help/' + id,
    method: 'get',
  })
}

/**
 * 修改帮助
 *
 * @param helpList 帮助信息
 */
export function updateHelp(helpList) {
  return request({
    url: 'help',
    method: 'put',
    data: helpList
  })
}
