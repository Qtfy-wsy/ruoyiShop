import request from '@/utils/request';

/**
 * 查询众筹详情
 * @param params 查询参数
 */
export function queryCrowdfundingMarketingByIdDetail(id) {
  return request({
    url: 'crowdfunding/detail/' + id,
    method: 'get',
  })
}


/**
 * 查询众筹分类
 */
export function queryMarketingCatesByTypeForCrowdfundingDetail() {
  return request({
    url: 'crowdfunding/detail/cates',
    method: 'get',
  })
}
