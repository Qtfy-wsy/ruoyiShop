package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsSpec;

import java.util.List;
import java.util.Map;

/**
 * 规格Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface PmsSpecMapper {
    /**
     * 查询规格
     *
     * @param id 规格ID
     * @return 规格
     */
    public PmsSpec selectPmsSpecById(Long id);

    /**
     * 查询规格列表
     *
     * @param pmsSpec 规格
     * @return 规格集合
     */
    public List<PmsSpec> selectPmsSpecList(PmsSpec pmsSpec);

    /**
     * 新增规格
     *
     * @param pmsSpec 规格
     * @return 结果
     */
    public int insertPmsSpec(PmsSpec pmsSpec);

    /**
     * 修改规格
     *
     * @param pmsSpec 规格
     * @return 结果
     */
    public int updatePmsSpec(PmsSpec pmsSpec);

    /**
     * 删除规格
     *
     * @param id 规格ID
     * @return 结果
     */
    public int deletePmsSpecById(Long id);

    /**
     * 批量删除规格
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSpecByIds(Long[] ids);

    /**
     * 新增规格
     *
     * @param spec 规格
     * @return 成功返回  失败返回0
     */

    int addSpec(PmsSpec spec);

    /**
     * 查询规格
     *
     * @param params 查询参数
     * @return 返回规格信息
     */

    List<PmsSpec> querySpecs(Map<String, Object> params);

    /**
     * 查询规格总数
     *
     * @param params 查询参数
     * @return 返回规格总数
     */

    int querySpecsCount(Map<String, Object> params);

    /**
     * 根据规格id查询规格信息
     *
     * @param id 主键id
     * @return 返回规格信息
     */

    PmsSpec querySpecById(long id);

    /**
     * 删除规格
     *
     * @param spec 规格信息
     * @return 成功返回1  失败返回0
     */

    int deleteSpec(PmsSpec spec);

    /**
     * 修改规格信心
     *
     * @param spec 规格信息
     * @return 成功返回1  失败返回0
     */

    int updateSpec(PmsSpec spec);

    /**
     * 查询所有规格信息
     *
     * @return 返回所有规格信息
     */

    List<PmsSpec> queryAllSpec();
}
