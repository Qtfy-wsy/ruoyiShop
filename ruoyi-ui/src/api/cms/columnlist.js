import request from '@/utils/request';


/**
 * 查询所有栏目列表
 */
export function queryColumnList() {
  return request({
    url: 'column',
    method: 'get',
  })
}

/**
 * 添加栏目
 * @param columnList 栏目信息
 */
export function addColumn(columnList) {
  return request({
    url: 'column',
    method: 'post',
    data: columnList
  })
}

/**
 * 编辑栏目
 * @param  columnList 栏目信息
 */
export function editColumn(columnList) {
  return request({
    url: 'column',
    method: 'put',
    data: columnList
  })
}

/**
 * 删除栏目
 * @param columnList 栏目信息
 */
export function deleteColumn(columnList) {
  return request({
    url: 'column',
    method: 'delete',
    data: columnList
  })
}
