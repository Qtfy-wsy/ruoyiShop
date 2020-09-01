import request from '@/utils/request';

/**
 * 分页查询红包列表
 * @param 查询参数
 */
export function redenvelopeList(query) {
  return request({
    url: 'redenvelope',
    method: 'get',
    params: query
  })
}

/**
 * 拷贝红包
 * @param id 红包id
 */
export function copyRedEnvelope(id) {
  return request({
    url: 'copyredenvelope/' + id,
    method: 'get',
  })
}

/**
 * 删除红包
 * @param ids 红包id
 */
export function deleteRedEnvelope(ids) {
  return request({
    url: 'redenvelope',
    method: 'delete',
    params: {ids: ids}
  })
}

/**
 * 添加红包
 * @param redenvelope 红包
 */
export function addRedEnvelope(redenvelope) {
  return request({
    url: 'redenvelope',
    method: 'post',
    data: redenvelope
  })
}
