package com.ruoyi.cms.service;

import com.ruoyi.cms.bean.ArticleList;
import com.ruoyi.util.PageHelper;

import java.util.List;


/**
 * 文章信息service层接口
 *
 * @author 伊甸园商城 on 2017/5/22.
 */
public interface ArticleListService {

    /**
     * 分页查询文章信息
     *
     * @param pageHelper   分页工具实体
     * @param title        文章标题
     * @param columnId     栏目id
     * @param releaseState 发布状态
     * @return 文章信息集合
     */
    PageHelper<ArticleList> queryArticleList(PageHelper<ArticleList> pageHelper, String title, long columnId, Long releaseState);

    /**
     * 添加文章
     *
     * @param articleList 文章实体类
     * @return 添加返回码 -1失败 1成功
     */
    int addArticle(ArticleList articleList);

    /**
     * 删除文章列表
     *
     * @param ids 文章id数组
     * @return 删除返回码
     */
    int deleteArticle(long[] ids);

    /**
     * 编辑文章
     *
     * @param articleList 文章实体类
     * @return 编辑返回码
     */
    int editArticle(ArticleList articleList);


    /**
     * 根据文章id查询文章信息
     *
     * @param id 文章主键id
     * @return 文章信息
     */
    ArticleList queryArticleById(long id);

    /**
     * 查询首篇文章
     *
     * @return 文章信息
     */
    ArticleList queryFirstArticle();

    List<ArticleList> queryArticleByCateId(long id);
}
