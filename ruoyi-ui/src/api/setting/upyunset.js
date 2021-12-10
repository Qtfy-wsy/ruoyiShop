/**
 * 又拍云设置路由
 *
 * @author 商城
 */

import request from '@/utils/request';


/**
 * 查询又拍云设置
 */
export function getSysSysQiniuConfig() {
  return request({
    url: '/aliyun/oss/oss',
    method: 'get',
  })
}

/**
 * 修改又拍云设置
 *
 * @param upYunSetting 又拍云设置信息
 */
export function updateSysSysQiniuConfig(upYunSetting) {
  return request({
    url: '/aliyun/oss/oss',
    method: 'put',
    data: upYunSetting
  })
}
