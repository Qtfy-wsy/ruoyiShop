package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.UmsPreDepositRecord;

import java.util.List;
import java.util.Map;

/**
 * 会员预存款记录Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface UmsPreDepositRecordMapper {
    /**
     * 查询会员预存款记录
     *
     * @param id 会员预存款记录ID
     * @return 会员预存款记录
     */
    public UmsPreDepositRecord selectUmsPreDepositRecordById(Long id);

    /**
     * 查询会员预存款记录列表
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 会员预存款记录集合
     */
    public List<UmsPreDepositRecord> selectUmsPreDepositRecordList(UmsPreDepositRecord umsPreDepositRecord);

    /**
     * 新增会员预存款记录
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 结果
     */
    public int insertUmsPreDepositRecord(UmsPreDepositRecord umsPreDepositRecord);

    /**
     * 修改会员预存款记录
     *
     * @param umsPreDepositRecord 会员预存款记录
     * @return 结果
     */
    public int updateUmsPreDepositRecord(UmsPreDepositRecord umsPreDepositRecord);

    /**
     * 删除会员预存款记录
     *
     * @param id 会员预存款记录ID
     * @return 结果
     */
    public int deleteUmsPreDepositRecordById(Long id);

    /**
     * 批量删除会员预存款记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUmsPreDepositRecordByIds(Long[] ids);

    /**
     * 查询预存款记录总数
     *
     * @param params 查询条件
     * @return 返回预存款记录总数
     */

    int queryPredepositRecordCount(Map<String, Object> params);

    /**
     * 查询预存款记录
     *
     * @param params 查询条件
     * @return 返回预存款记录
     */

    List<UmsPreDepositRecord> queryPredepositRecords(Map<String, Object> params);

    /**
     * 根据会员id查询会员最近的一次预存款记录
     *
     * @param id 会员id
     * @return 返回会员的最近的一次预存款记录
     */

    UmsPreDepositRecord queryLastRecordByCustomerId(long id);

    /**
     * 添加预存款操作纪录
     *
     * @param predepositRecord 预存款
     * @return 成功>0 失败返回0
     */

    int addPredepositRecord(UmsPreDepositRecord predepositRecord);

    /**
     * 根据会员id和交易号查询预存款记录
     *
     * @param map 会员id,交易号
     * @return 返回会员的最近的一次预存款记录
     */

    UmsPreDepositRecord queryPredepositRecordByTransCode(Map<String, Object> map);

    /**
     * 更新预存款记录
     *
     * @param predepositRecord 预存款记录实体
     * @return 1:成功
     */

    int updatePredepositRecordStatus(UmsPreDepositRecord predepositRecord);

}
