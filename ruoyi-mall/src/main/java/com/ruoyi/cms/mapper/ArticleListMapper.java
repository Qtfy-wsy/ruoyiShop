package com.ruoyi.cms.mapper;


import com.ruoyi.cms.bean.ArticleList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 文章信息mapper层
 *
 * @author 魔金商城 on 2017/5/22.
 */
@Repository
public interface ArticleListMapper {

    /**
     * 分页查询文章信息
     *
     * @param params 查询参数
     * @return 文章信息集合
     */

    List<ArticleList> queryArticleList(Map params);

    /**
     * 分页查询文章信息总行数
     *
     * @param params 查询参数
     * @return 行数
     */

    int queryArticleListCount(Map params);

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

    List<ArticleList> queryArticleByCateId(long id);
}
