package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 伊甸园商城 on 2020/2/8.
 * 该注解使用的场景是 不需要登录，但是如果登录的话也能获得会员ID
 * 直观效果就是 如果接口注解了该注视，那么首先这个接口在用户登录和未登录情况下都能使用，如果
 * 在登录情况下则能获取到用户的id，未登录情况下则没有用户id
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnAuthIfPossible {
}
