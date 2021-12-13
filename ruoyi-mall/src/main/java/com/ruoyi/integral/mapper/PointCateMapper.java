package com.ruoyi.integral.mapper;


import com.ruoyi.integral.domain.PointCate;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 18/1/11.
 * 积分商城分类数据库接口
 */
public interface PointCateMapper {

    /**
     * 查询积分商城分类的总记录数
     *
     * @param params 查询参数
     * @return 返回积分商城分类总记录数
     */

    int queryPointCateCount(Map<String, Object> params);

    /**
     * 分页查询积分商城分类
     *
     * @param params 查询参数
     * @return 返回积分商城分类信息
     */

    List<PointCate> queryPointCates(Map<String, Object> params);


    /**
     * 查询全部积分商城分类
     *
     * @return 返回全部积分商城分类信息
     */

    List<PointCate> queryAllPointCates();

    /**
     * 根据积分商城分类id查询积分商城分类
     *
     * @param id 积分商城分类id
     * @return 返回积分商城分类信息
     */

    PointCate queryPointCateById(long id);

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

}
