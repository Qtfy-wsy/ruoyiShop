package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsSpecValue;

import java.util.List;

/**
 * 规格值Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsSpecValueMapper {
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
     * 删除规格值
     *
     * @param id 规格值ID
     * @return 结果
     */
    public int deletePmsSpecValueById(String id);

    /**
     * 批量删除规格值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSpecValueByIds(String[] ids);

    /**
     * 批量新增规格值
     *
     * @param specValues 规格值集合
     */

    void addSpecValues(List<PmsSpecValue> specValues);

    /**
     * 根据规格id查询规格值信息
     *
     * @param specId 规格id
     * @return 返回规格值信息集合
     */

    List<PmsSpecValue> querySpecValuesBySpecId(long specId);

    /**
     * 根据规格id 删除规格值
     *
     * @param specValue 规格信息
     */

    void deleteBySpecId(PmsSpecValue specValue);

    /**
     * 根据规格id 删除规格值 (特别注意 该删除是直接物理删除)
     *
     * @param specId 规格id
     */

    void deleteBySpecIdPhysics(long specId);

    /**
     * 添加规格值
     *
     * @param specValue 规格值信息
     * @return 成功返回 1 失败返回0
     */

    int addSpecValue(PmsSpecValue specValue);
}
