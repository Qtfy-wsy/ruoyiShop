package com.ruoyi.member.service;

import com.ruoyi.common.utils.bean.WithdrawResponse;
import com.ruoyi.member.domain.UmsWithdraw;
import com.ruoyi.util.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * 提现记录Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public interface IUmsWithdrawService {

    /**
     * 分页查询提现记录
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询帮助类
     * @return 提现记录
     */
    PageHelper<UmsWithdraw> queryWithdrawRecords(PageHelper<UmsWithdraw> pageHelper, UmsWithdraw.QueryCriteria queryCriteria);

    /**
     * 分页查询提现记录(admin端使用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询帮助类
     * @return 提现记录
     */
    PageHelper<UmsWithdraw> queryWithdrawRecordsForAdmin(PageHelper<UmsWithdraw> pageHelper, UmsWithdraw.QueryCriteria queryCriteria);

    /**
     * 新增提现记录
     *
     * @param withdrawRecord 提现记录实体
     * @param password       支付密码
     * @return 1成功 0失败 -1用户不存在 -2用户没有设置支付密码 -3支付密码不正确 -4参数不全 -5佣金不足
     */
    int addWithdrawRecord(UmsWithdraw withdrawRecord, String password);

    /**
     * 更新提现记录状态
     *
     * @param id     提现记录id
     * @param status 状态
     * @return 1成功 0失败
     */
    int updateWithdrawRecordStatus(long id, String status);

    /**
     * 发送金额
     *
     * @return 返回提现结果
     */
    WithdrawResponse releaseMoney(long id);

    /**
     * 查询提现记录
     *
     * @param id 提现记录ID
     * @return 提现记录
     */
    public UmsWithdraw selectUmsWithdrawById(Long id);

    /**
     * 查询提现记录列表
     *
     * @param umsWithdraw 提现记录
     * @return 提现记录集合
     */
    public List<UmsWithdraw> selectUmsWithdrawList(UmsWithdraw umsWithdraw);

    /**
     * 新增提现记录
     *
     * @param umsWithdraw 提现记录
     * @return 结果
     */
    public int insertUmsWithdraw(UmsWithdraw umsWithdraw);

    /**
     * 修改提现记录
     *
     * @param umsWithdraw 提现记录
     * @return 结果
     */
    public int updateUmsWithdraw(UmsWithdraw umsWithdraw);

    /**
     * 批量删除提现记录
     *
     * @param ids 需要删除的提现记录ID
     * @return 结果
     */
    public int deleteUmsWithdrawByIds(Long[] ids);

    /**
     * 删除提现记录信息
     *
     * @param id 提现记录ID
     * @return 结果
     */
    public int deleteUmsWithdrawById(Long id);

    String queryWithdrawMoney(Map map);
}
