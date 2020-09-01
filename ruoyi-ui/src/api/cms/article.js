/**
 * 文章路由
 *
 * @author Caizize created on 2019/5/31
 */

import request from '@/utils/request';


/**
 * 分页查询文章列表
 *
 * @param query 查询参数
 */
export function queryArticleList(query) {
  return request({
    url: 'articlelist',
    method: 'get',
    params: query
  })
}

/**
 * 删除文章
 *
 * @param ids 文章id数组
 */
export function deleteArticle(ids) {
  return request({
    url: 'article',
    method: 'delete',
    params: ids,
  })
}

/**
 * 查询所有二级栏目列表（新增用）
 */
export function queryChildColumnListForArticleForAdd() {
  return request({
    url: 'article/columnlistforadd',
    method: 'get',
  })
}

/**
 * 新增文章
 *
 * @param articleList 文章实体
 */
export function addArticle(articleList) {
  return request({
    url: 'article',
    method: 'post',
    data: articleList,
  })
}

/**
 * 查询所有二级栏目列表（修改用）
 */
export function queryChildColumnListForArticleForUpdate() {
  return request({
    url: 'article/columnlistforupdate',
    method: 'get',
  })
}

/**
 * 根据id查询文章
 *
 * @param id 文章id
 */
export function queryArticleById(id) {
  return request({
    url: 'article/' + id,
    method: 'get',
  })
}

/**
 * 修改文章
 *
 * @param articleList 文章实体
 */
export function updateArticle(articleList) {
  return request({
    url: 'article',
    method: 'put',
    data: articleList,
  })
}
