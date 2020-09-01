import request from '@/utils/request';

/**
 * 分页查询秒杀场次活动
 * @param query 查询参数
 */
export function querySeckillSceneLists(query) {
  return request({
    url: 'seckillscene/list',
    method: 'get',
    params: query
  })
}
