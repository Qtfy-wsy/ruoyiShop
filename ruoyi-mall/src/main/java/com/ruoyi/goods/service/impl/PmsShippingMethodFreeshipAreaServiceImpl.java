package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsShippingMethodFreeshipArea;
import com.ruoyi.goods.mapper.PmsShippingMethodFreeshipAreaMapper;
import com.ruoyi.goods.service.IPmsShippingMethodFreeshipAreaService;
import com.ruoyi.setting.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 运费方式包邮关联的区域Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsShippingMethodFreeshipAreaServiceImpl implements IPmsShippingMethodFreeshipAreaService {
    /**
     * 调试日志
     */
    private Logger log = LoggerFactory.getLogger(PmsShippingMethodFreeshipAreaServiceImpl.class);

    @Autowired
    private PmsShippingMethodFreeshipAreaMapper pmsShippingMethodFreeshipAreaMapper;

    /**
     * 注入包邮运费关联区域的数据库接口
     */
    @Autowired
    private PmsShippingMethodFreeshipAreaMapper shippingMethodFreeShipAreaMapper;

    /**
     * 注入区域服务接口
     */
      @Autowired
      private AreaService areaService;
    @Override
    public void addShippingMethodFreeShipAreas(List<PmsShippingMethodFreeshipArea> shippingMethodFreeShipAreas) {
        log.debug("addShippingMethodFreeShipAreas and shippingMethodFreeShipAreas:{}", shippingMethodFreeShipAreas);
        if (CollectionUtils.isEmpty(shippingMethodFreeShipAreas)) {
            log.warn("there is no shippingMethodFreeShipAreas to add...");
            return;
        }

        shippingMethodFreeShipAreaMapper.addShippingMethodFreeShipAreas(shippingMethodFreeShipAreas);
    }

    @Override
    public List<PmsShippingMethodFreeshipArea> queryByMethodId(long methodId) {
        log.debug("queryByMethodId and methodId:{}", methodId);

        List<PmsShippingMethodFreeshipArea> shippingMethodFreeShipAreas = shippingMethodFreeShipAreaMapper.queryByMethodId(methodId);

        if (CollectionUtils.isEmpty(shippingMethodFreeShipAreas)) {
            return shippingMethodFreeShipAreas;
        }

         shippingMethodFreeShipAreas.stream().forEach(shippingMethodFreeShipArea -> shippingMethodFreeShipArea.setCity(areaService.queryCityById(shippingMethodFreeShipArea.getCityId())));

        return shippingMethodFreeShipAreas;
    }

    @Override
    public void deleteByTemplateId(long templateId) {
        log.debug("deleteByTemplateId and templateId:{}", templateId);
        shippingMethodFreeShipAreaMapper.deleteByTemplateId(templateId);
    }

    /**
     * 查询运费方式包邮关联的区域
     *
     * @param id 运费方式包邮关联的区域ID
     * @return 运费方式包邮关联的区域
     */
    @Override
    public PmsShippingMethodFreeshipArea selectPmsShippingMethodFreeshipAreaById(Long id) {
        return pmsShippingMethodFreeshipAreaMapper.selectPmsShippingMethodFreeshipAreaById(id);
    }

    /**
     * 查询运费方式包邮关联的区域列表
     *
     * @param pmsShippingMethodFreeshipArea 运费方式包邮关联的区域
     * @return 运费方式包邮关联的区域
     */
    @Override
    public List<PmsShippingMethodFreeshipArea> selectPmsShippingMethodFreeshipAreaList(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        return pmsShippingMethodFreeshipAreaMapper.selectPmsShippingMethodFreeshipAreaList(pmsShippingMethodFreeshipArea);
    }

    /**
     * 新增运费方式包邮关联的区域
     *
     * @param pmsShippingMethodFreeshipArea 运费方式包邮关联的区域
     * @return 结果
     */
    @Override
    public int insertPmsShippingMethodFreeshipArea(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        return pmsShippingMethodFreeshipAreaMapper.insertPmsShippingMethodFreeshipArea(pmsShippingMethodFreeshipArea);
    }

    /**
     * 修改运费方式包邮关联的区域
     *
     * @param pmsShippingMethodFreeshipArea 运费方式包邮关联的区域
     * @return 结果
     */
    @Override
    public int updatePmsShippingMethodFreeshipArea(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        return pmsShippingMethodFreeshipAreaMapper.updatePmsShippingMethodFreeshipArea(pmsShippingMethodFreeshipArea);
    }

    /**
     * 批量删除运费方式包邮关联的区域
     *
     * @param ids 需要删除的运费方式包邮关联的区域ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodFreeshipAreaByIds(Long[] ids) {
        return pmsShippingMethodFreeshipAreaMapper.deletePmsShippingMethodFreeshipAreaByIds(ids);
    }

    /**
     * 删除运费方式包邮关联的区域信息
     *
     * @param id 运费方式包邮关联的区域ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodFreeshipAreaById(Long id) {
        return pmsShippingMethodFreeshipAreaMapper.deletePmsShippingMethodFreeshipAreaById(id);
    }
}
