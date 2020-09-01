package com.ruoyi.member.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.vo.LoginParams;
import com.ruoyi.member.vo.UnAuthLoginParams;

import java.util.function.Consumer;

/**
 * Created by 魔金商城 on 17/7/17.
 * 登录接口
 */
public interface LoginService {

    /**
     * 前端登录 (app,pc,mobile)
     *
     * @param loginParams 登入参数
     * @return 返回码  -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    AjaxResult login(LoginParams loginParams);

    /**
     * 前端免密登录
     *
     * @param unAuthLoginParams 登入参数
     * @return 返回码  -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    int unAuthLogin(UnAuthLoginParams unAuthLoginParams);

    /**
     * 商家端app登录
     *
     * @param username 用户名
     * @param password 密码
     * @param consumer 回调
     * @return 返回码  0 输入为空 -2 不存在该用户 -3 用户名或密码错误 -4 冻结 -5 未启用 1 成功 -6 店铺信息错误 -7用户不是店铺管理员
     */
    int storeAppLogin(String username, String password, Consumer<UmsMember> consumer);
}
