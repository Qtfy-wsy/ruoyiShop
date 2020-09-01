package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.RegisterMarketing;
import org.springframework.stereotype.Repository;

/**
 * 注册营销数据库接口
 * <p>
 * Created by 魔金商城 on 2017/6/6.
 */
@Repository
public interface RegisterMarketingMapper {

    /**
     * 查找注册营销
     *
     * @return 注册营销
     */

    RegisterMarketing queryRegisterMarketing();

    /**
     * 修改注册营销
     *
     * @param registerMarketing 注册营销
     * @return 成功返回1  失败返回0
     */

    int updateRegisterMarketing(RegisterMarketing registerMarketing);

    /**
     * 根据id删除优惠券
     *
     * @param ids 优惠券ids数组
     * @return 成功返回1  失败返回0
     */

    int batchDeleteCoupon(long[] ids);

}
