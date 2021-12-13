package com.ruoyi.cms.service.impl;

import com.ruoyi.cms.bean.HelpCategory;
import com.ruoyi.cms.mapper.HelpCategoryMapper;
import com.ruoyi.cms.service.HelpCategoryService;
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
 * 帮助分类service实现类
 *
 * Created by 伊甸园商城 on 2017/5/23.
 */
@Service
public class HelpCategoryServiceImpl implements HelpCategoryService {

    /**
     * 注入帮助分类mapper
     */
    @Autowired
    private HelpCategoryMapper helpCategoryMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(HelpCategoryServiceImpl.class);

    /**
     * 分页查询帮助分类
     *
     * @param pageHelper 分页帮助类
     * @param name       帮助分类名称
     * @return           返回帮助分类集合
     */
    @Override
    public PageHelper<HelpCategory> queryHelpCategory(PageHelper<HelpCategory> pageHelper, String name) {
        logger.debug("queryHelpCategory and pageHelper :{} \r\n name :{} ", pageHelper, name);
        Map<String, Object> params = new HashMap<>();
        params.put("name",name);
        return pageHelper.setListDates(helpCategoryMapper.queryHelpCategory(pageHelper.getQueryParams(params, helpCategoryMapper.queryHelpCategoryCount(params))));
    }

    /**
     * 删除帮助分类
     *
     * @param id 帮助分类id
     * @return   成功返回1  失败返回0
     */
    @Override
    public int deleteHelpCategory(long id) {
        logger.debug("deleteHelpCategory and id :{}", id);
        return helpCategoryMapper.deleteHelpCategory(id);
    }

    /**
     * 批量删除帮助分类
     *
     * @param ids 帮助分类id数组
     * @return    成功返回1  失败返回0
     */
    @Transactional
    @Override
    public int batchDeleteHelpCategory(long[] ids) {
        logger.debug("batchDeleteHelpCategory and ids :{}",ids);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("batchDeleteHelpCategoryfail due to ids is null...");
            return 0;
        }
        return helpCategoryMapper.batchDeleteHelpCategory(ids);
    }

    /**
     * 添加帮助分类
     *
     * @param helpCategory 帮助分类
     * @return             返回 0 失败，1 成功
     */
    @Override
    public int addHelpCategory(HelpCategory helpCategory) {
        logger.debug("addHelpCategory and helpCategory :{}",helpCategory);
        return helpCategoryMapper.addHelpCategory(helpCategory);
    }

    /**
     * 修改帮助分类
     *
     * @param helpCategory 帮助分类
     * @return             成功返回1，失败返回0
     */
    @Override
    public int updateHelpCategory(HelpCategory helpCategory) {
        logger.debug("updateHelpCategory and helpCategory {}",helpCategory);
        return helpCategoryMapper.updateHelpCategory(helpCategory);
    }

    /**
     * 通过id查找帮助分类
     *
     * @param id 帮助分类id
     * @return   返回帮助分类
     */
    @Override
    public HelpCategory queryHelpCategoryById(long id) {
        logger.debug("queryHelpCategoryById and id {}",id);
        return helpCategoryMapper.queryHelpCategoryById(id);
    }

    /**
     * 查找帮助分类
     *
     * @return 返回帮助分类集合
     */
    @Override
    public List<HelpCategory> queryHelpAllCate() {
        logger.debug("queryHelpAllCate...");
        return helpCategoryMapper.queryHelpAllCate();
    }

}
