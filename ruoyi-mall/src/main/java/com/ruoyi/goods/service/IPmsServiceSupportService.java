package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsServiceSupport;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 服务支持Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IPmsServiceSupportService {
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
     * 批量删除服务支持
     *
     * @param ids 需要删除的服务支持ID
     * @return 结果
     */
    public int deletePmsServiceSupportByIds(Long[] ids);

    /**
     * 删除服务支持信息
     *
     * @param id 服务支持ID
     * @return 结果
     */
    public int deletePmsServiceSupportById(Long id);

    /**
     * 分页查询服务支持
     *
     * @param pageHelper 分页帮助类
     * @param name       服务支持名称
     * @return 返回服务支持
     */
    PageHelper<PmsServiceSupport> queryServiceSupport(PageHelper<PmsServiceSupport> pageHelper, String name);

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
     * 查询所有的服务支持
     *
     * @return 查询所有服务支持
     */
    List<PmsServiceSupport> queryAllServiceSupport();
}
