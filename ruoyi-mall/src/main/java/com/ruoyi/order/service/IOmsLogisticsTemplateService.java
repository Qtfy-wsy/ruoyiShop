package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsLogisticsTemplate;

import java.util.List;
import java.util.Set;

/**
 * 物流模版Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IOmsLogisticsTemplateService {
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
     * 批量删除物流模版
     *
     * @param ids 需要删除的物流模版ID
     * @return 结果
     */
    public int deleteOmsLogisticsTemplateByIds(Long[] ids);

    /**
     * 删除物流模版信息
     *
     * @param id 物流模版ID
     * @return 结果
     */
    public int deleteOmsLogisticsTemplateById(Long id);


    /**
     * 根据店铺id查询所有的运费模版
     *
     * @param storeId 店铺id
     * @return 返回店铺所有的运费模版
     */
    List<OmsLogisticsTemplate> queryAllTemplates(long storeId);

    /**
     * 设置默认模版
     *
     * @param id      模版id
     * @param storeId 店铺id
     * @return 成功返回1 失败返回0
     */
    int setDefaultTemplate(long id, long storeId);

    /**
     * 新增运费模版
     *
     * @param logisticsTemplate 运费模版
     * @return 成功返回1 失败返回0 -1 参数为空 -2 名称已存在
     */
    int addLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate);

    /**
     * 根据id查询物流模版(包含运费方式和运费方式区域)
     *
     * @param id      物流模版id
     * @param storeId 店铺id
     * @return 返回物流模版
     */
    OmsLogisticsTemplate queryLogisticsTemplate(long id, long storeId);

    /**
     * 删除运费模版
     *
     * @param id      运费模版id
     * @param storeId 店铺id
     * @return 成功返回1 失败返回0
     */
    int deleteLogisticsTemplate(long id, long storeId);

    /**
     * 修改物流模版
     *
     * @param logisticsTemplate 物流模版
     * @return 成功返回1 失败返回0 -1 参数为空 -2 名称已存在
     */
    int updateLogisticsTemplate(OmsLogisticsTemplate logisticsTemplate);


    /**
     * 根据用户的市id查询运费模版
     *
     * @param storeId 店铺id
     * @param cityId  市id
     * @return 返回运费模版(包含运费方式)
     */
    OmsLogisticsTemplate queryLogisticsTemplateByCityId(Long storeId, Long cityId);

    /**
     * 根据id和市区id查询运费模版
     *
     * @param ids    运费模版id集合
     * @param cityId 市id
     * @return 返回运费模版集合
     */
    List<OmsLogisticsTemplate> queryLogisticsTemplateByCityIdAndId(Set<Long> ids, Long cityId);

    /**
     * 查询店铺的默认运费模版
     *
     * @param storeId 店铺id
     * @return 返回店铺的默认运费模版
     */
    OmsLogisticsTemplate queryDefaultLogisticsTemplate(Long storeId);
}
