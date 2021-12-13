package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsShippingMethodFreeship;
import com.ruoyi.goods.mapper.PmsShippingMethodFreeshipMapper;
import com.ruoyi.goods.service.IPmsShippingMethodFreeshipAreaService;
import com.ruoyi.goods.service.IPmsShippingMethodFreeshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费模版包邮Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsShippingMethodFreeshipServiceImpl implements IPmsShippingMethodFreeshipService {
    /**
     * 调试日志
     */
    private Logger log = LoggerFactory.getLogger(PmsShippingMethodFreeshipServiceImpl.class);
    @Autowired
    private PmsShippingMethodFreeshipMapper pmsShippingMethodFreeshipMapper;
    /**
     * 注入包邮运费模版数据库接口
     */
    @Autowired
    private PmsShippingMethodFreeshipMapper shippingMethodFreeShipMapper;

    /**
     * 注入包邮运费模版区域服务接口
     */
    @Autowired
    private IPmsShippingMethodFreeshipAreaService shippingMethodFreeShipAreaService;


    @Override
    public void addShippingMethodFreeShips(List<PmsShippingMethodFreeship> shippingMethodFreeShips) {
        log.debug("addShippingMethodFreeShips and shippingMethodFreeShips:{}", shippingMethodFreeShips);

        if (CollectionUtils.isEmpty(shippingMethodFreeShips)) {
            log.error("addShippingMethodFreeShips fail due to shippingMethodFreeShips is empty...");
            return;
        }

        shippingMethodFreeShipMapper.addShippingMethodFreeShips(shippingMethodFreeShips);

        shippingMethodFreeShips.stream().forEach(shippingMethodFreeShip -> {
            shippingMethodFreeShip.setShippingMethodFreeShipAreaMethodId();
            shippingMethodFreeShipAreaService.addShippingMethodFreeShipAreas(shippingMethodFreeShip.getShippingMethodFreeShipAreas());
        });
    }

    @Override
    public List<PmsShippingMethodFreeship> queryShippingMethodFreeShips(long templateId) {
        log.debug("queryShippingMethodFreeShips and templateId:{}", templateId);

        List<PmsShippingMethodFreeship> shippingMethodFreeShips = shippingMethodFreeShipMapper.queryShippingMethodFreeShips(templateId);

        if (CollectionUtils.isEmpty(shippingMethodFreeShips)) {
            return shippingMethodFreeShips;
        }

        shippingMethodFreeShips.parallelStream().forEach(shippingMethodFreeShip ->
                shippingMethodFreeShip.setShippingMethodFreeShipAreas(shippingMethodFreeShipAreaService.queryByMethodId(shippingMethodFreeShip.getId())));

        return shippingMethodFreeShips;
    }

    @Override
    public void deleteByTemplateId(long templateId) {
        log.debug("deleteByTemplateId and templateId:{}", templateId);
        // 删除包邮运费方式
        shippingMethodFreeShipMapper.deleteByTemplateId(templateId);

        // 删除包邮运费方式的地区
        shippingMethodFreeShipAreaService.deleteByTemplateId(templateId);
    }

    @Transactional
    @Override
    public void updateShippingMethodFreeShips(List<PmsShippingMethodFreeship> shippingMethodFreeShips, long templateId) {
        log.debug("updateShippingMethodFreeShips and shippingMethodFreeShips:{} \r\n templateId:{}", shippingMethodFreeShips, templateId);

        this.deleteByTemplateId(templateId);

        this.addShippingMethodFreeShips(shippingMethodFreeShips);

    }

    @Override
    public PmsShippingMethodFreeship queryByCityId(Long templateId, Long cityId) {
        log.debug("queryByCityId and templateId:{} \r\n cityId:{}");
        Map<String, Object> params = new HashMap<>();
        params.put("templateId", templateId);
        params.put("cityId", cityId);
        return shippingMethodFreeShipMapper.queryByCityIdAndTemplateId(params);
    }

    /**
     * 查询运费模版包邮
     *
     * @param id 运费模版包邮ID
     * @return 运费模版包邮
     */
    @Override
    public PmsShippingMethodFreeship selectPmsShippingMethodFreeshipById(Long id) {
        return pmsShippingMethodFreeshipMapper.selectPmsShippingMethodFreeshipById(id);
    }

    /**
     * 查询运费模版包邮列表
     *
     * @param pmsShippingMethodFreeship 运费模版包邮
     * @return 运费模版包邮
     */
    @Override
    public List<PmsShippingMethodFreeship> selectPmsShippingMethodFreeshipList(PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        return pmsShippingMethodFreeshipMapper.selectPmsShippingMethodFreeshipList(pmsShippingMethodFreeship);
    }

    /**
     * 新增运费模版包邮
     *
     * @param pmsShippingMethodFreeship 运费模版包邮
     * @return 结果
     */
    @Override
    public int insertPmsShippingMethodFreeship(PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        return pmsShippingMethodFreeshipMapper.insertPmsShippingMethodFreeship(pmsShippingMethodFreeship);
    }

    /**
     * 修改运费模版包邮
     *
     * @param pmsShippingMethodFreeship 运费模版包邮
     * @return 结果
     */
    @Override
    public int updatePmsShippingMethodFreeship(PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        return pmsShippingMethodFreeshipMapper.updatePmsShippingMethodFreeship(pmsShippingMethodFreeship);
    }

    /**
     * 批量删除运费模版包邮
     *
     * @param ids 需要删除的运费模版包邮ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodFreeshipByIds(Long[] ids) {
        return pmsShippingMethodFreeshipMapper.deletePmsShippingMethodFreeshipByIds(ids);
    }

    /**
     * 删除运费模版包邮信息
     *
     * @param id 运费模版包邮ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodFreeshipById(Long id) {
        return pmsShippingMethodFreeshipMapper.deletePmsShippingMethodFreeshipById(id);
    }
}
