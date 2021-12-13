/**
 * 促销设置路由
 *
 * @author 伊甸园商城 created on 2019/5/27
 */

import request from '@/utils/request';


/**
 * 查询促销设置
 */
export function queryMarketingSetting() {
  return request({
    url: 'marketingsetting',
    method: 'get',
  })
}

/**
 * 新增或修改促销设置
 *
 * @param marketingSetting 促销设置信息
 */
export function addOrUpdateMarketingSetting(marketingSetting) {
  return request({
    url: 'marketingsetting',
    method: 'post',
    data: marketingSetting
  })
}
