import request from '@/utils/request';


/**
 * 分页查询sku列表信息
 */
export function queryMarketSkuPageList(params) {
  return request({
    url: 'addcommunitybuy',
    method: 'get',
    params: params
  })
}


/**
 * 创建社区团购活动
 */
export function addCommunityBuy(data) {
  return request({
    url: 'addcommunitybuy',
    method: 'post',
    data: data
  })
}
