package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.UmsBrowseRecord;

import java.util.List;
import java.util.Map;

/**
 * 会员浏览记录Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface UmsBrowseRecordMapper {
    /**
     * 查询会员浏览记录
     *
     * @param id 会员浏览记录ID
     * @return 会员浏览记录
     */
    public UmsBrowseRecord selectUmsBrowseRecordById(Long id);

    /**
     * 查询会员浏览记录列表
     *
     * @param umsBrowseRecord 会员浏览记录
     * @return 会员浏览记录集合
     */
    public List<UmsBrowseRecord> selectUmsBrowseRecordList(UmsBrowseRecord umsBrowseRecord);

    /**
     * 查询浏览历史列表
     *
     * @param customerId 会员id
     * @return 浏览历史列表
     */

    List<UmsBrowseRecord> queryBrowseRecord(long customerId);

    /**
     * 按天删除浏览历史
     *
     * @param params 日期及会员id
     * @return 成功返回>=1，失败返回0
     */

    int deleteBrowseRecordByDay(Map<String, Object> params);

    /**
     * 根据id删除浏览历史
     *
     * @param id 浏览历史id
     * @return 成功返回1，失败返回0
     */

    int deleteBrowseRecordById(long id);


    /**
     * 添加浏览记录
     *
     * @param browseRecord 浏览记录
     */

    int addBrowseRecord(UmsBrowseRecord browseRecord);

    /**
     * 根据用户id 和单品id查询浏览记录总数
     *
     * @param browseRecord 浏览记录
     * @return 返回浏览记录总数
     */

    int queryByCustomerIdAndSkuId(UmsBrowseRecord browseRecord);

    /**
     * 查询浏览记录总数
     *
     * @param params 查询参数
     * @return 返回浏览记录总数
     */

    int queryBrowseRecordsCount(Map<String, Object> params);

    /**
     * 分页查询浏览记录
     *
     * @param params 查询参数
     * @return 返回浏览记录
     */

    List<UmsBrowseRecord> queryBrowseRecords(Map<String, Object> params);

    /**
     * 根据用户id删除浏览记录
     *
     * @param customerId 用户id
     * @return >=1 成功 否则失败
     */

    int deleteByCustomerId(long customerId);

    /**
     * 查询浏览纪录的单品类型
     *
     * @param customerId 会员id
     * @return 返回浏览纪录
     */

    List<UmsBrowseRecord> queryBrowseSkuType(long customerId);

    /**
     * 新增会员浏览记录
     *
     * @param umsBrowseRecord 会员浏览记录
     * @return 结果
     */
    public int insertUmsBrowseRecord(UmsBrowseRecord umsBrowseRecord);

    /**
     * 修改会员浏览记录
     *
     * @param umsBrowseRecord 会员浏览记录
     * @return 结果
     */
    public int updateUmsBrowseRecord(UmsBrowseRecord umsBrowseRecord);

    /**
     * 删除会员浏览记录
     *
     * @param id 会员浏览记录ID
     * @return 结果
     */
    public int deleteUmsBrowseRecordById(Long id);

    /**
     * 批量删除会员浏览记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUmsBrowseRecordByIds(Long[] ids);
}
