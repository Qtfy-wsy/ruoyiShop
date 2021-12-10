import request from '@/utils/request';


/**
 * 查询基本信息和高级信息设置
 */
export function queryBaseInfoSet() {
  return request({
    url: '/goodsauditset',
    method: 'get',
  })
}


/**
 * 设置审核开关状态
 * @param storeSpuAudit 状态 0 是  1 否
 */
export function setAuditSwitch(storeSpuAudit) {
  return request({
    url: '/goodsauditset',
    method: 'put',
    data: storeSpuAudit
  })
}
