import request from '@/utils/request';


/**
 * 分页查询社区团购发货单信息
 * @param 查询参数
 */
export function querycommunityInvoice(params) {
  return request({
    url: 'communityinvoice',
    method: 'get',
    params: params
  })
}


/**
 * 分页查询社区团购发货单详情
 * @param 查询参数
 */
export function queryCommunityInvoiceDetail(params) {
  return request({
    url: 'communityinvoice/detail',
    method: 'get',
    params: params
  })
}


/**
 * 查询社区团购的 '销售总额' 和 '销售总数'
 * @param communityBuyId 社区团购 ID
 */
export function queryMoneyAndNum(communityBuyId) {
  return request({
    url: 'communityinvoice/sales/' + communityBuyId,
    method: 'get',
  })
}


/**
 * 导出所有社区团购发货单信息
 * @param 查询参数
 */
export function exportAllCommunityInvoice(communityBuyId) {
  return request({
    url: 'communityinvoice/' + communityBuyId,
    method: 'post',
  })
}
