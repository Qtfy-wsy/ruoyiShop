package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsLogisticsTemplate;

import java.util.List;
import java.util.Map;

/**
 * 物流模版Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface OmsLogisticsTemplateMapper {
    /**
     * 查询物流模版
     *
     * @param id 物流模版ID
     * @return 物流模版
     */
    public OmsLogisticsTemplate selectOmsLogisticsTemplateById(Long id);

    /**
     * 查询物流模版列表
     *
     * @param omsLogisticsTemplate 物流模版
     * @return 物流模版集合
     */
    public List<OmsLogisticsTemplate> selectOmsLogisticsTemplateList(OmsLogisticsTemplate omsLogisticsTemplate);

    /**
     * 新增物流模版
     *
     * @param omsLogisticsTemplate 物流模版
     * @return 结果
     */
    public int insertOmsLogisticsTemplate(OmsLogisticsTemplate omsLogisticsTemplate);

    /**
     * 修改物流模版
     *
     * @param omsLogisticsTemplate 物流模版
     * @return 结果
     */
    public int updateOmsLogisticsTemplate(OmsLogisticsTemplate omsLogisticsTemplate);

    /**
     * 删除物流模版
     *
     * @param id 物流模版ID
     * @return 结果
     */
    public int deleteOmsLogisticsTemplateById(Long id);

    /**
     * 批量删除物流模版
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsLogisticsTemplateByIds(Long[] ids);

    /**
     * 根据店铺id查询所有的运费模版
     *
     * @param storeId 店铺id
     * @return 返回店铺所有的运费模版
     */

    List<OmsLogisticsTemplate> queryAllTemplates(long storeId);

    /**
     * 将运费模版设置成 不是默认的
     *
     * @param storeId 店铺id
     */

    void setLogisticsTemplateUnDefault(long storeId);

    /**
     * 设置默认运费模版
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int setDefaultLogisticsTemplate(Map<String, Object> params);

    /**
     * 新增物流公司模版
     *
     * @param logisticsTemplate 物流公司模版
     * @return 成功返回1 失败返回0
     */

    int addLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate);

    /**
     * 查询物流模版
     *
     * @param params 查询参数
     * @return 返回物流模版
     */

    OmsLogisticsTemplate queryLogisticsTemplate(Map<String, Object> params);

    /**
     * 删除物流模版
     *
     * @param params 参数
     * @return 成功返回1  失败返回0
     */

    int deleteLogisticsTemplate(Map<String, Object> params);

    /**
     * 更新物流模版信息
     *
     * @param logisticsTemplate 物流模版
     * @return 成功返回1 失败返回0
     */

    int updateLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate);

    /**
     * 查询店铺的默认运费模版
     *
     * @param storeId 店铺id
     * @return 返回店铺的默认运费模版
     */

    OmsLogisticsTemplate queryDefaultLogisticsTemplate(Long storeId);


    /**
     * 根据id查询运费模版
     *
     * @param id 运费模版id
     * @return 返回运费模版
     */

    OmsLogisticsTemplate queryLogisticsTemplateById(long id);

    /**
     * 根据名称查询运费模版
     *
     * @param params 参数
     * @return 返回运费模版
     */

    OmsLogisticsTemplate queryLogisticsTemplateByName(Map<String, Object> params);
}
