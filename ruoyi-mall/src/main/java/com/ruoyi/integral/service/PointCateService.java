package com.ruoyi.integral.service;


import com.ruoyi.integral.domain.PointCate;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 魔金商城 on 18/1/11.
 * 积分商城分类服务接口
 */
public interface PointCateService {

    /**
     * 分页查询积分商城分类
     *
     * @param pageHelper 分页帮助类
     * @param name       积分商城分类名称
     * @return 返回促销分类信息
     */
    PageHelper queryPointCates(PageHelper<PointCate> pageHelper, String name);

    /**
     * 根据积分商城分类id查询积分商城分类
     *
     * @param id 积分商城分类id
     * @return 返回积分商城分类信息
     */
    PointCate queryPointCateById(long id);

    /**
     * 查找所有积分商品分类
     */
    List<PointCate> queryAllPointCates();

    /**
     * 新增积分商城分类
     *
     * @param pointCate 积分商城分类信息
     * @return 成功返回1 失败返回0
     */
    int addPointCate(PointCate pointCate);

    /**
     * 修改积分商城分类
     *
     * @param pointCate 积分商城分类信息
     * @return 成功返回1 失败返回0
     */
    int updatePointCate(PointCate pointCate);

    /**
     * 删除积分商城分类
     *
     * @param id 积分商城分类id
     * @return 成功返回1 失败返回0
     */
    int deletePointCate(long id);

    /**
     * 批量删除积分商城分类
     *
     * @param ids 积分商城分类id数组
     * @return 成功返回1 失败返回0
     */
    int deletePointCates(long[] ids);
}
