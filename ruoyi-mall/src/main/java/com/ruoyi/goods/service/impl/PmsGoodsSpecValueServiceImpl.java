package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsGoodsSpecValue;
import com.ruoyi.goods.mapper.PmsGoodsSpecValueMapper;
import com.ruoyi.goods.service.IPmsGoodsSpecValueService;
import com.ruoyi.goods.service.IPmsSpecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品和规格值的关联Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsGoodsSpecValueServiceImpl implements IPmsGoodsSpecValueService {
    @Autowired
    private PmsGoodsSpecValueMapper pmsGoodsSpecValueMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsGoodsSpecValueServiceImpl.class);

    /**
     * 注入商品规格值信息
     */
    @Autowired
    private PmsGoodsSpecValueMapper spuSpecValueMapper;

    /**
     * 注入规格值服务接口
     */
    @Autowired
    private IPmsSpecService specService;

    @Override
    public void addSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues) {

        logger.debug("addSpuSpeValues and spuSpecValues:{}", spuSpecValues);

        if (CollectionUtils.isEmpty(spuSpecValues)) {
            logger.warn("do not need to addSpuSpeValues");
            return;
        }
        spuSpecValueMapper.addSpuSpecValues(spuSpecValues);
    }

    @Override
    public List<PmsGoodsSpecValue> queryBySpuIdWithSpec(long spuId) {
        logger.debug("queryById and spuId:{}", spuId);
        return spuSpecValueMapper.queryBySpuId(spuId).parallelStream().map(spuSpecValue -> spuSpecValue.setSpec(specService.querySpecById(spuSpecValue.getSpecId()))).collect(Collectors.toList());
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId and spuId:{}", spuId);

        spuSpecValueMapper.deleteBySpuId(spuId);
    }

    @Override
    public void updateSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues, long spuId) {
        logger.debug("updateSpuSpecValues and spuSpecValues:{} \r\n spuId:{}", spuSpecValues, spuId);

        // 删除商品规格值(物理)
        spuSpecValueMapper.deleteBySpuIdPhysical(spuId);

        if (CollectionUtils.isEmpty(spuSpecValues)) {
            logger.warn("not need to updateSpuSpecValues");
            return;
        }

        // 新增商品规格值
        addSpuSpecValues(spuSpecValues);
    }

    @Override
    public int queryCountBySpecId(long specId) {
        logger.debug("queryCountBySpecId and specId:{}", specId);
        return spuSpecValueMapper.queryCountBySpecId(specId);
    }

    @Override
    public int queryCountBySpecValueId(String specValueId) {
        logger.debug("queryCountBySpecValueId and specValueId:{}", specValueId);
        return spuSpecValueMapper.queryCountBySpecValueId(specValueId);
    }

    /**
     * 查询商品和规格值的关联
     *
     * @param id 商品和规格值的关联ID
     * @return 商品和规格值的关联
     */
    @Override
    public PmsGoodsSpecValue selectPmsGoodsSpecValueById(Long id) {
        return pmsGoodsSpecValueMapper.selectPmsGoodsSpecValueById(id);
    }

    /**
     * 查询商品和规格值的关联列表
     *
     * @param pmsGoodsSpecValue 商品和规格值的关联
     * @return 商品和规格值的关联
     */
    @Override
    public List<PmsGoodsSpecValue> selectPmsGoodsSpecValueList(PmsGoodsSpecValue pmsGoodsSpecValue) {
        return pmsGoodsSpecValueMapper.selectPmsGoodsSpecValueList(pmsGoodsSpecValue);
    }

    /**
     * 新增商品和规格值的关联
     *
     * @param pmsGoodsSpecValue 商品和规格值的关联
     * @return 结果
     */
    @Override
    public int insertPmsGoodsSpecValue(PmsGoodsSpecValue pmsGoodsSpecValue) {
        return pmsGoodsSpecValueMapper.insertPmsGoodsSpecValue(pmsGoodsSpecValue);
    }

    /**
     * 修改商品和规格值的关联
     *
     * @param pmsGoodsSpecValue 商品和规格值的关联
     * @return 结果
     */
    @Override
    public int updatePmsGoodsSpecValue(PmsGoodsSpecValue pmsGoodsSpecValue) {
        return pmsGoodsSpecValueMapper.updatePmsGoodsSpecValue(pmsGoodsSpecValue);
    }

    /**
     * 批量删除商品和规格值的关联
     *
     * @param ids 需要删除的商品和规格值的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsSpecValueByIds(Long[] ids) {
        return pmsGoodsSpecValueMapper.deletePmsGoodsSpecValueByIds(ids);
    }

    /**
     * 删除商品和规格值的关联信息
     *
     * @param id 商品和规格值的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsSpecValueById(Long id) {
        return pmsGoodsSpecValueMapper.deletePmsGoodsSpecValueById(id);
    }
}
