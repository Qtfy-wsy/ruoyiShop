package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.PanicBuy;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 17/6/8.
 * 抢购数据库接口
 */
public interface PanicBuyMapper {

    /**
     * 新增抢购
     *
     * @param panicBuy 抢购实体
     * @return 成功返回1 失败返回0
     */

    int addPanicBuy(PanicBuy panicBuy);

    /**
     * 查询抢购信息
     *
     * @param params 参数
     * @return 返回抢购信息
     */

    List<PanicBuy> queryByMarketingId(Map<String, Object> params);

    /**
     * 更新抢购
     *
     * @param panicBuy 抢购信息
     * @return 成功返回1 失败返回0
     */

    int updatePanicBuy(PanicBuy panicBuy);

    /**
     * 删除抢购
     *
     * @param params 参数
     * @return 成功>0 否则失败
     */

    int deletePanicBuy(Map<String, Object> params);

    /**
     * 查询店铺抢购列表
     *
     * @param params 查询参数
     * @return 抢购列表
     */

    List<PanicBuy> queryStorePanicBuyList(Map<String, Object> params);

    /**
     * 根据店铺id查询抢购列表总记录数
     *
     * @param params 查询参数
     * @return 抢购总记录数
     */

    int queryStorePanicBuyCount(Map<String, Object> params);

    /**
     * 设置抢购促销展示和排序
     *
     * @param params 参数
     * @return 成功1 否则失败
     */

    int updatePanicBuyShow(Map<String, Object> params);


    /**
     * 根据秒杀场次时间查询折扣
     *
     * @param params 参数
     * @return 返回和秒杀场次时间相同的折扣
     */

    List<PanicBuy> queryPanicBuysByTime(Map<String, Object> params);

    /**
     * 根据id查询抢购信息
     *
     * @param id 抢购id
     * @return 返回抢购信息
     */

    PanicBuy queryById(long id);

    /**
     * 查询店铺抢购列表（前端用）
     *
     * @param params 查询参数
     * @return 抢购列表
     */

    List<PanicBuy> queryStorePanicBuyListForSite(Map<String, Object> params);

    /**
     * 根据店铺id查询抢购列表总记录数（前端用）
     *
     * @param params 查询参数
     * @return 抢购总记录数
     */

    int queryStorePanicBuyCountForSite(Map<String, Object> params);

    /**
     * 根据抢购id集合删除抢购
     *
     * @param params 参数
     * @return 成功>0 否则失败
     */

    int deletePanicBuyByIds(Map<String, Object> params);

}
