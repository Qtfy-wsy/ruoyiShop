package com.ruoyi.cms.mapper;

import com.ruoyi.cms.bean.ColumnList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 栏目列表mapper层
 *
 * @author 魔金商城 on 2017/5/22.
 */
@Repository
public interface ColumnListMapper {

    /**
     * 查询所有栏目列表
     *
     * @return 栏目列表集合
     */

    List<ColumnList> queryColumnList();

    /**
     * 查询所有一级栏目列表
     *
     * @return 栏目列表集合
     */

    List<ColumnList> queryParentColumnList();

    /**
     * 查询所有二级栏目列表
     *
     * @return 栏目列表集合
     */

    List<ColumnList> queryChildColumnList();

    /**
     * 添加栏目
     *
     * @param columnList 栏目实体类
     * @return 返回码 1 成功 -1 失败
     */

    int addColumn(ColumnList columnList);

    /**
     * 编辑栏目
     *
     * @param columnList 栏目
     * @return 返回码 1成功
     */

    int editColumn(ColumnList columnList);

    /**
     * 删除栏目
     *
     * @param columnList 栏目
     * @return 返回码 1成功
     */

    int deleteColumn(ColumnList columnList);
}
