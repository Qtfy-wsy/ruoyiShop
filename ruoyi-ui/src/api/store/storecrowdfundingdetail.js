import request from '@/utils/request';

/**
 * 分页查询单品信息
 * @param params 查询参数
 */
export function queryStoreCrowdfundingMarketingById(id) {
  return request({
    url: 'store/crowdfunding/' + id,
    method: 'get',
  })
}


/**
 * 查询众筹分类
 */
export function queryMarketingCatesByTypeForStoreCrowdfunding() {
  return request({
    url: 'store/crowdfunding/cate',
    method: 'get',
  })
}
