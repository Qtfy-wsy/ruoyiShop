package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsAttribute;
import com.ruoyi.goods.domain.PmsType;
import com.ruoyi.goods.mapper.PmsTypeMapper;
import com.ruoyi.goods.service.IPmsCategoryService;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.goods.service.IPmsTypeService;
import com.ruoyi.util.PageHelper;
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
import java.util.stream.Collectors;

/**
 * 商品类型Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsTypeServiceImpl implements IPmsTypeService {
    @Autowired
    private PmsTypeMapper pmsTypeMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsTypeServiceImpl.class);

    /**
     * 注入类型数据库接口
     */
    @Autowired
    private PmsTypeMapper typeMapper;

    /**
     * 注入分类服务接口
     */
    @Autowired
    private IPmsCategoryService categoryService;

    /**
     * 注入商品服务
     */
    @Autowired
    private IPmsGoodsService spuService;

    @Override
    public PageHelper<PmsType> queryTypes(PageHelper<PmsType> pageHelper, String name) {
        logger.debug("queryTypes and pageHelper : {} \r\n name : {} ", pageHelper, name);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return pageHelper.setListDates(typeMapper.queryTypes(pageHelper.getQueryParams(params, typeMapper.queryTypesCount(params))));
    }

    @Transactional
    @Override
    public int addType(PmsType type) {

        logger.debug("addType and : {} ", type);

        //校验是否已经有同名类型
        if (queryIsHasTypeByName(type.getName()) == 1) {
            logger.error("addType fail due to has same name type");
            return 0;
        }

        // 新增类型主表
        typeMapper.addType(type);

        // 设置属性的类型id
        type.setAttributesTypeId();

        // 新增属性
        addAttributes(type.getAttributes());

        return 1;
    }

    @Transactional
    @Override
    public int deleteType(PmsType type) {
        logger.debug("deleteType and type : {}", type);

        if (Objects.isNull(type)) {
            logger.error("deleteType fail due to type is null...");
            return 0;
        }

        // 类型被分类关联
        if (!CollectionUtils.isEmpty(categoryService.queryThirdCategoryByTypeId(type.getId()))) {
            logger.error("deleteType fail due to categoryMapper.queryThirdCategoryByTypeId(type.getId()) is not null...");
            return -1;
        }

        // 删除类型表
        typeMapper.deleteType(type);

        // 删除属性表
        typeMapper.deleteAttributes(type.getId());

        // 删除属性值表
        typeMapper.deleteAttributeValues(type.getId());
        return 1;
    }

    @Transactional
    @Override
    public int batchDeleteTypes(List<PmsType> types) {

        logger.debug("batchDeleteTypes and types : {}", types);

        if (CollectionUtils.isEmpty(types)) {
            logger.error("batchDeleteTypes fail due to types is empty.... ");
            return 0;
        }

        types.stream().forEach(this::deleteType);

        return 1;
    }

    @Override
    public PmsType queryTypeDetail(long typeId) {
        logger.debug("queryTypeDetail and typeId :{}", typeId);

        // 查询类型信息
        PmsType type = typeMapper.queryTypeById(typeId);

        if (Objects.isNull(type)) {
            logger.error("queryTypeDetail fail due to type is not exist....");
            return type;
        }

        // 查询类型关联的属性
        type.setAttributes(getAttributes(typeId));


        return type;
    }

    @Override
    public List<PmsType> queryAllType() {
        logger.debug("queryAllType ......");
        return typeMapper.queryAllType();
    }

    @Transactional
    @Override
    public int updateType(PmsType type) {
        logger.debug("updateType and type:{}", type);

        if (Objects.isNull(type)) {
            logger.error("updateType fail due to type is null....");
            return -1;
        }

        //校验是否和自己原先的类型名称相同
        if (!typeMapper.queryTypeById(type.getId()).getName().equals(type.getName())) {
            //校验是否已经有同名类型
            if (queryIsHasTypeByName(type.getName()) == 1) {
                logger.error("updateType fail due to has same name type");
                return 0;
            }
        }

        // 更新类型信息
        typeMapper.updateType(type);


        // 更新属性
        updateAttributes(type);

        return 1;
    }

    @Override
    public int queryIsHasTypeByName(String name) {
        logger.debug("queryIsHasTypeByName and name :{}", name);

        if (typeMapper.queryIsHasTypeByName(name) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int checkTypeAssociated(long id) {
        logger.debug("checkTypeAssociated and id:{}", id);
        return spuService.querySpuCountByTypeId(id);
    }

    /**
     * 更新属性
     *
     * @param type 类型
     */
    private void updateAttributes(PmsType type) {

        logger.debug("updateAttributes and attributes:{}", type.getAttributes());

        // 根据类型id删除属性
        typeMapper.deleteAttributesByTypeIdPhysical(type.getId());

        // 根据类型id删除属性值
        typeMapper.deleteAttributeValuesByTypeIdPhysical(type.getId());

        if (CollectionUtils.isEmpty(type.getAttributes())) {
            logger.warn("do not need to updateAttributes");
            return;
        }

        //设置属性的类型id
        type.setAttributesTypeId();

        // 新增属性
        addAttributes(type.getAttributes());
    }


    /**
     * 查询类型的属性
     *
     * @param typeId 类型id
     * @return 返回类型的属性
     */
    private List<PmsAttribute> getAttributes(long typeId) {
        return typeMapper.queryAttributesByTypeId(typeId).stream().map(this::setAttributeValues).collect(Collectors.toList());
    }

    /**
     * 设置属性的属性值
     *
     * @param attribute 属性
     */
    private PmsAttribute setAttributeValues(PmsAttribute attribute) {
        attribute.setAttributeValues(typeMapper.queryAttributeValueByAttributeId(attribute.getId()));
        return attribute;
    }


    /**
     * 新增属性
     *
     * @param attributes 属性集合
     */
    private void addAttributes(List<PmsAttribute> attributes) {
        logger.debug("addAttributes and attributes : {} ", attributes);

        if (CollectionUtils.isEmpty(attributes)) {
            logger.error("addAttributes fail due to params is empty....");
            return;
        }

        // 新增属性
        typeMapper.addAttributes(attributes);

        // 添加属性值
        attributes.stream().forEach(this::addAttributeValues);
    }

    /**
     * 新增属性值
     *
     * @param attribute 属性
     */
    private void addAttributeValues(PmsAttribute attribute) {
        logger.debug("addAttributeValues and attributeValues:{}", attribute.getAttributeValues());

        if (CollectionUtils.isEmpty(attribute.getAttributeValues())) {
            logger.error("addAttributeValues fail due to params is empty.....");
            return;
        }

        // 设置属性值的id
        attribute.setAttributeValuesId();

        // 新增属性值
        typeMapper.addAttributeValues(attribute.getAttributeValues());
    }

    /**
     * 查询商品类型
     *
     * @param id 商品类型ID
     * @return 商品类型
     */
    @Override
    public PmsType selectPmsTypeById(Long id) {
        return pmsTypeMapper.selectPmsTypeById(id);
    }

    /**
     * 查询商品类型列表
     *
     * @param pmsType 商品类型
     * @return 商品类型
     */
    @Override
    public List<PmsType> selectPmsTypeList(PmsType pmsType) {
        return pmsTypeMapper.selectPmsTypeList(pmsType);
    }

    /**
     * 新增商品类型
     *
     * @param pmsType 商品类型
     * @return 结果
     */
    @Override
    public int insertPmsType(PmsType pmsType) {
        pmsType.setCreateTime(DateUtils.getNowDate());
        return pmsTypeMapper.insertPmsType(pmsType);
    }

    /**
     * 修改商品类型
     *
     * @param pmsType 商品类型
     * @return 结果
     */
    @Override
    public int updatePmsType(PmsType pmsType) {
        return pmsTypeMapper.updatePmsType(pmsType);
    }

    /**
     * 批量删除商品类型
     *
     * @param ids 需要删除的商品类型ID
     * @return 结果
     */
    @Override
    public int deletePmsTypeByIds(Long[] ids) {
        return pmsTypeMapper.deletePmsTypeByIds(ids);
    }

    /**
     * 删除商品类型信息
     *
     * @param id 商品类型ID
     * @return 结果
     */
    @Override
    public int deletePmsTypeById(Long id) {
        return pmsTypeMapper.deletePmsTypeById(id);
    }
}
