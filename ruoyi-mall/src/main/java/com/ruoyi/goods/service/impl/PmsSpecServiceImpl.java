package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.goods.domain.PmsGoodsSpecValue;
import com.ruoyi.goods.domain.PmsSpec;
import com.ruoyi.goods.domain.PmsSpecValue;
import com.ruoyi.goods.mapper.PmsSpecMapper;
import com.ruoyi.goods.service.IPmsGoodsSpecValueService;
import com.ruoyi.goods.service.IPmsSpecService;
import com.ruoyi.goods.service.IPmsSpecValueService;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 规格Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsSpecServiceImpl implements IPmsSpecService {
    @Autowired
    private PmsSpecMapper pmsSpecMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsSpecServiceImpl.class);

    /**
     * 注入规格数据库接口
     */
    @Autowired
    private PmsSpecMapper specMapper;

    /**
     * 注入规格值服务接口
     */
    @Autowired
    private IPmsSpecValueService specValueService;

    /**
     * 注入商品规格值服务
     */
    @Autowired
    private IPmsGoodsSpecValueService spuSpecValueService;

    @Transactional
    @Override
    public int addSpec(PmsSpec spec) {
        logger.debug("addSpec and  spec {}", spec);

        // 首先增加规格主表
        specMapper.addSpec(spec);

        logger.debug("addSpec success and begin to add specvalue....");

        // 新增规格值表
        addSpecValues(spec);

        return 1;
    }

    @Override
    public PageHelper<PmsSpec> querySpecs(PageHelper<PmsSpec> pageHelper, String name) {
        logger.debug("querySpecs and pageHelper :{} \r\n name : {}", pageHelper, name);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return pageHelper.setListDates(specMapper.querySpecs(pageHelper.getQueryParams(params, specMapper.querySpecsCount(params))));
    }

    @Override
    public PmsSpec querySpecById(long id) {
        logger.debug("querySpecById and id : {}", id);

        PmsSpec spec = specMapper.querySpecById(id);

        spec.setSpecValues(specValueService.querySpecValuesBySpecId(spec.getId()));

        return spec;
    }

    @Override
    public PmsSpec querySpecByIdSimple(long id) {
        logger.debug("querySpecByIdSimple and id:{}", id);
        return specMapper.querySpecById(id);
    }

    @Override
    public int deleteSpec(PmsSpec spec) {
        logger.debug("deleteSpec and spec  : {}", spec);

        // 判断规格是否可以删除 (如果被商品关联了则不能删除)
        if (!isCanDelete(spec)) {
            logger.error("deleteSpec fail due to spec:{} has spus...", spec);
            return -1;
        }

        // 删除规格
        specMapper.deleteSpec(spec);

        // 删除规格值
        specValueService.deleteBySpecId(PmsSpecValue.buildForDelete(spec.getId(), spec.getDelName()));

        return 1;
    }


    @Override
    public int deleteSpecs(List<PmsSpec> specs) {
        logger.debug("deleteSpecs and specs : {}", specs);

        if (CollectionUtils.isEmpty(specs)) {
            logger.error("deleteSpecs fail due to specs is empty....");
            return 0;
        }

        // 判断规格是否可以删除
        if (specs.stream().map(this::isCanDelete).filter(Boolean::booleanValue).count() != specs.size()) {
            logger.error("deleteSpecs fail due to specs has goods...");
            return -1;
        }


        specs.stream().forEach(this::deleteSpec);

        return 1;
    }

    @Transactional
    @Override
    public int updateSpec(PmsSpec spec) {
        logger.debug("updateSpec and spec : {}", spec);

        // 判断规格是否可以更改 (如果被商品关联了，则只能新增规格值不能修改规格值)
        if (!isCanUpdateSepc(spec)) {
            logger.error("updateSpec fail due to spec:{} has spus...", spec);
            return -1;
        }

        // 修改规格信息
        specMapper.updateSpec(spec);

        // 修改规格值信息
        specValueService.updateSpecValues(spec);

        return 1;
    }


    /**
     * 判断规格是否可以更改
     * 只能更改没有被商品关联的规格值和规格，被商品关联的规格值和规格不能操作
     *
     * @param spec 规格信息
     * @return 可以返回true  不能返回false
     */
    private boolean isCanUpdateSepc(PmsSpec spec) {
        logger.debug("isCanUpdateSepc and spec:{}", spec);

        // 没有被商品关联过可以修改规格和规格值
        if (isCanDelete(spec)) {
            return true;
        }

        // 首先根据规格id查询数据库中旧的规格信息
        PmsSpec oldSpec = this.querySpecById(spec.getId());

        //  关联了商品不能更改规格名称和别名
        if (oldSpec.hasNameChange(spec)) {
            return false;
        }


        Map<String, Boolean> result = new HashMap<>();
        result.put("isCanUpdate", true);


        // 判断规格值是否可以操作（规格值关联了商品就不能操作）
        IteratorUtils.zip(spec.getSpecValues().stream().filter(PmsSpecValue::hasId).collect(Collectors.toList()), oldSpec.getSpecValues(), (specValue, specValue2) -> specValue.getId().equals(specValue2.getId()), (newSpecValue, oldSpecValue) -> {
            // 判断规格值是否关联了商品 如果关联了商品则判断规格值是否发生了变化
            if (!specValueService.isSpecValueCanDelete(newSpecValue.getId()) && newSpecValue.hasChange(oldSpecValue)) {
                result.put("isCanUpdate", false);
            }
        });

        return result.get("isCanUpdate");
    }


    @Override
    public List<PmsSpec> queryAllSpec() {
        logger.debug("queryAllSpec ....");
        return specMapper.queryAllSpec();
    }

    @Override
    public List<PmsSpec> querySpecsByIds(Long[] ids) {
        logger.debug("querySpecsByIds and ids :{}", ids);
        if (ArrayUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return Arrays.stream(ids).map(this::querySpecById).collect(Collectors.toList());
    }

    @Override
    public List<PmsSpec> querySpuSpecs(long spuId) {
        logger.debug("querySpuSpecs and spuId:{}", spuId);
        return convertSpuSpecValueToSpec(spuSpecValueService.queryBySpuIdWithSpec(spuId));
    }

    /**
     * 将商品规格值转化成商品规格
     *
     * @param spuSpecValues 商品规格值
     * @return 返回商品的所有规格
     */
    private List<PmsSpec> convertSpuSpecValueToSpec(List<PmsGoodsSpecValue> spuSpecValues) {
        if (CollectionUtils.isEmpty(spuSpecValues)) {
            return Collections.emptyList();
        }

        List<PmsSpec> specs = new ArrayList<>();

        spuSpecValues.stream().forEach(spuSpecValue -> {
            if (specs.stream().filter(spec -> spec.getId() == spuSpecValue.getSpecId()).count() == 0) {
                PmsSpec spec1 = new PmsSpec();
                spec1.setId(spuSpecValue.getSpec().getId());
                spec1.setName(spuSpecValue.getSpec().getName());
                spec1.setSpecValues(new ArrayList<>());
                specs.add(spec1);
            }

            specs.stream().filter(spec -> spec.getId() == spuSpecValue.getSpecId()).findFirst().ifPresent(
                    spec1 -> {
                        PmsSpecValue specValue = new PmsSpecValue();
                        specValue.setName(spuSpecValue.getValueRemark());
                        specValue.setSpecId(spuSpecValue.getSpecId());
                        specValue.setId(spuSpecValue.getSpecValueId());
                        specValue.setUrl(spuSpecValue.getUrl());
                        spec1.getSpecValues().add(specValue);
                    }
            );
        });

        return specs;

    }


    /**
     * 新增规格值
     *
     * @param spec 规格
     */
    private void addSpecValues(PmsSpec spec) {

        if (CollectionUtils.isEmpty(spec.getSpecValues())) {
            logger.warn("do not need to addSpecValues due to values is empty....");
            return;
        }

        setSpecId(spec.getId(), spec.getSpecValues());

        // 新增规格值信息
        specValueService.addSpecValues(spec.getSpecValues());

    }

    /**
     * 给规格值设置规格id
     *
     * @param specId 规格id
     */
    private void setSpecId(long specId, List<PmsSpecValue> specValues) {
        specValues.stream().forEach(specValue -> specValue.setSpecId(specId));
    }


    /**
     * 判断规格是否可以删除
     *
     * @param spec 规格信息
     * @return 可以返回true  不可以返回false
     */
    private boolean isCanDelete(PmsSpec spec) {
        return spuSpecValueService.queryCountBySpecId(spec.getId()) == 0;
    }


    /**
     * 查询规格
     *
     * @param id 规格ID
     * @return 规格
     */
    @Override
    public PmsSpec selectPmsSpecById(Long id) {
        PmsSpec spec = pmsSpecMapper.selectPmsSpecById(id);
        spec.setSpecValues(specValueService.querySpecValuesBySpecId(spec.getId()));
        return spec;
    }

    /**
     * 查询规格列表
     *
     * @param pmsSpec 规格
     * @return 规格
     */
    @Override
    public List<PmsSpec> selectPmsSpecList(PmsSpec pmsSpec) {
        return pmsSpecMapper.selectPmsSpecList(pmsSpec);
    }

    /**
     * 新增规格
     *
     * @param pmsSpec 规格
     * @return 结果
     */
    @Override
    public int insertPmsSpec(PmsSpec pmsSpec) {
        pmsSpec.setCreateTime(DateUtils.getNowDate());
        pmsSpecMapper.insertPmsSpec(pmsSpec);
        // 新增规格值表
        addSpecValues(pmsSpec);
        return 1;
    }

    /**
     * 修改规格
     *
     * @param pmsSpec 规格
     * @return 结果
     */
    @Override
    public int updatePmsSpec(PmsSpec pmsSpec) {
        return pmsSpecMapper.updatePmsSpec(pmsSpec);
    }

    /**
     * 批量删除规格
     *
     * @param ids 需要删除的规格ID
     * @return 结果
     */
    @Override
    public int deletePmsSpecByIds(Long[] ids) {
        return pmsSpecMapper.deletePmsSpecByIds(ids);
    }

    /**
     * 删除规格信息
     *
     * @param id 规格ID
     * @return 结果
     */
    @Override
    public int deletePmsSpecById(Long id) {
        return pmsSpecMapper.deletePmsSpecById(id);
    }
}
