package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.order.domain.OmsLogisticsCompany;
import com.ruoyi.order.domain.OmsLogisticsCompanyUse;
import com.ruoyi.order.mapper.OmsLogisticsCompanyMapper;
import com.ruoyi.order.mapper.OmsLogisticsCompanyUseMapper;
import com.ruoyi.order.service.IOmsLogisticsCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物流公司Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsLogisticsCompanyServiceImpl implements IOmsLogisticsCompanyService {
    @Autowired
    private OmsLogisticsCompanyMapper omsLogisticsCompanyMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OmsLogisticsCompanyServiceImpl.class);

    /**
     * 注入店铺物流公司使用数据库接口
     */
    @Autowired
    private OmsLogisticsCompanyUseMapper logisticsCompanyUseMapper;

    @Override
    public List<OmsLogisticsCompany> queryStoreUseCompanys(long storeId) {
        logger.debug("queryStoreUseCompanys and storeId:{}");
        return omsLogisticsCompanyMapper.queryStoreUseCompanys(storeId);
    }
    /**
     * 查询物流公司
     *
     * @param id 物流公司ID
     * @return 物流公司
     */
    @Override
    public OmsLogisticsCompany selectOmsLogisticsCompanyById(Long id) {
        return omsLogisticsCompanyMapper.selectOmsLogisticsCompanyById(id);
    }
    @Override
    public List<OmsLogisticsCompany> queryLogisticsCompanysWithUse(long storeId) {

        List<OmsLogisticsCompany> logisticsCompanies = this.selectOmsLogisticsCompanyList(null);

        if (CollectionUtils.isEmpty(logisticsCompanies)) {
            logger.info("there is no LogisticsCompany.....");
            return logisticsCompanies;
        }

        // 查询店铺使用的物流公司

        List<OmsLogisticsCompanyUse> logisticsCompanyUses = logisticsCompanyUseMapper.queryLogisticsCompanyUses(storeId);

        // 如果店铺没有使用物流公司 则直接返回
        if (CollectionUtils.isEmpty(logisticsCompanyUses)) {
            logger.info("store no use LogisticsCompany");
            return logisticsCompanies;
        }

        // 设置店铺使用的物流公司
        IteratorUtils.zip(logisticsCompanies, logisticsCompanyUses,
                (logisticsCompany, logisticsCompanyUse) -> logisticsCompany.getId() == logisticsCompanyUse.getCompanyId(),
                (logisticsCompany1, logisticsCompanyUse1) -> logisticsCompany1.setStoreUseCompany());

        return logisticsCompanies;
    }

    @Override
    public int changeLogisticsCompanyUse(long storeId, long companyId, int actionType) {
        logger.debug("changeLogisticsCompanyUse and storeId:{} \r\n companyId:{} \r\n actionType:{}", storeId, companyId, actionType);

        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("companyId", companyId);
        // 使用
        if (actionType == 1) {
            if (logisticsCompanyUseMapper.queryLogisticsCompanyUseCount(storeId) >= 20) {
                logger.error("changeLogisticsCompanyUse fail due to store use company >=20");
                return -1;
            }
            logisticsCompanyUseMapper.addLogisticsCompanyUse(params);
        } else {
            // 不使用
            logisticsCompanyUseMapper.deleteLogisticsCompanyUse(params);
        }

        return 1;
    }

    /**
     * 查询物流公司列表
     *
     * @param omsLogisticsCompany 物流公司
     * @return 物流公司
     */
    @Override
    public List<OmsLogisticsCompany> selectOmsLogisticsCompanyList(OmsLogisticsCompany omsLogisticsCompany) {
        return omsLogisticsCompanyMapper.selectOmsLogisticsCompanyList(omsLogisticsCompany);
    }

    /**
     * 新增物流公司
     *
     * @param omsLogisticsCompany 物流公司
     * @return 结果
     */
    @Override
    public int insertOmsLogisticsCompany(OmsLogisticsCompany omsLogisticsCompany) {
        omsLogisticsCompany.setCreateTime(DateUtils.getNowDate());
        return omsLogisticsCompanyMapper.insertOmsLogisticsCompany(omsLogisticsCompany);
    }

    /**
     * 修改物流公司
     *
     * @param omsLogisticsCompany 物流公司
     * @return 结果
     */
    @Override
    public int updateOmsLogisticsCompany(OmsLogisticsCompany omsLogisticsCompany) {
        return omsLogisticsCompanyMapper.updateOmsLogisticsCompany(omsLogisticsCompany);
    }

    /**
     * 批量删除物流公司
     *
     * @param ids 需要删除的物流公司ID
     * @return 结果
     */
    @Override
    public int deleteOmsLogisticsCompanyByIds(Long[] ids) {
        return omsLogisticsCompanyMapper.deleteOmsLogisticsCompanyByIds(ids);
    }

    /**
     * 删除物流公司信息
     *
     * @param id 物流公司ID
     * @return 结果
     */
    @Override
    public int deleteOmsLogisticsCompanyById(Long id) {
        return omsLogisticsCompanyMapper.deleteOmsLogisticsCompanyById(id);
    }
}
