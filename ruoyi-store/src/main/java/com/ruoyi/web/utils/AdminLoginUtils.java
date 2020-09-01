package com.ruoyi.web.utils;

import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * Created by 魔金商城 on 2019/5/15.
 * 登录帮助类 主要是获得登录信息
 */
public class AdminLoginUtils {

    private static final AdminLoginUtils ADMIN_LOGIN_UTILS = new AdminLoginUtils();


    private AdminLoginUtils() {

    }

    public static AdminLoginUtils getInstance() {
        return ADMIN_LOGIN_UTILS;
    }


    /**
     * 获得管理员名称
     *
     * @return 返回管理员名称
     */
    public String getManagerName() {
        return Objects.nonNull(getManager()) ? getManager().getUser().getUserName() : "";
    }

    /**
     * 获得管理员id
     *
     * @return 返回管理员id
     */
    public Long getManagerId() {
        return Objects.nonNull(getManager()) ? getManager().getUser().getUserId() : 0;
    }


    /**
     * 获取店铺id
     *
     * @return 获取店铺id
     */
    public long getStoreId() {
        return getManager().getUser().getStoreId();
    }
    public String getStoreName() {
        return getManager().getUser().getStoreName();
    }
    /**
     * 获得管理员信息
     *
     * @return 返回管理员信息
     */
    public LoginUser getManager() {
        return Objects.nonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal()) ? (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
    }

}
