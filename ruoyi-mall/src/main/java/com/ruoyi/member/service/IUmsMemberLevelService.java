package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsMemberLevel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员等级Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public interface IUmsMemberLevelService {
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
     * 根据消费金额计算出会员等级
     *
     * @param money 消费金额
     * @return 返回会员等级
     */
    UmsMemberLevel queryCustomerLevelByMoney(BigDecimal money);

    /**
     * 修改会员等级
     *
     * @param umsMemberLevel 会员等级
     * @return 结果
     */
    public int updateUmsMemberLevel(UmsMemberLevel umsMemberLevel);

    /**
     * 批量删除会员等级
     *
     * @param ids 需要删除的会员等级ID
     * @return 结果
     */
    public int deleteUmsMemberLevelByIds(Long[] ids);

    /**
     * 删除会员等级信息
     *
     * @param id 会员等级ID
     * @return 结果
     */
    public int deleteUmsMemberLevelById(Long id);

    /**
     * 查询所有的会员等级
     *
     * @return 返回所有的会员等级
     */
    List<UmsMemberLevel> queryAllCustomerLevels();
}
