import request from '@/utils/request';


/**
 * 分页查询sku列表信息
 */
export function queryMarketSkuPageList(params) {
  return request({
    url: 'editcommunitybuy/sku',
    method: 'get',
    params: params
  })
}


/**
 * 保存编辑后的团购活动信息
 */
export function editCommunityBuy(data) {
  return request({
    url: 'editcommunitybuy',
    method: 'put',
    data: data
  })
}

/**
 * 编辑团购活动时查询该团购信息
 */
export function queryCommunityBuy(communityBuyId) {
  return request({
    url: 'editcommunitybuy/' + communityBuyId,
    method: 'get',
  })
}


