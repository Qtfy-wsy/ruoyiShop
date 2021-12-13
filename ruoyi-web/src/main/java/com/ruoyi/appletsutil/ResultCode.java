package com.ruoyi.appletsutil;

/**
 * Created by 伊甸园商城 on 17/7/10.
 * 结果返回吗
 */
public interface ResultCode {

    /**
     * 操作成功；
     */
    String SUCCESSFUL = "R-00000";

    /**
     * 操作失败；
     */
    String FAILED = "R-00001";

    /**
     * 微信未授权
     */
    String WX_NOT_AUTHORIZED = "R-00002";

    /**
     * 微信未关联用户
     */
    String WX_NOT_LINKD = "R-00003";

    /**
     * 小程序账号已绑定错误
     */
    String WX_ALREADY_BIND_ERROR = "R-00004";

}
