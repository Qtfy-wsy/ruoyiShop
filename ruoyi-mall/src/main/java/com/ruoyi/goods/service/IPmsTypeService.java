package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsType;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 商品类型Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsTypeService {
    /**
     * 分页查询类型
     *
     * @param pageHelper 分页帮助类
     * @param name       类型名称
     * @return 返回类型信息
     */
    PageHelper<PmsType> queryTypes(PageHelper<PmsType> pageHelper, String name);

    /**
     * 新增类型
     *
     * @param type 类型
     * @return 成功返回1 失败返回0
     */
    int addType(PmsType type);

    /**
     * 删除类型
     *
     * @param type 类型
     * @return 成功返回1  失败返回0 类型被分类占用返回-1
     */
    int deleteType(PmsType type);

    /**
     * 批量删除类型信息
     *
     * @param types 类型信息
     * @return 成功返回1 失败返回0
     */
    int batchDeleteTypes(List<PmsType> types);

    /**
     * 查询类型详情
     *
     * @param typeId 类型id
     * @return 返回类型详情(次接口返回类型, 属性, 属性值和类型关联的规格信息....)
     */
    PmsType queryTypeDetail(long typeId);

    /**
     * 查询所有的类型信息
     *
     * @return 返回所有的类型信息
     */
    List<PmsType> queryAllType();

    /**
     * 更新类型信息
     *
     * @param type 类型
     * @return 成功返回1  失败返回0
     */
    int updateType(PmsType type);

    /**
     * 查询是否有相同名称的类型
     *
     * @param name 类型名称
     * @return 返回 0 没有同名类型 1 有同名类型
     */
    int queryIsHasTypeByName(String name);

    /**
     * 校验类型是否有商品关联
     *
     * @param id 类型id
     * @return 类型关联的商品数量
     */
    int checkTypeAssociated(long id);

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
     * 批量删除商品类型
     *
     * @param ids 需要删除的商品类型ID
     * @return 结果
     */
    public int deletePmsTypeByIds(Long[] ids);

    /**
     * 删除商品类型信息
     *
     * @param id 商品类型ID
     * @return 结果
     */
    public int deletePmsTypeById(Long id);
}
