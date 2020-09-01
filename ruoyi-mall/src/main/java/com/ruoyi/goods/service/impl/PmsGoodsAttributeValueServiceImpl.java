package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsGoodsAttributeValue;
import com.ruoyi.goods.mapper.PmsGoodsAttributeValueMapper;
import com.ruoyi.goods.service.IPmsGoodsAttributeValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 商品下面的属性值Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsGoodsAttributeValueServiceImpl implements IPmsGoodsAttributeValueService {
    @Autowired
    private PmsGoodsAttributeValueMapper pmsGoodsAttributeValueMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsGoodsAttributeValueServiceImpl.class);

    /**
     * 注入商品属性值数据库接口
     */
    @Autowired
    private PmsGoodsAttributeValueMapper spuAttributeValueMapper;

    @Override
    public void addSpuAttributeValues(List<PmsGoodsAttributeValue> spuAttributeValues) {
        logger.debug("addSpuAttributeValues and spuAttributeValues:{}", spuAttributeValues);

        if (CollectionUtils.isEmpty(spuAttributeValues)) {
            logger.warn("addSpuAttributeValues not need...");
            return;
        }
        spuAttributeValueMapper.addSpuAttributeValues(spuAttributeValues);
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId and spuId:{}", spuId);

        spuAttributeValueMapper.deleteBySpuId(spuId);
    }

    @Override
    public List<PmsGoodsAttributeValue> queryBySpuId(long spuId) {
        logger.debug("queryBySpuId and spuId:{}", spuId);
        return spuAttributeValueMapper.queryBySpuId(spuId);
    }

    @Override
    public void updateSpuAttributValues(List<PmsGoodsAttributeValue> spuAttributeValues, long spuId) {
        logger.debug("updateSpuAttributValues and spuAttributeValues:{} \r\n spuId :{}", spuAttributeValues, spuId);

        // 首先删除商品属性值(物理)
        spuAttributeValueMapper.deleteBySpuIdPhysical(spuId);

        if (CollectionUtils.isEmpty(spuAttributeValues)) {
            logger.warn("not need to updateSpuAttributValues");
            return;
        }

        // 新增商品属性值
        addSpuAttributeValues(spuAttributeValues);
    }

    /**
     * 查询商品下面的属性值
     *
     * @param id 商品下面的属性值ID
     * @return 商品下面的属性值
     */
    @Override
    public PmsGoodsAttributeValue selectPmsGoodsAttributeValueById(Long id) {
        return pmsGoodsAttributeValueMapper.selectPmsGoodsAttributeValueById(id);
    }

    /**
     * 查询商品下面的属性值列表
     *
     * @param pmsGoodsAttributeValue 商品下面的属性值
     * @return 商品下面的属性值
     */
    @Override
    public List<PmsGoodsAttributeValue> selectPmsGoodsAttributeValueList(PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        return pmsGoodsAttributeValueMapper.selectPmsGoodsAttributeValueList(pmsGoodsAttributeValue);
    }

    /**
     * 新增商品下面的属性值
     *
     * @param pmsGoodsAttributeValue 商品下面的属性值
     * @return 结果
     */
    @Override
    public int insertPmsGoodsAttributeValue(PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        return pmsGoodsAttributeValueMapper.insertPmsGoodsAttributeValue(pmsGoodsAttributeValue);
    }

    /**
     * 修改商品下面的属性值
     *
     * @param pmsGoodsAttributeValue 商品下面的属性值
     * @return 结果
     */
    @Override
    public int updatePmsGoodsAttributeValue(PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        return pmsGoodsAttributeValueMapper.updatePmsGoodsAttributeValue(pmsGoodsAttributeValue);
    }

    /**
     * 批量删除商品下面的属性值
     *
     * @param ids 需要删除的商品下面的属性值ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsAttributeValueByIds(Long[] ids) {
        return pmsGoodsAttributeValueMapper.deletePmsGoodsAttributeValueByIds(ids);
    }

    /**
     * 删除商品下面的属性值信息
     *
     * @param id 商品下面的属性值ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsAttributeValueById(Long id) {
        return pmsGoodsAttributeValueMapper.deletePmsGoodsAttributeValueById(id);
    }
}
