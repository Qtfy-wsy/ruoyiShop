import request from '@/utils/request';

/**
 * 查询秒杀场次
 * @param 时间
 */
export function querySeckillScenes(time) {
  return request({
    url: 'seckillscenes/' + time,
    method: 'get',
  })
}

/**
 * 更新秒杀场次
 * @param seckillScenes 秒杀场次
 * @param seckillTime 秒杀时间
 */
export function updateSeckillScene(seckillScenes, seckillTime) {
  return request({
    url: 'seckillscene/' + seckillTime,
    method: 'put',
    data: seckillScenes
  })
}
