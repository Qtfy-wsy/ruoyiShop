package com.ruoyi.cms.service.impl;

import com.ruoyi.cms.bean.ArticleList;
import com.ruoyi.cms.bean.ColumnList;
import com.ruoyi.cms.mapper.ArticleListMapper;
import com.ruoyi.cms.service.ArticleListService;
import com.ruoyi.cms.service.ColumnListService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 文章列表service实现类
 *
 * @author 伊甸园商城 on 2017/5/22.
 */
@Service
public class ArticleListServiceImpl implements ArticleListService {

    /**
     * 调试日志
     */
    Logger logger = LoggerFactory.getLogger(ArticleListServiceImpl.class);

    /**
     * 注入文章信息mapper
     */
    @Autowired
    private ArticleListMapper articleListMapper;

    @Autowired
    private ColumnListService columnListService;

    /**
     * 分页查询文章信息
     *
     * @return 文章信息集合
     */
    @Override
    public PageHelper<ArticleList> queryArticleList(PageHelper<ArticleList> pageHelper, String title, long columnId, Long releaseState) {
        logger.debug("queryArticleList and pageHelper : {} \r\n title: {} \r\n columnId:{} \r\n  releaseState:{}", pageHelper, title, columnId, releaseState);
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("columnId", columnId);
        params.put("releaseState", releaseState);
        return pageHelper.setListDates(articleListMapper.queryArticleList(pageHelper.getQueryParams(params, articleListMapper.queryArticleListCount(params))));
    }

    /**
     * 添加文章
     *
     * @param articleList 文章实体类
     * @return 添加返回码 -1失败 1成功
     */
    @Override
    public int addArticle(ArticleList articleList) {
        logger.debug("addArticle and articleList : {}", articleList);
        if (Objects.isNull(articleList)) {
            logger.error("addArticle error due to articleList is null...");
            return -1;
        }
        return articleListMapper.addArticle(articleList);
    }

    /**
     * 删除文章列表
     *
     * @param ids 文章id数组
     * @return 删除返回码 -1 删除失败 >=1删除成功
     */
    @Override
    public int deleteArticle(long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deleteArticleList error due to ids is empty...");
            return -1;
        }
        return articleListMapper.deleteArticle(ids);
    }

    /**
     * 编辑文章
     *
     * @param articleList 文章实体类
     * @return 编辑返回码 -1失败 1成功
     */
    @Override
    public int editArticle(ArticleList articleList) {
        logger.debug("addArticle and articleList : {}", articleList);
        if (Objects.isNull(articleList)) {
            logger.error("editArticle error due to articleList is null...");
            return -1;
        }
        return articleListMapper.editArticle(articleList);
    }

    /**
     * 根据文章id查询文章信息
     *
     * @param id 文章主键id
     * @return 文章信息
     */
    @Override
    public ArticleList queryArticleById(long id) {
        logger.debug("queryArticleById and id : {}", id);
        return articleListMapper.queryArticleById(id);
    }

    @Override
    public  List<ArticleList> queryArticleByCateId(long id){
        return articleListMapper.queryArticleByCateId(id);
    }
    /**
     * 查询首篇文章
     */
    @Override
    public ArticleList queryFirstArticle() {
        logger.debug("queryFirstArticle ");
        List<ColumnList> lists = columnListService.queryColumnList();
        List<ColumnList> finalColumn = new ArrayList<>();
        lists.forEach(columnList -> {
            if (!columnList.isParent()) {
                finalColumn.add(columnList);
                return;
            }
        });
        if (CollectionUtils.isEmpty(finalColumn)) {
            logger.error("queryFirstArticle fail:no second column");
            return null;
        }
        PageHelper<ArticleList> pageHelper = queryArticleList(new PageHelper(), null, finalColumn.get(0).getId(), CommonConstant.QUERY_WITH_ISRELEASE);
        if (CollectionUtils.isEmpty(pageHelper.getList())) {
            logger.error("queryFirstArticle fail:no article");
            return null;
        }
        return pageHelper.getList().get(0);
    }
}
