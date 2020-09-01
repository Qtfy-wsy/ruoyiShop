/**
 * Created by dujinkai on 2019/6/5.
 */
import request from '@/utils/request';

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
 *  新增物流模版
 * @param logisticsTemplate 物流模版
 */
export function addLogisticsTemplate(logisticsTemplate) {
  return request({
    url: '/order/OmsLogisticsTemplate',
    method: 'post',
    data: logisticsTemplate
  })
}
