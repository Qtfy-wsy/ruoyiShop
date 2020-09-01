package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsAttribute;
import com.ruoyi.goods.domain.PmsAttributeValue;
import com.ruoyi.goods.domain.PmsType;

import java.util.List;
import java.util.Map;

/**
 * 商品类型Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsTypeMapper {
    /**
     * 查询类型总数
     *
     * @param params 查询 参数
     * @return 返回类型总数
     */

    int queryTypesCount(Map<String, Object> params);

    /**
     * 分页查询类型信息
     *
     * @param params 查询参数
     * @return 返回类型信息
     */

    List<PmsType> queryTypes(Map<String, Object> params);

    /**
     * 新增商品类型
     *
     * @param type 商品类型
     * @return 成功返回1  失败返回0
     */

    int addType(PmsType type);

    /**
     * 新增属性
     *
     * @param attributes 属性集合
     */

    void addAttributes(List<PmsAttribute> attributes);

    /**
     * 新增属性值
     *
     * @param attributeValues 属性值集合
     */

    void addAttributeValues(List<PmsAttributeValue> attributeValues);

    /**
     * 删除类型
     *
     * @param type 类型实体
     * @return 成功返回1  失败返回0
     */

    int deleteType(PmsType type);

    /**
     * 根据类型id删除属性
     *
     * @param typeId 类型id
     */

    void deleteAttributes(long typeId);

    /**
     * 根据类型id删除属性值
     *
     * @param typeId 类型id
     */

    void deleteAttributeValues(long typeId);

    /**
     * 根据id查询类型
     *
     * @param id 类型id
     * @return 返回类型信息
     */

    PmsType queryTypeById(long id);


    /**
     * 根据类型id查询属性信息
     *
     * @param typeId 类型id
     * @return 返回类型的属性
     */

    List<PmsAttribute> queryAttributesByTypeId(long typeId);

    /**
     * 根据属性id查询属性值
     *
     * @param id 属性id
     * @return 返回属性的属性值
     */

    List<PmsAttributeValue> queryAttributeValueByAttributeId(String id);

    /**
     * 查询所有的类型信息
     *
     * @return 返回所有的类型信息
     */

    List<PmsType> queryAllType();

    /**
     * 更新类型
     *
     * @param type 类型信息
     * @return 成功返回1  失败返回0
     */

    int updateType(PmsType type);

    /**
     * 根据类型id删除属性(物理删除)
     *
     * @param typeId 类型id
     */

    void deleteAttributesByTypeIdPhysical(long typeId);

    /**
     * 根据类型id 删除属性值(物理删除)
     *
     * @param typeId 类型id
     */

    void deleteAttributeValuesByTypeIdPhysical(long typeId);

    /**
     * 查询是否有相同名称的类型
     *
     * @param name 类型名称
     * @return 返回 0 没有同名类型 >0 有同名类型
     */

    int queryIsHasTypeByName(String name);

    /**
     * 查询商品类型
     *
     * @param id 商品类型ID
     * @return 商品类型
     */
    public PmsType selectPmsTypeById(Long id);

    /**
     * 查询商品类型列表
     *
     * @param pmsType 商品类型
     * @return 商品类型集合
     */
    public List<PmsType> selectPmsTypeList(PmsType pmsType);

    /**
     * 新增商品类型
     *
     * @param pmsType 商品类型
     * @return 结果
     */
    public int insertPmsType(PmsType pmsType);

    /**
     * 修改商品类型
     *
     * @param pmsType 商品类型
     * @return 结果
     */
    public int updatePmsType(PmsType pmsType);

    /**
     * 删除商品类型
     *
     * @param id 商品类型ID
     * @return 结果
     */
    public int deletePmsTypeById(Long id);

    /**
     * 批量删除商品类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsTypeByIds(Long[] ids);
}
