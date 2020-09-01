/**
 * 帮助分类路由
 *
 * @author Caizize created on 2019/6/3
 */

import request from '@/utils/request';


/**
 * 分页查询帮助分类列表
 *
 * @param query 查询参数
 */
export function queryHelpCateList(query) {
    return request({
        url: 'helpcatelist',
        method: 'get',
        params: query
    })
}

/**
 * 新增帮助分类
 *
 * @param helpCategory 帮助分类信息
 */
export function addHelpCategory(helpCategory) {
    return request({
        url: 'helpcate',
        method: 'post',
        data: helpCategory
    })
}

/**
 * 根据id查询帮助分类
 *
 * @param id 帮助分类id
 */
export function queryHelpCategoryById(id) {
    return request({
        url: 'helpcate/' + id,
        method: 'get',
    })
}

/**
 * 修改帮助分类
 *
 * @param helpCategory 帮助分类信息
 */
export function updateHelpCategory(helpCategory) {
    return request({
        url: 'helpcate',
        method: 'put',
        data: helpCategory
    })
}

/**
 * 删除帮助分类
 *
 * @param ids 帮助分类id数组
 */
export function deleteHelpCategory(ids) {
    return request({
        url: 'helpcate',
        method: 'delete',
        params: ids
    })
}
