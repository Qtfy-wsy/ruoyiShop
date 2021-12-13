package com.ruoyi.member.service.impl;


import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.md5.MD5Utils;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.LoginService;
import com.ruoyi.member.service.RegisterService;
import com.ruoyi.member.vo.AppletLoginRedisParamResponse;
import com.ruoyi.member.vo.LoginParams;
import com.ruoyi.member.vo.UnAuthLoginParams;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreInfoService;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by 伊甸园商城 on 17/7/17.
 * 登录服务接口
 */
@Service("loginService")
@Slf4j
public class LoginServiceImpl implements LoginService {

    /**
     * 请求时间戳过期时间5分钟
     */
    private static final int REQUEST_TIME_OUT = 1000 * 60 * 5;
    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;
    /**
     * 注册接口
     */
    @Autowired
    private RegisterService registerService;
    /**
     * 注入redis服务
     */
    @Autowired
    private RedisCache redisService;

    /**
     * jwt密钥
     */
    @Value("${token.secret}")
    private String jwtSecretKey;
    @Autowired
    private ITStoreInfoService storeInfoService;
    /**
     * 密码工具类
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${auth.salt:wsfmall}")
    private String salt;

    @Override
    public AjaxResult login(LoginParams loginParams) {
        log.debug("login and loginParams:{}", loginParams);

        if (Objects.isNull(loginParams)) {
            log.info("login fail due to loginParams is null....");
            return AjaxResult.error(-6,"loginParams is null");
        }

        // 验证验证码
        if (!loginParams.validateCode()) {
            log.info("login fail due to validateCode fail....");
            return AjaxResult.error(-6,"validateCode fail");
        }


        // 根据用户名查询出用户信息
        UmsMember customer = customerService.queryCustomerByName(loginParams.getMobile());

        log.debug("login member:{}", customer);

        // 用户不存在直接返回失败
        if (Objects.isNull(customer)) {
            log.info("login fail due to member is not exist....");
            return AjaxResult.error(-4,"member is not exist");
        }

        // 登录次数
        int errorCount = customer.getLoginErrorCount();

        // 判断用户账号是否正常
        if (!"1".equals(customer.getStatus())) {
            log.info("login fail due to  member status is error ....");
            return AjaxResult.error(-2,"账号冻结");
        }

        //判断用户的账号是否被锁定
        if (Objects.nonNull(customer.getLockTime())) {
            if (LocalDateTime.now().isBefore(customer.getLockTime().plus(30, ChronoUnit.MINUTES))) {
                // 账户被锁定或禁用
                log.info("login fail due to  member is locked ....");
                return AjaxResult.error(-3,"账号锁定");
            } else {
                // 30分钟后解锁用户
                customerService.unlockUser(customer.getId());
                errorCount = 0;
            }
        }
// -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误

        // 判断用户密码是否正确
        if (!passwordEncoder.matches(loginParams.getPassword(), customer.getPassword())) {
            log.info("login fail due to  password is wrong....");

            //增加登录失败的次数
            customerService.updateLoginErrorCount(customer.getId());

            // 如果登录失败次数>= 6次 则锁定帐户 30分钟内不能登录
            if (errorCount + 1 >= 6) {
                customerService.lockUser(customer.getId());
                return null;
            } else {
                return AjaxResult.error(-1,"用户名或密码错误");
            }
        }
        // 清除密码
        customer.clearPassword();

        // 更新登录时间
        customerService.updateLoginTime(customer.getId());

        //清除登录错误次数
        customerService.unlockUser(customer.getId());
        Map<String, Object> res = new HashMap<>();
        final StringBuilder sb = new StringBuilder();
        sb.append(Jwts.builder().setSubject(customer.getUsername())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setIssuedAt(new Date())
                .claim("userName", customer.getUsername())
                .claim("nickName", customer.getNickname())
                .claim("releName", customer.getRelename())
                .claim("id", customer.getId())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))) // 有效期1小时
                .compact());
        if (customer != null) {
            AppletLoginRedisParamResponse appletLoginRedisParamResponse = new AppletLoginRedisParamResponse();
            appletLoginRedisParamResponse.setCustomerId(customer.getId());
            appletLoginRedisParamResponse.setToken(sb.toString());
            redisService.putToRedis(sb.toString(), JSON.toJSONString(appletLoginRedisParamResponse),1, TimeUnit.DAYS);
            redisService.putToRedis(loginParams.getMobile(), sb.toString());
            res.put("access_token", sb.toString());
            res.put("refresh_token", sb.toString());
            res.put("member", customer);
        }

        log.debug("ok...login success.....");

        return AjaxResult.success(res);
    }


    /**
     * 前端免密登录
     *
     * @param unAuthLoginParams 登入参数
     * @return 返回码  -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @Override
    public int unAuthLogin(UnAuthLoginParams unAuthLoginParams) {
        log.debug("login and unAuthLoginParams:{}", unAuthLoginParams);
        if (Objects.isNull(unAuthLoginParams)) {
            log.info("login fail due to unAuthLoginParams is null....");
            return -1;
        }
        if (!checkSignLegal(unAuthLoginParams)) {
            log.warn("登录请求不合法! loginParam={}", JSON.toJSONString(unAuthLoginParams));
            return -5;
        }
        // 根据用户名查询出用户信息
        UmsMember customer = customerService.queryCustomerByName(unAuthLoginParams.getPhoneNo());
        // 用户不存在则尝试注册 否则直接放行
        long customerId;
        if (Objects.isNull(customer)) {
            log.info("login fail due to member is not exist....");
            customerId = registerService.unAuthRegister(unAuthLoginParams.getPhoneNo(), unAuthLoginParams.getChannelType());
            Consumer<Long> consumer = unAuthLoginParams.getConsumer();
            if (Objects.nonNull(consumer)) {
                //处理登录后回调
                consumer.accept(customerId);
            }
            //清除session
            return 1;
        }
        customerId = customer.getId();
        // 店铺员工禁止登陆
        if ("3".equals(customer.getType())) {
            log.info("login fail due to member is storeStaff");
            return -1;
        }
        //判断用户的账号是否被锁定
        if (Objects.nonNull(customer.getLockTime())) {
            if (LocalDateTime.now().isBefore(customer.getLockTime().plus(30, ChronoUnit.MINUTES))) {
                // 账户被锁定或禁用
                log.info("login fail due to member is locked ....");
                return -3;
            } else {
                // 30分钟后解锁用户
                customerService.unlockUser(customerId);
            }
        }
        log.debug("login member:{}", customer);
        Consumer<Long> consumer = unAuthLoginParams.getConsumer();
        if (Objects.nonNull(consumer)) {
            //处理登录后回调
            consumer.accept(customerId);
        }
        customerService.updateLoginTime(customerId);
        return 1;
    }

    /**
     * @return 是否合法
     * @description 检查签名合法性
     * @author lingfeng.xu
     * @date 2020/7/22 10:12
     */
    private boolean checkSignLegal(UnAuthLoginParams unAuthLoginParams) {
        Long timeStamp = unAuthLoginParams.getTimeStamp();
        Long currentTime = System.currentTimeMillis();
        if (currentTime - timeStamp > REQUEST_TIME_OUT) {
            log.warn("请求时间戳过期!unAuthLoginParams={}", JSON.toJSONString(unAuthLoginParams));
            return false;
        }
        String phoneNo = unAuthLoginParams.getPhoneNo();
        String channelType = unAuthLoginParams.getChannelType();

        String originSign = unAuthLoginParams.getSign();
        String random = originSign.substring(originSign.length() - 2);
        String md5 = MD5Utils.getInstance().createMd5(timeStamp + salt + phoneNo + random + channelType);
        String sign = "ws" + md5 + random;
        return sign.equals(originSign);
    }

    @Override
    public int storeAppLogin(String username, String password, Consumer<UmsMember> consumer) {
        log.debug("storeAppLogin and username:{} \r\n password:{}", username, password);


        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            log.error("store login fail due to params is error.....");
            return 0;
        }

        // 根据用户名查询出用户信息
        UmsMember customer = customerService.queryCustomerByName(username);

        log.debug("login member:{}", customer);

        if (Objects.isNull(customer)) {
            log.error("store login fail due to member is not exit.....");
            return -2;
        }
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            log.error("store login fail due to password is error.....");
            return -3;
        }
        if ("2".equals(customer.getStatus())) {//1 正常 2冻结 3未启用
            log.error("store login fail due to member is frozen.....");
            return -4;
        }
        if ("3".equals(customer.getStatus())) {
            log.error("store login fail due to member is not use.....");
            return -5;
        }

        // 判断用户是否是店铺管理员
        if (!"2".equals(customer.getType())) {
            log.error("store login fail due to custoemr is not store admin....");
            return -7;
        }

        // 查询店铺信息
        TStoreInfo storeInfo = storeInfoService.queryStoreInfo(customer.getStoreId());


        // 店铺不存在 店铺关闭  店铺不是普通店铺 返回错误
        if (Objects.isNull(storeInfo) || "4".equals(storeInfo.getStatus()) || !"0".equals(storeInfo.getType()) || !"2".equals(storeInfo.getStatus())) {
            log.error("storeInfo is err0r...");
            return -6;
        }

        // 登录成功 进行回调
        customer.clearPassword();
        consumer.accept(customer);

        customerService.updateLoginTime(customer.getId());

        log.debug("ok...login success.....");

        return 1;
    }
}
