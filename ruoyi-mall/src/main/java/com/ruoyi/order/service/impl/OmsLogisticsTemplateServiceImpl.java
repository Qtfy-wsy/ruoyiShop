package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.service.IPmsShippingMethodFreeshipService;
import com.ruoyi.goods.service.IPmsShippingMethodService;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.mapper.OmsLogisticsTemplateMapper;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 物流模版Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class OmsLogisticsTemplateServiceImpl implements IOmsLogisticsTemplateService {
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OmsLogisticsTemplateServiceImpl.class);

    /**
     * 注入数据库服务接口
     */
    @Autowired
    private OmsLogisticsTemplateMapper logisticsTemplateMapper;

    /**
     * 注入运费方式服务接口
     */
    @Autowired
    private IPmsShippingMethodService shippingMethodService;

    /**
     * 注入包邮运费方式服务接口
     */
    @Autowired
    private IPmsShippingMethodFreeshipService shippingMethodFreeShipService;
    @Autowired
    private OmsLogisticsTemplateMapper omsLogisticsTemplateMapper;

    /**
     * 查询物流模版
     *
     * @param id 物流模版ID
     * @return 物流模版
     */
    @Override
    public OmsLogisticsTemplate selectOmsLogisticsTemplateById(Long id) {
        return omsLogisticsTemplateMapper.selectOmsLogisticsTemplateById(id);
    }

    /**
     * 查询物流模版列表
     *
     * @param omsLogisticsTemplate 物流模版
     * @return 物流模版
     */
    @Override
    public List<OmsLogisticsTemplate> selectOmsLogisticsTemplateList(OmsLogisticsTemplate omsLogisticsTemplate) {
        return omsLogisticsTemplateMapper.selectOmsLogisticsTemplateList(omsLogisticsTemplate);
    }

    /**
     * 新增物流模版
     *
     * @param omsLogisticsTemplate 物流模版
     * @return 结果
     */
    @Override
    public int insertOmsLogisticsTemplate(OmsLogisticsTemplate omsLogisticsTemplate) {
        omsLogisticsTemplate.setCreateTime(DateUtils.getNowDate());
        return omsLogisticsTemplateMapper.insertOmsLogisticsTemplate(omsLogisticsTemplate);
    }

    /**
     * 修改物流模版
     *
     * @param logisticsTemplate 物流模版
     * @return 结果
     */
    @Override
    public int updateOmsLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate) {
        logger.debug("updateLogisticsTemplate and logisticsTemplate:{}", logisticsTemplate);

        // 校验参数
        if (Objects.isNull(logisticsTemplate)) {
            logger.error("updateLogisticsTemplate fail due to logisticsTemplate is null...");
            return -1;
        }

        // 校验重名
        OmsLogisticsTemplate checkLogisticsTemplate = queryLogisticsTemplateByName(logisticsTemplate);
        if (Objects.nonNull(checkLogisticsTemplate)) {
            if (checkLogisticsTemplate.getId() != logisticsTemplate.getId()) {
                logger.error("updateLogisticsTemplate fail due to checkLogisticsTemplate name has been exist");
                return -2;
            }
        }

        // 更新物流模版
        logisticsTemplateMapper.updateLogisticsTemplate(logisticsTemplate);


        // 设置运费方式的模版id
        logisticsTemplate.setShippingMethodTemplateId();

        // 更新运费方式
        shippingMethodService.updateShippingMethods(logisticsTemplate.getShippingMethods(), logisticsTemplate.getId());

        //更新包邮运费方式
        shippingMethodFreeShipService.updateShippingMethodFreeShips(logisticsTemplate.getShippingMethodFreeShips(), logisticsTemplate.getId());

        return 1;
    }

    /**
     * 批量删除物流模版
     *
     * @param ids 需要删除的物流模版ID
     * @return 结果
     */
    @Override
    public int deleteOmsLogisticsTemplateByIds(Long[] ids) {
        return omsLogisticsTemplateMapper.deleteOmsLogisticsTemplateByIds(ids);
    }

    /**
     * 删除物流模版信息
     *
     * @param id 物流模版ID
     * @return 结果
     */
    @Override
    public int deleteOmsLogisticsTemplateById(Long id) {
        return omsLogisticsTemplateMapper.deleteOmsLogisticsTemplateById(id);
    }

    @Override
    public List<OmsLogisticsTemplate> queryAllTemplates(long storeId) {
        logger.debug("queryAllTemplates and storeId:{}", storeId);
        return logisticsTemplateMapper.queryAllTemplates(storeId);
    }

    @Override
    public int addLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate) {
        logger.debug("addLogisticsTemplate and logisticsTemplate:{}", logisticsTemplate);

        // 校验参数
        if (Objects.isNull(logisticsTemplate)) {
            logger.error("addLogisticsTemplate fail due to logisticsTemplate is null");
            return -1;
        }

        if (Objects.nonNull(queryLogisticsTemplateByName(logisticsTemplate))) {
            logger.error("addLogisticsTemplate fail due to logisticsTemplate name has been exist");
            return -2;
        }

        // 首先查询店铺的默认运费模版
        OmsLogisticsTemplate defaultLogisticsTemplate = logisticsTemplateMapper.queryDefaultLogisticsTemplate(logisticsTemplate.getStoreId());

        // 如果没有默认运费模版，则新增的即为默认运费模版
        if (Objects.isNull(defaultLogisticsTemplate)) {
            logisticsTemplate.setIsDefault("0");
        }

        // 新增运费模版主表
        logisticsTemplateMapper.addLogisticsTemplate(logisticsTemplate);

        // 设置运费方式的模版id
        logisticsTemplate.setShippingMethodTemplateId();

        // 新增运费方式
        shippingMethodService.addShippingMethod(logisticsTemplate.getShippingMethods());

        // 新增包邮运费方式
        shippingMethodFreeShipService.addShippingMethodFreeShips(logisticsTemplate.getShippingMethodFreeShips());

        return 1;
    }

    @Transactional
    @Override
    public int setDefaultTemplate(long id, long storeId) {
        logger.debug("setDefaultTemplate and id:{} , storeId:{}", id, storeId);

        // 首先把所有的运费模版都改成非默认
        logisticsTemplateMapper.setLogisticsTemplateUnDefault(storeId);

        // 然后把当前的模版改成默认模版
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return logisticsTemplateMapper.setDefaultLogisticsTemplate(params);
    }

    @Override
    public OmsLogisticsTemplate queryLogisticsTemplate(long id, long storeId) {
        logger.debug("queryLogisticsTemplate and id:{},storeId:{}", id, storeId);


        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);

        OmsLogisticsTemplate logisticsTemplate = logisticsTemplateMapper.queryLogisticsTemplate(params);

        if (Objects.isNull(logisticsTemplate)) {
            return logisticsTemplate;
        }

        // 设置运费模版
        logisticsTemplate.setShippingMethods(shippingMethodService.queryByTemplateId(id));

        // 设置包邮运费模版
        logisticsTemplate.setShippingMethodFreeShips(shippingMethodFreeShipService.queryShippingMethodFreeShips(id));

        return logisticsTemplate;
    }

    @Override
    public int deleteLogisticsTemplate(long id, long storeId) {
        logger.debug("deleteLogisticsTemplate id:{} \r\n storeId:{}", id, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);

        OmsLogisticsTemplate logisticsTemplate = logisticsTemplateMapper.queryLogisticsTemplate(params);

        if (Objects.isNull(logisticsTemplate)) {
            return 1;
        }

        if (logisticsTemplate.isDefaultTemplate()) {
            logger.error("deleteLogisticsTemplate due to current template is default template....");
            return -1;
        }

        logisticsTemplateMapper.deleteLogisticsTemplate(params);

        // 删除运费方式
        shippingMethodService.deleteByTemplateId(id);

        // 删除包邮运费方式
        shippingMethodFreeShipService.deleteByTemplateId(id);

        return 1;
    }

    @Transactional
    @Override
    public int updateLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate) {
        logger.debug("updateLogisticsTemplate and logisticsTemplate:{}", logisticsTemplate);

        // 校验参数
        if (Objects.isNull(logisticsTemplate)) {
            logger.error("updateLogisticsTemplate fail due to logisticsTemplate is null...");
            return -1;
        }

        // 校验重名
        OmsLogisticsTemplate checkLogisticsTemplate = queryLogisticsTemplateByName(logisticsTemplate);
        if (Objects.nonNull(checkLogisticsTemplate)) {
            if (checkLogisticsTemplate.getId() != logisticsTemplate.getId()) {
                logger.error("updateLogisticsTemplate fail due to checkLogisticsTemplate name has been exist");
                return -2;
            }
        }

        // 更新物流模版
        logisticsTemplateMapper.updateLogisticsTemplate(logisticsTemplate);


        // 设置运费方式的模版id
        logisticsTemplate.setShippingMethodTemplateId();

        // 更新运费方式
        shippingMethodService.updateShippingMethods(logisticsTemplate.getShippingMethods(), logisticsTemplate.getId());

        //更新包邮运费方式
        shippingMethodFreeShipService.updateShippingMethodFreeShips(logisticsTemplate.getShippingMethodFreeShips(), logisticsTemplate.getId());

        return 1;
    }

    @Override
    public OmsLogisticsTemplate queryLogisticsTemplateByCityId(Long storeId, Long cityId) {

        logger.debug("queryLogisticsTemplateByCityId and storeId:{},cityId:{}", storeId, cityId);

        // 首先查询店铺的默认运费模版
        OmsLogisticsTemplate logisticsTemplate = logisticsTemplateMapper.queryDefaultLogisticsTemplate(storeId);

        if (Objects.isNull(logisticsTemplate)) {
            return logisticsTemplate;
        }


        // 根据市id设置运费模版的运费方式
        logisticsTemplate.setCustomerShippingMethod(shippingMethodService.queryShippingMethodByCityId(logisticsTemplate.getId(), cityId));

        return logisticsTemplate;
    }


    @Override
    public List<OmsLogisticsTemplate> queryLogisticsTemplateByCityIdAndId(Set<Long> ids, Long cityId) {
        logger.debug("queryLogisticsTemplateByCityIdAndId and ids:{} \r\n cityId", ids, cityId);

        return ids.stream().map(id -> this.queryByIdAndCityId(id, cityId)).collect(Collectors.toList());
    }


    /**
     * 根据运费模版id和市区id 查询运费模版
     *
     * @param id     运费模版id
     * @param cityId 市di
     * @return 返回运费模版
     */
    private OmsLogisticsTemplate queryByIdAndCityId(long id, long cityId) {
        logger.debug("queryByIdAndCityId and id  :{} \r\n cityId:{}", id, cityId);

        //查询运费模版
        OmsLogisticsTemplate logisticsTemplate = logisticsTemplateMapper.queryLogisticsTemplateById(id);

        if (Objects.isNull(logisticsTemplate)) {
            return logisticsTemplate;
        }

        // 根据市id设置运费模版的运费方式
        logisticsTemplate.setCustomerShippingMethod(shippingMethodService.queryShippingMethodByCityId(logisticsTemplate.getId(), cityId));

        // 根据市id设置包邮运费模版
        logisticsTemplate.setShippingMethodFreeShip(shippingMethodFreeShipService.queryByCityId(logisticsTemplate.getId(), cityId));

        return logisticsTemplate;
    }

    @Override
    public OmsLogisticsTemplate queryDefaultLogisticsTemplate(Long storeId) {
        logger.debug("queryDefaultLogisticsTemplate and storeId ;{}", storeId);
        return logisticsTemplateMapper.queryDefaultLogisticsTemplate(storeId);
    }

    /**
     * 根据名称查询运费模版
     *
     * @param logisticsTemplate 运费模版实体
     * @return 运费模版
     */
    private OmsLogisticsTemplate queryLogisticsTemplateByName(OmsLogisticsTemplate logisticsTemplate) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", logisticsTemplate.getName());
        params.put("storeId", logisticsTemplate.getStoreId());
        return logisticsTemplateMapper.queryLogisticsTemplateByName(params);
    }

}
