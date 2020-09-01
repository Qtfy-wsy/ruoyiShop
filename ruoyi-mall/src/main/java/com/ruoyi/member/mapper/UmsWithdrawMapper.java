package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.UmsWithdraw;

import java.util.List;
import java.util.Map;

/**
 * 提现记录Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface UmsWithdrawMapper {
    /**
     * 分页查询提现记录
     *
     * @param params 参数
     * @return 提现记录
     */

    List<UmsWithdraw> queryWithdrawRecords(Map<String, Object> params);

    /**
     * 分页查询提现记录(admin端使用)
     *
     * @param params 参数
     * @return 提现记录
     */

    List<UmsWithdraw> queryWithdrawRecordsForAdmin(Map<String, Object> params);

    /**
     * 查询提现记录数量(admin端使用)
     *
     * @param params 参数
     * @return 数量
     */

    int queryWithdrawRecordsCountForAdmin(Map<String, Object> params);


    /**
     * 根据id查询提现记录
     *
     * @param id 提现记录id
     * @return 提现记录实体
     */

    UmsWithdraw queryWithdrawRecordById(long id);

    /**
     * 查询提现记录数量
     *
     * @param params 参数
     * @return 数量
     */

    int queryWithdrawRecordsCount(Map<String, Object> params);

    /**
     * 新增提现记录
     *
     * @param withdrawRecord 提现记录实体
     * @return 1成功 0失败
     */

    int addWithdrawRecord(UmsWithdraw withdrawRecord);

    /**
     * 更新提现记录状态
     *
     * @param params 参数
     * @return 1成功 0失败
     */

    int updateWithdrawRecordStatus(Map<String, Object> params);

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
     * 删除提现记录
     *
     * @param id 提现记录ID
     * @return 结果
     */
    public int deleteUmsWithdrawById(Long id);

    /**
     * 批量删除提现记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUmsWithdrawByIds(Long[] ids);

    String queryWithdrawMoney(Map map);
}
