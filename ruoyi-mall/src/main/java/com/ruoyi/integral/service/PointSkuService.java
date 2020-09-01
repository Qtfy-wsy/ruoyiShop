package com.ruoyi.integral.service;

import com.ruoyi.integral.domain.PointSku;
import com.ruoyi.util.PageHelper;

import java.util.List;


/**
 * 积分商品服务接口
 */
public interface PointSkuService {

    /**
     * 增加积分商品
     *
     * @param pointSku 积分商品实体
     * @return 1：成功 0：失败 -1:没有商品编号 -2:商品编号已存在
     */
    int addPointSku(PointSku pointSku);

    /**
     * 更新积分商品
     *
     * @param pointSku 积分商品实体
     * @return 1：成功 0：失败 -1:没有商品编号 -2:商品编号已存在
     */
    int updatePointSku(PointSku pointSku);

    /**
     * 查询积分商品
     *
     * @param pointskuPageHelper 分页帮助类
     * @param cateId             积分商品分类id
     * @param name               商品名
     * @param isFilterStatus     是否根据发布状态过滤，true只查询已发布的商品
     * @return 积分商品集合
     */
    PageHelper<PointSku> queryPointSkus(PageHelper<PointSku> pointskuPageHelper, Long cateId, String name, boolean isFilterStatus);


    /**
     * 根据积分商品id查询积分商品
     *
     * @param id 积分商品id
     * @return 积分商品
     */
    PointSku queryPointSkuById(long id);

    /**
     * 删除积分商品
     *
     * @param id 积分商品id
     * @return 1：成功 0：失败
     */
    int deletePointSku(long id);

    /**
     * 批量删除积分商品
     *
     * @param ids 积分商品id
     * @return >0：成功 0：失败
     */
    int deletePointSkus(Long... ids);

    /**
     * 积分商品减库存
     *
     * @param id  积分商品id
     * @param num 减掉的数量
     * @return 1成功 0失败
     */
    int reducePointSkuStock(long id, int num);

    /**
     * 查找热销积分商品
     *
     * @param num 数量
     */
    List<PointSku> queryHotPointSkus(int num);
}
