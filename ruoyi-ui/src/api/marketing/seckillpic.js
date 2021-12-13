/**
 * 秒杀图片路由
 *
 * @author 伊甸园商城 created on 2020/5/12
 */

import request from '@/utils/request';


/**
 * 查询秒杀图片
 */
export function queryPanicBuyPic() {
  return request({
    url: 'panicbuypic',
    method: 'get'
  })
}

/**
 *
 * 保存秒杀图片
 * @param marketingPic 秒杀图片实体
 */
export function savePanicBuyPic(marketingPic) {
  return request({
    url: 'panicbuypic',
    method: 'post',
    data: marketingPic
  })
}
