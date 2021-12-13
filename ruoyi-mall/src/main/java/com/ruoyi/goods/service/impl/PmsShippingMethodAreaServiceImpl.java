package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsShippingMethodArea;
import com.ruoyi.goods.mapper.PmsShippingMethodAreaMapper;
import com.ruoyi.goods.service.IPmsShippingMethodAreaService;
import com.ruoyi.setting.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费方式关联的区域Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsShippingMethodAreaServiceImpl implements IPmsShippingMethodAreaService {
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsShippingMethodAreaServiceImpl.class);

    /**
     * 注入运费方式区域数据库接口
     */
    @Autowired
    private PmsShippingMethodAreaMapper shippingMethodAreaMapper;

    /**
     * 注入区域服务接口
     */
    @Autowired
    private AreaService areaService;

    @Autowired
    private PmsShippingMethodAreaMapper pmsShippingMethodAreaMapper;

    @Override
    public void addShippingMethodAreas(List<PmsShippingMethodArea> shippingMethodAreas) {
        logger.debug("addShippingMethodAreas and shippingMethodAreas:{}", shippingMethodAreas);

        if (CollectionUtils.isEmpty(shippingMethodAreas)) {
            logger.error("no need to addShippingMethodAreas .....");
            return;
        }

        shippingMethodAreaMapper.addShippingMethodAreas(shippingMethodAreas);
    }

    @Override
    public List<PmsShippingMethodArea> queryByShippingMethodId(long id) {
        logger.debug("queryByShippingMethodId and id:{}", id);

        List<PmsShippingMethodArea> shippingMethodAreas = shippingMethodAreaMapper.queryByShippingMethodId(id);

        if (CollectionUtils.isEmpty(shippingMethodAreas)) {
            return shippingMethodAreas;
        }

        shippingMethodAreas.stream().forEach(shippingMethodArea -> shippingMethodArea.setCity(areaService.queryCityById(shippingMethodArea.getCityId())));

        return shippingMethodAreas;
    }

    @Override
    public void deleteByTemplateId(long id) {
        logger.debug("deleteByTemplateId and id:{}", id);
        shippingMethodAreaMapper.deleteByTemplateId(id);
    }

    @Override
    public PmsShippingMethodArea queryShippingMethodAreaByTemplateIdAndCityId(Long templateId, Long cityId) {
        logger.debug("queryShippingMethodAreaByTemplateIdAndCityId and templateId:{} \r\n cityId:{}", templateId, cityId);
        Map<String, Object> params = new HashMap<>();
        params.put("templateId", templateId);
        params.put("cityId", cityId);
        return shippingMethodAreaMapper.queryShippingMethodAreaByTemplateIdAndCityId(params);
    }

    /**
     * 查询运费方式关联的区域
     *
     * @param id 运费方式关联的区域ID
     * @return 运费方式关联的区域
     */
    @Override
    public PmsShippingMethodArea selectPmsShippingMethodAreaById(Long id) {
        return pmsShippingMethodAreaMapper.selectPmsShippingMethodAreaById(id);
    }

    /**
     * 查询运费方式关联的区域列表
     *
     * @param pmsShippingMethodArea 运费方式关联的区域
     * @return 运费方式关联的区域
     */
    @Override
    public List<PmsShippingMethodArea> selectPmsShippingMethodAreaList(PmsShippingMethodArea pmsShippingMethodArea) {
        return pmsShippingMethodAreaMapper.selectPmsShippingMethodAreaList(pmsShippingMethodArea);
    }

    /**
     * 新增运费方式关联的区域
     *
     * @param pmsShippingMethodArea 运费方式关联的区域
     * @return 结果
     */
    @Override
    public int insertPmsShippingMethodArea(PmsShippingMethodArea pmsShippingMethodArea) {
        return pmsShippingMethodAreaMapper.insertPmsShippingMethodArea(pmsShippingMethodArea);
    }

    /**
     * 修改运费方式关联的区域
     *
     * @param pmsShippingMethodArea 运费方式关联的区域
     * @return 结果
     */
    @Override
    public int updatePmsShippingMethodArea(PmsShippingMethodArea pmsShippingMethodArea) {
        return pmsShippingMethodAreaMapper.updatePmsShippingMethodArea(pmsShippingMethodArea);
    }

    /**
     * 批量删除运费方式关联的区域
     *
     * @param ids 需要删除的运费方式关联的区域ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodAreaByIds(Long[] ids) {
        return pmsShippingMethodAreaMapper.deletePmsShippingMethodAreaByIds(ids);
    }

    /**
     * 删除运费方式关联的区域信息
     *
     * @param id 运费方式关联的区域ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodAreaById(Long id) {
        return pmsShippingMethodAreaMapper.deletePmsShippingMethodAreaById(id);
    }
}
