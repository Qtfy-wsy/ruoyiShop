package com.ruoyi.member.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.member.domain.UmsMemberLevel;
import com.ruoyi.member.mapper.UmsMemberLevelMapper;
import com.ruoyi.member.service.IUmsMemberLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 会员等级Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@Service
public class UmsMemberLevelServiceImpl implements IUmsMemberLevelService {
    private static final Logger logger = LoggerFactory.getLogger(UmsMemberLevelServiceImpl.class);

    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;

    /**
     * 查询会员等级
     *
     * @param id 会员等级ID
     * @return 会员等级
     */
    @Override
    public UmsMemberLevel selectUmsMemberLevelById(Long id) {
        return umsMemberLevelMapper.selectUmsMemberLevelById(id);
    }

    /**
     * 查询会员等级列表
     *
     * @param umsMemberLevel 会员等级
     * @return 会员等级
     */
    @Override
    public List<UmsMemberLevel> selectUmsMemberLevelList(UmsMemberLevel umsMemberLevel) {
        return umsMemberLevelMapper.selectUmsMemberLevelList(umsMemberLevel);
    }

    @Override
    public List<UmsMemberLevel> queryAllCustomerLevels() {
        logger.debug("begin to queryAllCustomerLevels .....");
        return umsMemberLevelMapper.queryAllCustomerLevels();
    }

    /**
     * 新增会员等级
     *
     * @param umsMemberLevel 会员等级
     * @return 结果
     */
    @Override
    public int insertUmsMemberLevel(UmsMemberLevel umsMemberLevel) {
        logger.debug("addCustomerLevel and customerLevel:{}", umsMemberLevel);

        if (Objects.isNull(umsMemberLevel)) {
            logger.error("addCustomerLevel fail due to customerLevel is null...");
            return 0;
        }

        // 存在交集 返回错误
        if (hasIntersection(umsMemberLevel, queryAllCustomerLevels())) {
            logger.error("addCustomerLevel fail due to hasIntersection");
            return -1;
        }
        umsMemberLevel.setCreateTime(DateUtils.getNowDate());
        return umsMemberLevelMapper.insertUmsMemberLevel(umsMemberLevel);
    }

    @Override
    public UmsMemberLevel queryCustomerLevelByMoney(BigDecimal money) {
        logger.debug("queryCustomerLevelByMoney and money:{}", money);
        return this.queryAllCustomerLevels().parallelStream().filter(customerLevel -> customerLevel.isCurrentLevl(money)).findFirst().orElseGet(UmsMemberLevel::buildNoLevel);
    }

    /**
     * 判断新增加的会员等级的消费金额和已经存在的会员等级消费金额是否存在交集
     *
     * @param customerLevel 会员等级
     * @return 存在返回true  不存在返回false
     */
    private boolean hasIntersection(UmsMemberLevel customerLevel, List<UmsMemberLevel> customerLevels) {
        return customerLevels.stream().map(customerLevel1 -> customerLevel1.hasIntersection(customerLevel)).filter(Boolean::booleanValue).count() >= 1;
    }

    /**
     * 修改会员等级
     *
     * @param umsMemberLevel 会员等级
     * @return 结果
     */
    @Override
    public int updateUmsMemberLevel(UmsMemberLevel umsMemberLevel) {
        return umsMemberLevelMapper.updateUmsMemberLevel(umsMemberLevel);
    }

    /**
     * 批量删除会员等级
     *
     * @param ids 需要删除的会员等级ID
     * @return 结果
     */
    @Override
    public int deleteUmsMemberLevelByIds(Long[] ids) {
        return umsMemberLevelMapper.deleteUmsMemberLevelByIds(ids);
    }

    /**
     * 删除会员等级信息
     *
     * @param id 会员等级ID
     * @return 结果
     */
    @Override
    public int deleteUmsMemberLevelById(Long id) {
        return umsMemberLevelMapper.deleteUmsMemberLevelById(id);
    }
}
