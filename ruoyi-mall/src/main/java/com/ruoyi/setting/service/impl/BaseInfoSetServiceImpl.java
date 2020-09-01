package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.bean.BaseInfoSet;
import com.ruoyi.setting.mapper.BaseInfoSetMapper;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.util.RedisCahceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 基本信息设置service实现类
 *
 * @author 魔金商城 on 2017/5/17.
 */
@Service
public class BaseInfoSetServiceImpl implements BaseInfoSetService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(BaseInfoSetServiceImpl.class);

    /**
     * 注入信息设置mapper
     */
    @Autowired
    private BaseInfoSetMapper baseInfoSetMapper;

    /**
     * 查询基本信息和高级信息设置
     *
     * @return 基本信息和高级信息设置实体类
     */
    @Cacheable(value = RedisCahceKey.BASE_INFO_SET, key = "'BaseInfoSet'")
    @Override
    public BaseInfoSet queryBaseInfoSet() {
        logger.debug("queryBaseInfoSet...");
        return baseInfoSetMapper.queryBaseInfoSet();
    }

    /**
     * 编辑基本信息和高级信息
     *
     * @param baseInfoSet 基本信息实体类
     * @param type        1 基本信息 0高级信息
     * @return -1编辑失败 1编辑成功 0编辑失败
     */
    @CacheEvict(value = RedisCahceKey.BASE_INFO_SET, allEntries = true)
    @Override
    public int editBaseInfoSet(BaseInfoSet baseInfoSet, int type) {
        logger.debug("editBaseInfoSet and baseInfoSet:{}\r\n type:{}", baseInfoSet, type);
        //非法参数
        if (!checkParam(type)) {
            logger.error("editBaseInfoSet error due to type is illegal...");
            return -1;
        }
        baseInfoSet.dealPicUrl();
        //设置基本信息
        if (type == 1) {
            logger.debug("editBaseInfoSet to editBaseInfoSetA and type:{}", type);
            return baseInfoSetMapper.editBaseInfoSetA(baseInfoSet);
        }
        //设置高级信息
        if (type == 0) {
            logger.debug("editBaseInfoSet to editBaseInfoSetB and type:{}", type);
            return baseInfoSetMapper.editBaseInfoSetB(baseInfoSet);
        }
        return 0;
    }

    /**
     * 设置审核开关
     *
     * @param storeSpuAudit 店铺商品审核开关  0 需要审核 1 不需要 默认0
     * @return 成功返回1，失败返回0
     */
    @CacheEvict(value = RedisCahceKey.BASE_INFO_SET, allEntries = true)
    @Override
    public int setAuditSwitch(String storeSpuAudit) {
        logger.debug("setAuditSwitch and storeSpuAudit ：{}", storeSpuAudit);
        return baseInfoSetMapper.setAuditSwitch(storeSpuAudit);
    }

    /**
     * 设置会员价启用状态
     *
     * @param memberPriceStatus 会员等级启用状态   0 启用 1 不启用 默认0
     * @return 成功返回1，失败返回0
     */
    @CacheEvict(value = RedisCahceKey.BASE_INFO_SET, allEntries = true)
    @Override
    public int setMemberPriceStatus(String memberPriceStatus) {
        logger.debug("setMemberPriceStatus and memberPriceStatus ：{}", memberPriceStatus);
        return baseInfoSetMapper.setMemberPriceStatus(memberPriceStatus);
    }

    @Override
    public boolean isSkuNeedAudit() {
        return queryBaseInfoSet().isSpuNeedAudit();
    }

    /**
     * 校验参数是否为 0 或 1
     *
     * @param type 参数
     * @return 正确true 错误 FALSE
     */
    private boolean checkParam(int type) {
        return type == 1 || type == 0;
    }

}
