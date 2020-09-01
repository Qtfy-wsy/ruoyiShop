package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsShippingMethod;
import com.ruoyi.goods.domain.PmsShippingMethodArea;
import com.ruoyi.goods.mapper.PmsShippingMethodMapper;
import com.ruoyi.goods.service.IPmsShippingMethodAreaService;
import com.ruoyi.goods.service.IPmsShippingMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 运费方式Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsShippingMethodServiceImpl implements IPmsShippingMethodService {
    @Autowired
    private PmsShippingMethodMapper pmsShippingMethodMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsShippingMethodServiceImpl.class);

    /**
     * 运费方式数据库接口
     */
    @Autowired
    private PmsShippingMethodMapper shippingMethodMapper;

    /**
     * 运费方式区域服务接口
     */
    @Autowired
    private IPmsShippingMethodAreaService shippingMethodAreaService;

    @Transactional
    @Override
    public void addShippingMethod(List<PmsShippingMethod> shippingMethods) {
        logger.debug("addShippingMethod and shippingMethods:{}", shippingMethods);

        if (CollectionUtils.isEmpty(shippingMethods)) {
            logger.error("no need to addShippingMethod due to shippingMethods is null...");
            return;
        }

        //新增运费方式
        shippingMethodMapper.addShippingMethods(shippingMethods);

        // 新增运费方式的区域
        shippingMethods.stream().forEach(shippingMethod -> {
            shippingMethod.setShippingMethodAreaMethodId();
            shippingMethodAreaService.addShippingMethodAreas(shippingMethod.getShippingMethodAreas());
        });
    }

    @Override
    public List<PmsShippingMethod> queryByTemplateId(long id) {
        logger.debug("queryByTemplateId and id:{}", id);

        List<PmsShippingMethod> shippingMethods = shippingMethodMapper.queryByTemplateId(id);

        if (CollectionUtils.isEmpty(shippingMethods)) {
            return shippingMethods;
        }

        shippingMethods.parallelStream().forEach(shippingMethod -> shippingMethod.setShippingMethodAreas(shippingMethodAreaService.queryByShippingMethodId(shippingMethod.getId())));

        return shippingMethods;
    }

    @Override
    public void deleteByTemplateId(long id) {
        logger.debug("deleteByTemplateId and id:{}", id);

        // 删除运费方式
        shippingMethodMapper.deleteByTemplateId(id);

        // 删除运费方式区域
        shippingMethodAreaService.deleteByTemplateId(id);
    }

    @Transactional
    @Override
    public void updateShippingMethods(List<PmsShippingMethod> shippingMethods, long templateId) {
        logger.debug("updateShippingMethods and shippingMethods:{} \r\n templateId:{}", shippingMethods, templateId);

        // 删除运费方式和运费方式区域
        deleteByTemplateId(templateId);

        // 新增运费方式
        addShippingMethod(shippingMethods);
    }

    @Override
    public PmsShippingMethod queryShippingMethodByCityId(Long templageId, Long cityId) {
        logger.debug("queryShippingMethodByCityId and templageId:{} \r\n cityId:{}", templageId, cityId);


        PmsShippingMethodArea shippingMethodArea = shippingMethodAreaService.queryShippingMethodAreaByTemplateIdAndCityId(templageId, cityId);

        // 如果当前市没有运费方式 则取该运费模版下的默认运费方式,没有传入市id也返回默认运费方式
        if (Objects.isNull(shippingMethodArea)) {
            return shippingMethodMapper.queryDefaultShippingMethod(templageId);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("templateId", templageId);
        params.put("id", shippingMethodArea.getShippingMethodId());

        // 根据区域的运费方式查询运费方式
        return shippingMethodMapper.queryById(params);
    }

    /**
     * 查询运费方式
     *
     * @param id 运费方式ID
     * @return 运费方式
     */
    @Override
    public PmsShippingMethod selectPmsShippingMethodById(Long id) {
        return pmsShippingMethodMapper.selectPmsShippingMethodById(id);
    }

    /**
     * 查询运费方式列表
     *
     * @param pmsShippingMethod 运费方式
     * @return 运费方式
     */
    @Override
    public List<PmsShippingMethod> selectPmsShippingMethodList(PmsShippingMethod pmsShippingMethod) {
        return pmsShippingMethodMapper.selectPmsShippingMethodList(pmsShippingMethod);
    }

    /**
     * 新增运费方式
     *
     * @param pmsShippingMethod 运费方式
     * @return 结果
     */
    @Override
    public int insertPmsShippingMethod(PmsShippingMethod pmsShippingMethod) {
        return pmsShippingMethodMapper.insertPmsShippingMethod(pmsShippingMethod);
    }

    /**
     * 修改运费方式
     *
     * @param pmsShippingMethod 运费方式
     * @return 结果
     */
    @Override
    public int updatePmsShippingMethod(PmsShippingMethod pmsShippingMethod) {
        return pmsShippingMethodMapper.updatePmsShippingMethod(pmsShippingMethod);
    }

    /**
     * 批量删除运费方式
     *
     * @param ids 需要删除的运费方式ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodByIds(Long[] ids) {
        return pmsShippingMethodMapper.deletePmsShippingMethodByIds(ids);
    }

    /**
     * 删除运费方式信息
     *
     * @param id 运费方式ID
     * @return 结果
     */
    @Override
    public int deletePmsShippingMethodById(Long id) {
        return pmsShippingMethodMapper.deletePmsShippingMethodById(id);
    }
}
