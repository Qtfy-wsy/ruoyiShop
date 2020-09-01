package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsBrowseRecord;
import com.ruoyi.member.vo.BrowseRecordList;
import com.ruoyi.util.PageHelper;

import java.util.List;
import java.util.Set;

/**
 * 会员浏览记录Service接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface IUmsBrowseRecordService {

    /**
     * 查询浏览历史列表
     *
     * @param customerId 会员id
     * @return 浏览历史列表
     */
    List<BrowseRecordList> queryBrowseRecord(long customerId);

    /**
     * 分页查询浏览记录
     *
     * @param pageHelper 分页帮助类
     * @param customerId 用户id
     * @return 返回用户浏览记录
     */
    PageHelper<UmsBrowseRecord> queryBrowseRecords(PageHelper<UmsBrowseRecord> pageHelper, long customerId);

    /**
     * 按天删除浏览历史
     *
     * @param customerId 会员id
     * @param createTime 日期
     * @return 成功返回>=1，失败返回0
     */
    int deleteBrowseRecordByDay(long customerId, String createTime);

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
     * @return 成功返回1 失败返回0
     */
    int addBrowseRecord(UmsBrowseRecord browseRecord);

    /**
     * 根据用户id删除浏览记录
     *
     * @param customerId 用户id
     * @return >=1 成功 否则失败
     */
    int deleteByCustomerId(long customerId);

    /**
     * 查询用户的浏览纪录
     *
     * @param customerId 会员id
     * @return 返回用户的浏览纪录
     */
    int queryCustomerBrowseRecordCount(long customerId);

    /**
     * 查询浏览纪录的单品类型
     *
     * @param customerId 会员id
     * @return 返回浏览纪录的单品类型
     */
    Set<Long> queryBrowseSkuType(long customerId);

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
     * 批量删除会员浏览记录
     *
     * @param ids 需要删除的会员浏览记录ID
     * @return 结果
     */
    public int deleteUmsBrowseRecordByIds(Long[] ids);

    /**
     * 删除会员浏览记录信息
     *
     * @param id 会员浏览记录ID
     * @return 结果
     */
    public int deleteUmsBrowseRecordById(Long id);
}
