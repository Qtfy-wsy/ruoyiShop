package com.ruoyi.cms.service.impl;

import com.ruoyi.cms.bean.HelpCategory;
import com.ruoyi.cms.bean.HelpList;
import com.ruoyi.cms.mapper.HelpListMapper;
import com.ruoyi.cms.service.HelpCategoryService;
import com.ruoyi.cms.service.HelpListService;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助列表service实现类
 *
 * Created by 伊甸园商城 on 2017/5/27.
 */
@Service
public class HelpListServiceImpl implements HelpListService {

    /**
     * 注入帮助mapper
     */
    @Autowired
    private HelpListMapper helpListMapper;

    /**
     * 自动注入帮助分类service
     */
    @Autowired
    private HelpCategoryService helpCategoryService;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(HelpListServiceImpl.class);

    /**
     * 分页查询帮助列表
     *
     * @param pageHelper 分页帮助类
     * @param name       帮助名称
     * @return           返回帮助数据
     */
    @Override
    public PageHelper<HelpList> queryHelpList(PageHelper<HelpList> pageHelper, String name) {
        logger.debug("queryHelp and pageHelper :{} \r\n name :{}", pageHelper, name);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return pageHelper.setListDates(helpListMapper.queryHelpList(pageHelper.getQueryParams(params, helpListMapper.queryHelpListCount(params))));
    }

    /**
     * 查找帮助分类
     *
     * @return 返回帮助分类集合
     */
    @Override
    public List<HelpCategory> queryHelpCate() {
        logger.debug("queryHelpCate...");
        return helpCategoryService.queryHelpAllCate();
    }

    /**
     * 添加帮助
     *
     * @param helpList 帮助
     * @return         成功返回1，失败返回0
     */
    @Override
    public int addHelp(HelpList helpList) {
        logger.debug("addHelp and helpList :{}", helpList);
        return helpListMapper.addHelp(helpList);
    }

    /**
     * 删除帮助
     *
     * @param id 帮助id
     * @return   成功返回1  失败返回0
     */
    @Override
    public int deleteHelp(long id) {
        logger.debug("deleteHelp and id :{}", id);
        return helpListMapper.deleteHelp(id);
    }

    /**
     * 批量删除帮助
     *
     * @param ids 帮助id数组
     * @return    成功返回1  失败返回0
     */
    @Transactional
    @Override
    public int batchDeleteHelp(long[] ids) {
        logger.debug("batchDeleteHelp and ids :{}", ids);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("batchDeleteHelpfail due to ids is null...");
            return 0;
        }
        return helpListMapper.batchDeleteHelp(ids);
    }

    /**
     * 修改帮助
     *
     * @param helpList  帮助
     * @return         成功返回1，失败返回0
     */
    @Override
    public int updateHelp(HelpList helpList) {
        logger.debug("updateHelp and helpList :{}",helpList);
        return helpListMapper.updateHelp(helpList);
    }

    /**
     * 通过id查找帮助
     *
     * @param id 帮助id
     * @return   帮助
     */
    @Override
    public HelpList queryHelpById(long id) {
        logger.debug("queryHelpById and id :{}", id);
        return helpListMapper.queryHelpById(id);
    }

    /**
     * 查找所有服务
     *
     * @return 返回服务集合
     */
    @Override
    public List<HelpList> queryHelp() {
        logger.debug("queryHelp...");
        return helpListMapper.queryHelp();
    }

    /**
     * 根据帮助分类id查找帮助
     *
     * @param id 帮助分类id
     * @return   帮助
     */
    @Override
    public List<HelpList> queryHelpByCateId(long id) {
        logger.debug("queryHelpByCateId and id :{}", id);
        return helpListMapper.queryHelpByCateId(id);
    }
}
