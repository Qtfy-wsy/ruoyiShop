package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsSpec;
import com.ruoyi.goods.domain.PmsSpecValue;
import com.ruoyi.goods.mapper.PmsSpecValueMapper;
import com.ruoyi.goods.service.IPmsGoodsSpecValueService;
import com.ruoyi.goods.service.IPmsSpecValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * 规格值Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsSpecValueServiceImpl implements IPmsSpecValueService {
    @Autowired
    private PmsSpecValueMapper pmsSpecValueMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsSpecValueServiceImpl.class);

    /**
     * 注入规格值数据库接口
     */
    @Autowired
    private PmsSpecValueMapper specValueMapper;

    /**
     * 注入商品规格值服务接口
     */
    @Autowired
    private IPmsGoodsSpecValueService spuSpecValueService;

    @Override
    public String addSpecValue(PmsSpecValue specValue) {
        logger.debug("addSpecValue and specValue:{}", specValue);

        if (Objects.isNull(specValue)) {
            logger.error("addSpecValue fail due to specValue is null...");
            return "";
        }
        specValueMapper.addSpecValue(specValue);
        return specValue.getId();
    }

    @Override
    public void addSpecValues(List<PmsSpecValue> specValues) {
        logger.debug("addSpecValues and specValues {}", specValues);

        if (CollectionUtils.isEmpty(specValues)) {
            logger.error("addSpecValues fail due to params is empty....");
            return;
        }

        // 设置规格值的主键id
        setSpecValueId(specValues);

        specValueMapper.addSpecValues(specValues);
    }


    @Override
    public List<PmsSpecValue> querySpecValuesBySpecId(long specId) {
        logger.debug("querySpecValuesBySpecId and specId:{}", specId);
        return specValueMapper.querySpecValuesBySpecId(specId);
    }

    @Override
    public void deleteBySpecId(PmsSpecValue specValue) {
        logger.debug("deleteBySpecId and specValue : {}", specValue);
        specValueMapper.deleteBySpecId(specValue);
    }

    @Override
    public void updateSpecValues(PmsSpec spec) {

        logger.debug("updateSpecValues and spec : {}", spec);

        // 第一步 根据规格id删除规格值信息
        specValueMapper.deleteBySpecIdPhysics(spec.getId());

        if (CollectionUtils.isEmpty(spec.getSpecValues())) {
            logger.error("updateSpecValues fail due to specValues is empty....");
            return;
        }

        // 第二步 新增规格值信息
        setSpecValueId(spec.getSpecValues());

        specValueMapper.addSpecValues(spec.getSpecValues());
    }

    @Override
    public boolean isSpecValueCanDelete(String specValueId) {
        logger.debug("isSpecValueCanDelete and specValueId:{}", specValueId);

        return spuSpecValueService.queryCountBySpecValueId(specValueId) == 0;
    }

    /**
     * 设置规格值的主键id
     *
     * @param specValues 规格值
     */
    private void setSpecValueId(List<PmsSpecValue> specValues) {
        IntStream.range(0, specValues.size()).forEach(index -> specValues.get(index).setCustomId(index));
    }

    /**
     * 查询规格值
     *
     * @param id 规格值ID
     * @return 规格值
     */
    @Override
    public PmsSpecValue selectPmsSpecValueById(String id) {
        return pmsSpecValueMapper.selectPmsSpecValueById(id);
    }

    /**
     * 查询规格值列表
     *
     * @param pmsSpecValue 规格值
     * @return 规格值
     */
    @Override
    public List<PmsSpecValue> selectPmsSpecValueList(PmsSpecValue pmsSpecValue) {
        return pmsSpecValueMapper.selectPmsSpecValueList(pmsSpecValue);
    }

    /**
     * 新增规格值
     *
     * @param pmsSpecValue 规格值
     * @return 结果
     */
    @Override
    public int insertPmsSpecValue(PmsSpecValue pmsSpecValue) {
        pmsSpecValue.setCreateTime(DateUtils.getNowDate());
        return pmsSpecValueMapper.insertPmsSpecValue(pmsSpecValue);
    }

    /**
     * 修改规格值
     *
     * @param pmsSpecValue 规格值
     * @return 结果
     */
    @Override
    public int updatePmsSpecValue(PmsSpecValue pmsSpecValue) {
        return pmsSpecValueMapper.updatePmsSpecValue(pmsSpecValue);
    }

    /**
     * 批量删除规格值
     *
     * @param ids 需要删除的规格值ID
     * @return 结果
     */
    @Override
    public int deletePmsSpecValueByIds(String[] ids) {
        return pmsSpecValueMapper.deletePmsSpecValueByIds(ids);
    }

    /**
     * 删除规格值信息
     *
     * @param id 规格值ID
     * @return 结果
     */
    @Override
    public int deletePmsSpecValueById(String id) {
        return pmsSpecValueMapper.deletePmsSpecValueById(id);
    }
}
