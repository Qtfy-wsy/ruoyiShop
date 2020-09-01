package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsServiceSupport;

import java.util.List;
import java.util.Map;

/**
 * 服务支持Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsServiceSupportMapper {
    /**
     * 查询服务支持
     *
     * @param id 服务支持ID
     * @return 服务支持
     */
    public PmsServiceSupport selectPmsServiceSupportById(Long id);

    /**
     * 查询服务支持列表
     *
     * @param pmsServiceSupport 服务支持
     * @return 服务支持集合
     */
    public List<PmsServiceSupport> selectPmsServiceSupportList(PmsServiceSupport pmsServiceSupport);

    /**
     * 新增服务支持
     *
     * @param pmsServiceSupport 服务支持
     * @return 结果
     */
    public int insertPmsServiceSupport(PmsServiceSupport pmsServiceSupport);

    /**
     * 修改服务支持
     *
     * @param pmsServiceSupport 服务支持
     * @return 结果
     */
    public int updatePmsServiceSupport(PmsServiceSupport pmsServiceSupport);

    /**
     * 删除服务支持
     *
     * @param id 服务支持ID
     * @return 结果
     */
    public int deletePmsServiceSupportById(Long id);

    /**
     * 批量删除服务支持
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsServiceSupportByIds(Long[] ids);

    /**
     * 分页查询服务支持
     *
     * @param params 查询参数
     * @return 返回服务支持
     */

    List<PmsServiceSupport> queryServiceSupport(Map<String, Object> params);

    /**
     * 查询帮助列表的总记录数
     *
     * @param params 查询参数
     * @return 返回服务支持总记录数
     */

    int queryServiceSupportCount(Map<String, Object> params);

    /**
     * 添加服务支持
     *
     * @param serviceSupport 服务支持
     * @return 成功返回1，失败返回0
     */

    int addServiceSupport(PmsServiceSupport serviceSupport);

    /**
     * 删除服务支持
     *
     * @param id 服务支持id
     * @return 成功返回1  失败返回0
     */

    int deleteServiceSupport(long id);

    /**
     * 批量删除服务支持
     *
     * @param ids 服务支持id数组
     * @return 成功返回1  失败返回0
     */

    int batchDeleteServiceSupport(long[] ids);

    /**
     * 通过id查询服务支持
     *
     * @param id 服务支持id
     * @return 返回服务支持
     */

    PmsServiceSupport queryServiceSupportById(long id);

    /**
     * 修改服务支持
     *
     * @param serviceSupport 服务支持
     * @return 成功返回1，失败返回0
     */

    int updateServiceSupport(PmsServiceSupport serviceSupport);

    /**
     * 查询所有服务支持
     *
     * @return 返回所有服务支持
     */

    List<PmsServiceSupport> queryAllServiceSupport();
}
