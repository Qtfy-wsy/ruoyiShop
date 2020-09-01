package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.UmsMemberLevel;

import java.util.List;

/**
 * 会员等级Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public interface UmsMemberLevelMapper {
    /**
     * 查询会员等级
     *
     * @param id 会员等级ID
     * @return 会员等级
     */
    public UmsMemberLevel selectUmsMemberLevelById(Long id);

    /**
     * 查询会员等级列表
     *
     * @param umsMemberLevel 会员等级
     * @return 会员等级集合
     */
    public List<UmsMemberLevel> selectUmsMemberLevelList(UmsMemberLevel umsMemberLevel);

    /**
     * 新增会员等级
     *
     * @param umsMemberLevel 会员等级
     * @return 结果
     */
    public int insertUmsMemberLevel(UmsMemberLevel umsMemberLevel);

    /**
     * 修改会员等级
     *
     * @param umsMemberLevel 会员等级
     * @return 结果
     */
    public int updateUmsMemberLevel(UmsMemberLevel umsMemberLevel);

    /**
     * 删除会员等级
     *
     * @param id 会员等级ID
     * @return 结果
     */
    public int deleteUmsMemberLevelById(Long id);

    /**
     * 批量删除会员等级
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUmsMemberLevelByIds(Long[] ids);

    List<UmsMemberLevel> queryAllCustomerLevels();
}
