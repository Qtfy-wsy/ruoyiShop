import request from '@/utils/request';


/**
 * 查找所有手机分类
 */
export function queryAllMobileCate() {
  return request({
    url: 'mobilecate',
    method: 'get',
  })
}


/**
 * 根据父类ID手机分类
 */
export function queryMobileCateByParentId(parentId) {
  return request({
    url: 'mobilecate/child/' + parentId,
    method: 'get',
  })
}


/**
 * 根据ID查询手机分类
 */
export function queryMobileCateById(id) {
  return request({
    url: 'mobilecate/one/' + id,
    method: 'get',
  })
}


/**
 * 查找所有boss分类
 */
export function queryAllCategory() {
  return request({
    url: 'mobilecate/boss',
    method: 'get',
  })
}


/**
 *  添加手机分类
 *
 * @param logisticsTemplate 手机分类实体
 */
export function addMobileCate(cate) {
  return request({
    url: 'mobilecate',
    method: 'post',
    params: cate
  })
}

/**
 *  删除手机分类(级联删除)
 *
 * @param mobileCateId 分类 id
 */
export function delMobileCate(mobileCateId) {
  return request({
    url: 'mobilecate/' + mobileCateId,
    method: 'delete',
  })
}

/**
 *  更新手机分类
 *
 * @params 分类实体
 */
export function updateMobileCate(params) {
  return request({
    url: 'mobilecate',
    method: 'put',
    params: params
  })
}


