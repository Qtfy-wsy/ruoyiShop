import request from '@/utils/request';

//查询社区团设置
export function queryCommunitySet() {
  return request({
    url: 'communitygroupbuyset',
    method: 'get',
  })
}

//保存设置
export function saveSetting(param) {
  return request({
    url: 'communitygroupbuyset',
    method: 'put',
    data: param
  })
}
