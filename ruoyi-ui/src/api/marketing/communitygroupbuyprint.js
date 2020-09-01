import request from '@/utils/request';


/**
 * 查询打印活动发货单信息
 * @param communityBuyId 活动 ID
 */
export function printInvoice(communityBuyId) {
  return request({
    url: 'communityinvoice/invoice/' + communityBuyId,
    method: 'get',
  })
}
