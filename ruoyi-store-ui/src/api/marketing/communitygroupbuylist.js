import request from '@/utils/request';

/**
 * 分页查询社区团购信息
 * @param 查询参数
 */
export function queryCommunityBuyPageList(params) {
  return request({
    url: 'communitybuy',
    method: 'get',
    params: params
  })
}


/**
 * 查看是团购销量/库存时 分页查询单品信息
 * @param 查询参数
 */
export function pageListByMarketId(params) {
  return request({
    url: 'communitybuy/sku',
    method: 'get',
    params: params
  })
}

/**
 * 撤销团购活动
 * @param 查询参数
 */
export function dismissCommunityBuy(id) {
  return request({
    url: 'communitybuy/' + id,
    method: 'put',
  })
}


/**
 * 创建社区团购活动推广二维码
 * @param communityBuyId  社区团购活动 ID
 * @param w 宽
 * @param h 高
 */
export function createCommunityBuyQrCode(communityBuyId, w, h) {
  return request({
    url: 'communitybuy/qrcode',
    method: 'get',
    params: {communityBuyId: communityBuyId, w: w, h: h},
    responseType: 'arraybuffer'
  })
}

/**
 * 下载社区团购推广二维码 推广二维码
 * @param communityBuyId  社区团购活动 ID
 */
export function downPng(communityBuyId) {
  return request({
    url: 'communitybuy/png/' + communityBuyId,
    method: 'get',
    responseType: 'arraybuffer'
  })
}


/**
 * 获取移动端社区团购详情地址
 */
export function obtaincommunityBuyMobileUrl() {
  return request({
    url: 'communitybuy/mobileurl',
    method: 'get',
  })
}
