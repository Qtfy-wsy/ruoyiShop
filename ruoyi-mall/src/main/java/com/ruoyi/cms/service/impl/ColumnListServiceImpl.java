package com.ruoyi.cms.service.impl;

import com.ruoyi.cms.bean.ColumnList;
import com.ruoyi.cms.mapper.ColumnListMapper;
import com.ruoyi.cms.service.ColumnListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 栏目列表service层实现类
 *
 * @author 魔金商城 on 2017/5/22.
 */
@Service
public class ColumnListServiceImpl implements ColumnListService {
    /**
     * 调试日志
     */
    Logger logger = LoggerFactory.getLogger(ColumnListServiceImpl.class);
    /**
     * 注入栏目列表mapper
     */
    @Autowired
    private ColumnListMapper columnListMapper;

    /**
     * 查询所有栏目列表
     *
     * @return 栏目列表集合
     */
    @Override
    public List<ColumnList> queryColumnList() {
        logger.debug("queryColumnList...");
        List<ColumnList> lists = columnListMapper.queryColumnList();
        List<ColumnList> finalLis = new ArrayList<>();
        lists.forEach(columnList -> {
            if (finalLis.contains(columnList)) {
                return;
            }
            finalLis.add(columnList);
            lists.forEach(columnListSecond -> {
                if (columnList.getId() == columnListSecond.getParentId()) {
                    finalLis.add(columnListSecond);
                }
            });
        });
        return finalLis;
    }

    /**
     * 查询所有一级栏目列表
     *
     * @return 栏目列表集合
     */
    @Override
    public List<ColumnList> queryParentColumnList() {
        logger.debug("queryParentColumnList...");
        return columnListMapper.queryParentColumnList();
    }

    /**
     * 查询所有二级栏目列表
     *
     * @return 栏目列表集合
     */
    @Override
    public List<ColumnList> queryChildColumnList() {
        logger.debug("queryChildColumnList...");
        return columnListMapper.queryChildColumnList();
    }

    /**
     * 添加栏目
     *
     * @param columnList 栏目实体类
     * @return 返回码 1 成功 -1 失败
     */
    @Override
    public int addColumn(ColumnList columnList) {
        logger.debug("addColumn and columnList:{}", columnList);
        return columnListMapper.addColumn(columnList);
    }

    /**
     * 编辑栏目
     *
     * @param columnList 栏目
     * @return 返回码 1成功 -1失败
     */
    @Override
    public int editColumn(ColumnList columnList) {
        logger.debug("editColumn and columnList:{}", columnList);
        if (Objects.isNull(columnList)) {
            logger.error("editColumn error due to columnList is empty");
            return -1;
        }
        return columnListMapper.editColumn(columnList);
    }

    /**
     * 删除栏目
     *
     * @param columnList 栏目
     * @return -1 失败 1成功
     */
    @Override
    public int deleteColumn(ColumnList columnList) {
        logger.debug("deleteColumn and columnList:{}", columnList);
        if (Objects.isNull(columnList)) {
            logger.error("editColumn error due to columnList is empty");
            return -1;
        }
        return columnListMapper.deleteColumn(columnList);
    }
}
