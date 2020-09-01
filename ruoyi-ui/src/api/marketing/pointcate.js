/**
 * 积分商城分类路由
 *
 * @author 魔金商城 created on 2019/7/26
 */

import request from '@/utils/request';


/**
 * 分页查询积分商城分类列表
 *
 * @param query 查询参数
 */
export function queryPointCates(query) {
    return request({
        url: 'pointcates',
        method: 'get',
        params: query
    })
}

/**
 * 新增积分商城分类
 *
 * @param pointCate 积分商城分类信息
 */
export function addPointCate(pointCate) {
    return request({
        url: 'pointcate',
        method: 'post',
        data: pointCate
    })
}

/**
 * 根据id查询积分商城分类
 *
 * @param id 积分商城分类id
 */
export function queryPointCateById(id) {
    return request({
        url: 'pointcate/' + id,
        method: 'get',
    })
}

/**
 * 修改积分商城分类
 *
 * @param pointCate 积分商城分类信息
 */
export function updatePointCate(pointCate) {
    return request({
        url: 'pointcate',
        method: 'put',
        data: pointCate
    })
}

/**
 * 删除积分商城分类
 *
 * @param ids 积分商城分类id数组
 */
export function deletePointCate(ids) {
    return request({
        url: 'pointcate',
        method: 'delete',
        params: ids
    })
}
