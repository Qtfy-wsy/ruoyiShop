import request from '@/utils/request';


/**
 * 查询社区推广信息
 */
export function selectCommunityBuyIndex() {
  return request({
    url: 'recruitmenthead',
    method: 'get',
  })
}


/**
 * 修改推广内容
 * @param communityBuyIndex 社区推广实体
 */
export function editCommunityBuyIndex(communityBuyIndex) {
  return request({
    url: 'recruitmenthead',
    method: 'put',
    data: communityBuyIndex
  })
}


/**
 * 下载推广二维码
 * @param urlList 链接地址
 */
export function downPng(urlList) {
  return request({
    url: 'recruitmenthead',
    method: 'post',
    params: urlList,
    responseType: 'arraybuffer'
  })
}


/**
 * 创建二维码
 */
export function createQeCode(url, w, h) {
  return request({
    url: 'recruitmenthead/qrcode?url=' + url + '&w=' + w + '&h' + h,
    method: 'get',
    responseType: 'arraybuffer'
  })
}
