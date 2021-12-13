package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsSkuSpecValue;
import com.ruoyi.goods.mapper.PmsSkuSpecValueMapper;
import com.ruoyi.goods.service.IPmsSkuSpecValueService;
import com.ruoyi.goods.service.IPmsSpecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 单品的规格值Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsSkuSpecValueServiceImpl implements IPmsSkuSpecValueService {
    @Autowired
    private PmsSkuSpecValueMapper pmsSkuSpecValueMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsSkuSpecValueServiceImpl.class);

    /**
     * 单品规格值数据库接口
     */
    @Autowired
    private PmsSkuSpecValueMapper skuSpecValueMapper;

    /**
     * 注入规格服务接口
     */
    @Autowired
    private IPmsSpecService specService;

    @Override
    public void addSkuSpecValues(List<PmsSkuSpecValue> skuSpecValues) {

        logger.debug("addSkuSpecValues and skuSpecValues:{}", skuSpecValues);

        if (CollectionUtils.isEmpty(skuSpecValues)) {
            logger.warn("addSkuSpecValues not need...");
            return;
        }

        skuSpecValueMapper.addSkuSpecValues(skuSpecValues);
    }

    @Override
    public List<PmsSkuSpecValue> queryBySkuId(String skuId) {
        logger.debug("queryBySkuId and skuId:{}", skuId);
        return skuSpecValueMapper.queryBySkuId(skuId).stream().map(skuSpecValue -> {
            skuSpecValue.setSpec(specService.selectPmsSpecById(skuSpecValue.getSpecId()));
            return skuSpecValue;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId andspuId:{}", spuId);
        skuSpecValueMapper.deleteBySpuId(spuId);
    }

    @Override
    public void deleteBySpuIdPhysical(long spuId) {
        logger.debug("deleteBySpuIdPhysical and spuId:{}", spuId);

        skuSpecValueMapper.deleteBySpuIdPhysical(spuId);
    }

    @Override
    public List<PmsSkuSpecValue> queryBySpuId(long spuId) {
        logger.debug("queryBySpuId and spuId:{}", spuId);
        return skuSpecValueMapper.queryBySpuId(spuId);
    }

    /**
     * 查询单品的规格值
     *
     * @param id 单品的规格值ID
     * @return 单品的规格值
     */
    @Override
    public PmsSkuSpecValue selectPmsSkuSpecValueById(Long id) {
        return pmsSkuSpecValueMapper.selectPmsSkuSpecValueById(id);
    }

    /**
     * 查询单品的规格值列表
     *
     * @param pmsSkuSpecValue 单品的规格值
     * @return 单品的规格值
     */
    @Override
    public List<PmsSkuSpecValue> selectPmsSkuSpecValueList(PmsSkuSpecValue pmsSkuSpecValue) {
        return pmsSkuSpecValueMapper.selectPmsSkuSpecValueList(pmsSkuSpecValue);
    }

    /**
     * 新增单品的规格值
     *
     * @param pmsSkuSpecValue 单品的规格值
     * @return 结果
     */
    @Override
    public int insertPmsSkuSpecValue(PmsSkuSpecValue pmsSkuSpecValue) {
        return pmsSkuSpecValueMapper.insertPmsSkuSpecValue(pmsSkuSpecValue);
    }

    /**
     * 修改单品的规格值
     *
     * @param pmsSkuSpecValue 单品的规格值
     * @return 结果
     */
    @Override
    public int updatePmsSkuSpecValue(PmsSkuSpecValue pmsSkuSpecValue) {
        return pmsSkuSpecValueMapper.updatePmsSkuSpecValue(pmsSkuSpecValue);
    }

    /**
     * 批量删除单品的规格值
     *
     * @param ids 需要删除的单品的规格值ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuSpecValueByIds(Long[] ids) {
        return pmsSkuSpecValueMapper.deletePmsSkuSpecValueByIds(ids);
    }

    /**
     * 删除单品的规格值信息
     *
     * @param id 单品的规格值ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuSpecValueById(Long id) {
        return pmsSkuSpecValueMapper.deletePmsSkuSpecValueById(id);
    }
}
