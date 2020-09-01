import request from '@/utils/request';


/**
 * 查询活动中团长的销售信息
 * @param 查询参数
 */
export function printInvoiceOfHead(params) {
  return request({
    url: 'communityinvoice/head',
    method: 'get',
    params: params
  })
}
