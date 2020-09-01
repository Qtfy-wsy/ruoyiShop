package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsServiceSupport;
import com.ruoyi.goods.mapper.PmsServiceSupportMapper;
import com.ruoyi.goods.service.IPmsServiceSupportService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务支持Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsServiceSupportServiceImpl implements IPmsServiceSupportService {
    @Autowired
    private PmsServiceSupportMapper pmsServiceSupportMapper;

    /**
     * 注入服务支持mapper
     */
    @Autowired
    private PmsServiceSupportMapper serviceSupportMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsServiceSupportServiceImpl.class);

    /**
     * 分页查询服务支持
     *
     * @param pageHelper 分页帮助类
     * @param name       服务支持名称
     * @return 返回服务支持
     */
    @Override
    public PageHelper<PmsServiceSupport> queryServiceSupport(PageHelper<PmsServiceSupport> pageHelper, String name) {
        logger.debug("queryServiceSupport and pageHelper :{} \r\n name :{}", pageHelper, name);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return pageHelper.setListDates(serviceSupportMapper.queryServiceSupport(pageHelper.getQueryParams(params, serviceSupportMapper.queryServiceSupportCount(params))));
    }


    /**
     * 添加服务支持
     *
     * @param serviceSupport 服务支持
     * @return 成功返回1，失败返回0
     */
    @Override
    public int addServiceSupport(PmsServiceSupport serviceSupport) {
        logger.debug("addServiceSupport and serviceSupport :{}", serviceSupport);
        return serviceSupportMapper.addServiceSupport(serviceSupport);
    }

    /**
     * 删除服务支持
     *
     * @param id 服务支持id
     * @return 成功返回1  失败返回0
     */
    @Override
    public int deleteServiceSupport(long id) {
        logger.debug("deleteServiceSupport and id :{}", id);
        return serviceSupportMapper.deleteServiceSupport(id);
    }

    /**
     * 批量删除服务支持
     *
     * @param ids 服务支持id数组
     * @return 成功返回1  失败返回0
     */
    @Override
    public int batchDeleteServiceSupport(long[] ids) {
        logger.debug("batchDeleteServiceSupport and ids :{}", ids);
        return serviceSupportMapper.batchDeleteServiceSupport(ids);
    }

    /**
     * 通过id查询服务支持
     *
     * @param id 服务支持id
     * @return 返回服务支持
     */
    @Override
    public PmsServiceSupport queryServiceSupportById(long id) {
        logger.debug("queryServiceSupportById and id :{}", id);
        return serviceSupportMapper.queryServiceSupportById(id);
    }

    /**
     * 修改服务支持
     *
     * @param serviceSupport 服务支持
     * @return 成功返回1，失败返回0
     */
    @Override
    public int updateServiceSupport(PmsServiceSupport serviceSupport) {
        logger.debug("updateServiceSupport and serviceSupport :{}", serviceSupport);
        return serviceSupportMapper.updateServiceSupport(serviceSupport);
    }

    /**
     * 查询所有服务支持
     *
     * @return 返回所有服务支持
     */
    @Override
    public List<PmsServiceSupport> queryAllServiceSupport() {
        logger.debug("queryAllServiceSupport .....");
        return serviceSupportMapper.queryAllServiceSupport();
    }

    /**
     * 查询服务支持
     *
     * @param id 服务支持ID
     * @return 服务支持
     */
    @Override
    public PmsServiceSupport selectPmsServiceSupportById(Long id) {
        return pmsServiceSupportMapper.selectPmsServiceSupportById(id);
    }

    /**
     * 查询服务支持列表
     *
     * @param pmsServiceSupport 服务支持
     * @return 服务支持
     */
    @Override
    public List<PmsServiceSupport> selectPmsServiceSupportList(PmsServiceSupport pmsServiceSupport) {
        return pmsServiceSupportMapper.selectPmsServiceSupportList(pmsServiceSupport);
    }

    /**
     * 新增服务支持
     *
     * @param pmsServiceSupport 服务支持
     * @return 结果
     */
    @Override
    public int insertPmsServiceSupport(PmsServiceSupport pmsServiceSupport) {
        pmsServiceSupport.setCreateTime(DateUtils.getNowDate());
        return pmsServiceSupportMapper.insertPmsServiceSupport(pmsServiceSupport);
    }

    /**
     * 修改服务支持
     *
     * @param pmsServiceSupport 服务支持
     * @return 结果
     */
    @Override
    public int updatePmsServiceSupport(PmsServiceSupport pmsServiceSupport) {
        return pmsServiceSupportMapper.updatePmsServiceSupport(pmsServiceSupport);
    }

    /**
     * 批量删除服务支持
     *
     * @param ids 需要删除的服务支持ID
     * @return 结果
     */
    @Override
    public int deletePmsServiceSupportByIds(Long[] ids) {
        return pmsServiceSupportMapper.deletePmsServiceSupportByIds(ids);
    }

    /**
     * 删除服务支持信息
     *
     * @param id 服务支持ID
     * @return 结果
     */
    @Override
    public int deletePmsServiceSupportById(Long id) {
        return pmsServiceSupportMapper.deletePmsServiceSupportById(id);
    }
}
