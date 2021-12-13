package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsSpec;
import com.ruoyi.goods.domain.PmsSpecValue;

import java.util.List;

/**
 * 规格值Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IPmsSpecValueService {
    /**
     * 查询规格值
     *
     * @param id 规格值ID
     * @return 规格值
     */
    public PmsSpecValue selectPmsSpecValueById(String id);

    /**
     * 查询规格值列表
     *
     * @param pmsSpecValue 规格值
     * @return 规格值集合
     */
    public List<PmsSpecValue> selectPmsSpecValueList(PmsSpecValue pmsSpecValue);

    /**
     * 新增规格值
     *
     * @param pmsSpecValue 规格值
     * @return 结果
     */
    public int insertPmsSpecValue(PmsSpecValue pmsSpecValue);

    /**
     * 修改规格值
     *
     * @param pmsSpecValue 规格值
     * @return 结果
     */
    public int updatePmsSpecValue(PmsSpecValue pmsSpecValue);

    /**
     * 批量删除规格值
     *
     * @param ids 需要删除的规格值ID
     * @return 结果
     */
    public int deletePmsSpecValueByIds(String[] ids);

    /**
     * 删除规格值信息
     *
     * @param id 规格值ID
     * @return 结果
     */
    public int deletePmsSpecValueById(String id);

    /**
     * 添加规格值
     *
     * @param specValue 规格值信息
     * @return 成功返回1 失败返回0
     */
    String addSpecValue(PmsSpecValue specValue);

    /**
     * 新增规格值
     *
     * @param specValues 规格值集合
     */
    void addSpecValues(List<PmsSpecValue> specValues);

    /**
     * 根据规格id 查询规格值信息
     *
     * @param specId 规格id
     * @return 返回规格值信息集合
     */
    List<PmsSpecValue> querySpecValuesBySpecId(long specId);

    /**
     * 删除规格值
     *
     * @param specValue 规格信息
     */
    void deleteBySpecId(PmsSpecValue specValue);

    /**
     * 修改规格值
     *
     * @param spec 规格信息
     */
    void updateSpecValues(PmsSpec spec);

    /**
     * 判断规格值是否可以删除
     *
     * @param specValueId 规格值id
     * @return 可以返回true  不可以返回false
     */
    boolean isSpecValueCanDelete(String specValueId);
}
