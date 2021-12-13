package com.ruoyi.util;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/5/2.
 * 分页帮助类
 */

public class PageHelper<T> {

    /**
     * 当前查询的列表数据
     */
    private List<T> list = new ArrayList<>();

    /**
     * 总计录数
     */
    private int rows;

    /**
     * 每页显示记录数 默认10条记录
     */
    private int pageSize = 10;

    /**
     * 当前页码  默认第一页
     */
    private int pageNum = 0;

    /**
     * 构造分页对象
     *
     * @param pageHelper 原始分页对象
     * @param function   转化函数
     * @return 返回新的返回对象
     */
    public static PageHelper buildByCopy(PageHelper pageHelper, Function function) {
        PageHelper pageHelper1 = new PageHelper();
        pageHelper1.setRows(pageHelper.getRows());
        pageHelper1.setPageNum(pageHelper.getPageNum());
        pageHelper1.setPageSize(pageHelper.getPageSize());

        if (!CollectionUtils.isEmpty(pageHelper.getList())) {
            pageHelper1.setList((List) pageHelper.getList().stream().map(function).collect(Collectors.toList()));
        }
        return pageHelper1;
    }

    /**
     * 获取开始页
     *
     * @return 返回开始页
     */
    public int getStartRowNum() {
        return pageNum * pageSize;
    }

    /**
     * 设置当前的数据列表
     *
     * @param list 当前数据
     * @return 返回当前对象
     */
    public PageHelper setListDates(List<T> list) {
        this.list = list;
        return this;
    }

    /**
     * 获得设置通用分页
     *
     * @param params 查询参数
     * @param rows   总记录数
     * @return 返回查询参数
     */
    public Map<String, Object> getQueryParams(Map<String, Object> params, int rows) {
        this.setRows(rows);
        params.put(CommonConstant.START_ROW_NUM, this.getStartRowNum());
        params.put(CommonConstant.PAGE_SIZE, this.getPageSize());
        return params;
    }

    /**
     * 获得总页数
     *
     * @return 返回总的页数 此方法不能删除
     */
    public int getTotalPages() {
        return rows % pageSize == 0 ? rows / pageSize : rows / pageSize + 1;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
