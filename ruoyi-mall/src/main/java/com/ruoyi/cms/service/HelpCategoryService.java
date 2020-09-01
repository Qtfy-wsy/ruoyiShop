package com.ruoyi.cms.service;

import com.ruoyi.cms.bean.HelpCategory;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 魔金商城 on 2017/5/23.
 * 帮助分类接口
 */
public interface HelpCategoryService {

    /**
     * 分页查询帮助分类
     *
     * @param pageHelper 分页帮助类
     * @param name       帮助分类名称
     * @return           返回帮助分类数据
     */
    PageHelper<HelpCategory> queryHelpCategory(PageHelper<HelpCategory> pageHelper, String name);

    /**
     * 添加帮助分类
     *
     * @param helpCategory 帮助分类
     * @return             返回 0 失败，1 成功
     */
    int addHelpCategory(HelpCategory helpCategory);

    /**
     * 删除帮助分类
     *
     * @param id 帮助分类id
     * @return   成功返回1  失败返回0
     */
    int deleteHelpCategory(long id);

    /**
     * 批量删除帮助分类
     *
     * @param ids 帮助分类id数组
     * @return    成功返回1  失败返回0
     */
    int batchDeleteHelpCategory(long [] ids);

    /**
     * 修改帮助分类
     *
     * @param helpCategory 帮助分类
     * @return             成功返回1，失败返回0
     */
    int updateHelpCategory(HelpCategory helpCategory);

    /**
     * 通过id查找帮助分类
     *
     * @param id 帮助分类id
     * @return   返回帮助分类
     */
    HelpCategory queryHelpCategoryById(long id);

    /**
     * 查找帮助分类
     *
     * @return 返回帮助分类集合
     */
    List<HelpCategory> queryHelpAllCate();

}
