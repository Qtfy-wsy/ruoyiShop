package com.ruoyi.member.service;


import com.ruoyi.member.vo.CustomerStatistics;

import java.io.OutputStream;

/**
 * Created by 魔金商城 on 17/12/5.
 * 会员混合api接口
 */
public interface CustomerServiceApi {

    /**
     * 查询会员统计(pc会员中心首页使用)
     *
     * @param customerId 会员id
     * @return 返回会员统计
     */
    CustomerStatistics queryCustomerStatistics(long customerId);

    /**
     * 导出选中的会员信息
     *
     * @param outputStream 输出流
     * @param ids          会员ID数组
     */
    Void exportCheckedCustomer(OutputStream outputStream, Long[] ids);


    /**
     * 导出全部会员信息
     *
     * @param outputStream 输出流
     */
    Void exportAllCustomer(OutputStream outputStream);
}
