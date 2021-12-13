package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.CustomerPanicRecord;

import java.util.Map;

/**
 * Created by 伊甸园商城 on 17/12/27.
 * 用户抢购纪录数据库接口
 */
public interface CustomerPanicRecordMapper {

    /**
     * 根据用户id和抢购id查询用户抢购信息
     *
     * @param params 用户id和会员id
     * @return 返回用户抢购纪录
     */

    CustomerPanicRecord queryByCustomerIdAndMarktingId(Map<String, Object> params);

    /**
     * 新增会员抢购纪录
     *
     * @param customerPanicRecord 会员抢购纪录
     * @return 成功返回>0  失败返回0
     */

    int addCustomerPanicRecord(CustomerPanicRecord customerPanicRecord);

    /**
     * 更新会抢购纪录
     *
     * @param params 参数
     * @return 成功返回 1 失败返回0
     */

    int updateCustomerPanicRecord(Map<String, Object> params);

    /**
     * 查询秒杀总的购买数量
     *
     * @param panicbuyId 秒杀id
     * @return 返回秒杀总的购买数量
     */

    int queryPanicUsedCount(long panicbuyId);

}
