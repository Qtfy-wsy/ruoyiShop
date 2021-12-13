/**
 * 促销分类路由
 *
 * @author 伊甸园商城 created on 2019/5/24
 */

import request from '@/utils/request';


/**
 * 分页查询促销分类列表
 *
 * @param query 查询参数
 */
export function queryMarketingCates(query) {
    return request({
        url: 'marketingcates',
        method: 'get',
        params: query
    })
}

/**
 * 新增促销分类
 *
 * @param marketingCate 促销分类信息
 */
export function addMarketingCate(marketingCate) {
    return request({
        url: 'marketingcate',
        method: 'post',
        data: marketingCate
    })
}

/**
 * 根据id查询促销分类
 *
 * @param id 促销分类id
 */
export function queryMarketingCateById(id) {
    return request({
        url: 'marketingcate/' + id,
        method: 'get',
    })
}

/**
 * 修改促销分类
 *
 * @param marketingCate 促销分类信息
 */
export function updateMarketingCate(marketingCate) {
    return request({
        url: 'marketingcate',
        method: 'put',
        data: marketingCate
    })
}

/**
 * 删除促销分类
 *
 * @param ids 促销分类id数组
 */
export function deleteMarketingCate(ids) {
    return request({
        url: 'marketingcate',
        method: 'delete',
        params: ids
    })
}
