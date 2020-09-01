/**
 * Created by dujinkai on 2019/6/5.
 */
import request from '@/utils/request';

/**
 * 根据id查询物流模版信息
 * @param id 模版id
 */
export function queryLogisticsTemplateById(id) {
  return request({
    url: '/order/OmsLogisticsTemplate/' + id,
    method: 'get'
  })
}

/**
 * 查询所有省份包括省份下面的市
 */
export function queryAllProvincesWithCity() {
  return request({
    url: '/order/OmsLogisticsTemplate/update/allprovinceswithcity',
    method: 'get',
  })
}


/**
 *  更新
 * @param logisticsTemplate 物流模版
 */
export function updateLogisticsTemplate(logisticsTemplate) {
  return request({
    url: '/order/OmsLogisticsTemplate',
    method: 'put',
    data: logisticsTemplate
  })
}

