import request from '@/utils/request';


/**
 * 分页查询试用申请列表
 * @param query 查询参数
 */
export function queryTemplateSkus(query) {
  return request({
    url: 'template/allskus',
    method: 'get',
    params: query
  })
}

/**
 * 分页查询文章列表
 * @param query 查询参数
 */
export function queryArticleList(query) {
  return request({
    url: 'template/articlelist',
    method: 'get',
    params: query
  })
}

/**
 * 分页查询专题
 * @param query 查询参数
 */
export function queryThematic(query) {
  return request({
    url: 'template/thematic',
    method: 'get',
    params: query
  })
}

/**
 * 查询帮助列表
 * @param query 查询参数
 */
export function queryHelpList(query) {
  return request({
    url: 'template/helplist',
    method: 'get',
    params: query
  })
}

/**
 * 查询所有分类
 */
export function queryAllCategory() {
  return request({
    url: 'template/cates',
    method: 'get',
  })
}

/**
 * 根据ID查询单品信息
 */
export function querySkuById(id) {
  return request({
    url: 'template/sku/' + id,
    method: 'get',
  })
}


/**
 *根据文章id查询文章信息
 */
export function queryArticleById(id) {
  return request({
    url: 'template/article/' + id,
    method: 'get',
  })
}

/**
 * 根据id查询专题
 */
export function queryThematicById(id) {
  return request({
    url: 'template/thematic/' + id,
    method: 'get',
  })
}


/**
 * 根据id查询帮助
 */
export function queryHelpById(id) {
  return request({
    url: 'template/help/' + id,
    method: 'get',
  })
}

/**
 * 查询模版信息
 * @param id 模版id
 */
export function queryTemplate(id) {
  return request({
    url: 'template/' + id,
    method: 'get',
  })
}

/**
 * 更新模版
 * @param template 模版信息
 */
export function updateTemplate(template) {
  return request({
    url: 'template',
    method: 'put',
    data: template
  })
}
