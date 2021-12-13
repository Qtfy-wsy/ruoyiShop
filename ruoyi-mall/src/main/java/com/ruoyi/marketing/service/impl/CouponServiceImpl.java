package com.ruoyi.marketing.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.RandomMathLetter;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.mapper.CouponCodeMapper;
import com.ruoyi.marketing.mapper.CouponFallMapper;
import com.ruoyi.marketing.mapper.CouponFullMapper;
import com.ruoyi.marketing.mapper.CouponMapper;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.marketing.service.RegisterMarketingService;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 优惠券service实现类
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
@Service
public class CouponServiceImpl implements CouponService {
    /**
     * 调试日志
     */
    Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    /**
     * 注入优惠券mapper
     */
    @Autowired
    private CouponMapper couponMapper;
    /**
     * 注入优惠券券码mapper
     */
    @Autowired
    private CouponCodeMapper couponCodeMapper;
    /**
     * 直降mapper
     */
    @Autowired
    private CouponFallMapper couponFallMapper;
    /**
     * 满减mapper
     */
    @Autowired
    private CouponFullMapper couponFullMapper;

    /**
     * 注入信息设置mapper
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;
    /**
     * 注入注册营销service
     */
    @Autowired
    private RegisterMarketingService registerMarketingService;


    /**
     * 添加优惠券
     *
     * @param coupon  优惠券实体类
     * @param storeId 店铺id
     * @return 添加返回码 -1 失败 -2 限领张数不正确 -3限领张数不能大于总张数 -4 开始时间大于结束时间 >=1 成功
     */
    @Override
    @Transactional
    public int addCoupon(Coupon coupon, long storeId) {
        logger.debug("addCoupon and coupon:{}", coupon);
        if (Objects.isNull(coupon)) {
            logger.error("addCoupon error due to coupon is null");
            return -1;
        }
        if (coupon.getLimitNum() <= 0) {
            logger.error("addCoupon fail: limitNum <=0");
            return -2;
        }
        if (coupon.getNum() < coupon.getLimitNum()) {
            logger.error("addCoupon fail: num < limitNum");
            return -3;
        }
        if (coupon.toCompareTime()) {
            logger.error("addCoupon fail: toCompareTime fail");
            return -4;
        }
        coupon.setStoreId(storeId);
        couponMapper.addCoupon(coupon);
        if (coupon.getType() == 1) {//1满减
            CouponFull couponFull = new CouponFull();
            couponFull.setCouponId(coupon.getId());
            couponFull.setFullPrice(coupon.getCouponFull().getFullPrice());
            couponFull.setPrice(coupon.getCouponFull().getPrice());
            //判断满的金额是否大于减的金额
            if (couponFull.getFullPrice().compareTo(couponFull.getPrice()) < 1) {
                logger.error("addCoupon fail: fullPrice <=price");
                throw new ServiceException("R-000013");
            }
            couponFullMapper.addCouponFull(couponFull);
        }
        if (coupon.getType() == 2) {//2直降
            CouponFall couponFall = new CouponFall();
            couponFall.setCouponId(coupon.getId());
            couponFall.setPrice(coupon.getCouponFall().getPrice());
            couponFallMapper.addCouponFall(couponFall);
        }
        //优惠券券码集合
        List<CouponCode> list = new ArrayList<>();
        for (int i = 0; i < coupon.getNum(); i++) {
            list.add(new CouponCode(0, coupon.getId(), RandomMathLetter.randomString(18), 0, "0", null, ""));
        }
        //添加优惠券券码
        return couponCodeMapper.addCouponCode(list);
    }

    /**
     * 分页查询优惠券
     *
     * @param pageHelper 分页帮助类
     * @param name       优惠券名称
     * @return 优惠券集合
     */
    @Override
    public PageHelper<Coupon> queryCoupon(PageHelper<Coupon> pageHelper, String name, long storeId) {
        logger.debug("queryCoupon and pageHelper : {} \r\n name: {}", pageHelper, name);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("name", name);
        return pageHelper.setListDates(couponMapper.queryCoupon(pageHelper.getQueryParams(params, couponMapper.queryCouponCount(params))));
    }

    /**
     * 删除优惠券
     *
     * @param ids 优惠券id数组
     * @return 删除返回码 -1 失败 >=1成功
     */
    @Override
    @Transactional
    public int deleteCoupon(long[] ids, long storeId) {
        logger.debug("deleteCoupon and ids:{}", ids);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deleteCoupon error due to ids is empty");
            return -1;
        }
        registerMarketingService.batchDeleteCoupon(ids);
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("ids", ids);
        couponCodeMapper.deleteCouponCode(ids);
        couponFallMapper.deleteCouponFall(ids);
        couponFullMapper.deleteCouponFull(ids);
        return couponMapper.deleteCoupon(map);
    }

    /**
     * 复制优惠券链接
     *
     * @param id      优惠券id
     * @param storeId 店铺id
     * @return 返回优惠券链接 -1:没有查询到该优惠券, 0:优惠券已过期, 其他字符串为优惠券链接
     */
    @Override
    public String copyCoupon(long id, long storeId) {
        logger.debug("copyCoupon and id:{}\r\n storeId:{}", id, storeId);
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("id", id);
        Coupon coupon = couponMapper.queryCouponById(map);
        //优惠券过期
        String value = "0";
        //不存在该优惠券
        if (Objects.isNull(coupon)) {
            value = "-1";
        }
        //该优惠券链接
        if (coupon.getEndTime().isAfter(LocalDateTime.now())) {
            value = baseInfoSetService.queryBaseInfoSet().getSiteUrl() + "/#/getcoupon?couponId=" + coupon.getId();
        }
        return value;
    }

    /**
     * 导出优惠券券码
     *
     * @param storeId  店铺id
     * @param couponId 优惠券id
     */
    @Override
    public Void exportCoupon(OutputStream outputStream, long storeId, long couponId) {
        logger.debug("exportCoupon and couponId:{}\r\n storeId:{}", couponId, storeId);
        //查询优惠券map
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("id", couponId);
        List<CouponCode> couponCodeList = couponCodeMapper.queryCouponCodeByCouponId(couponId);
        exportCouponCodeList(couponCodeList, outputStream);
        return null;
    }

    /**
     * 根据店铺id查询优惠券
     *
     * @param storeId 店铺id
     * @return 优惠券集合
     */
    @Override
    public List<Coupon> queryCouponByStoreId(long storeId, boolean isNeedDetail, long useType) {
        logger.debug("exportCoupon and  storeId:{} \r\n isNeedDetail:{} \r\n useType:{}", storeId, isNeedDetail, useType);

        List<Coupon> coupons = couponMapper.queryCouponByStoreId(storeId, useType);

        if (isNeedDetail && !CollectionUtils.isEmpty(coupons)) {
            coupons.stream().forEach(this::setCouponDetail);
        }
        return coupons;
    }

    /**
     * 根据店铺id查询优惠券(商品详情使用)
     *
     * @param storeId      店铺id
     * @param isNeedDetail 是否需要查询详情
     * @param useType      获得方式 1领取 2发放
     * @return 优惠券集合
     */
    @Override
    public List<Coupon> queryCouponForSpu(long storeId, boolean isNeedDetail, long useType) {
        logger.debug("queryCouponForSpu and  storeId:{} \r\n isNeedDetail:{} \r\n useType:{}", storeId, isNeedDetail, useType);

        //获取优惠券
        List<Coupon> coupons = this.queryCouponByStoreId(storeId, isNeedDetail, useType);

        //过滤无效优惠券
        return coupons.stream().map(coupon -> {
            coupon.buildCanReceiveCount(couponCodeMapper.queryCanReceiveCouponCodeCount(coupon.getId()));
            return coupon.buildIsRunOut();
        }).collect(toList());
    }

    /**
     * 查询优惠券详情页数据
     *
     * @return 优惠券详情页数据
     */
    @Override
    public CouponDetails queryCouponDetails(long storeId, long couponId) {
        logger.debug("queryCouponDetails and  storeId:{}\r\n couponId:{}", storeId, couponId);
        //1.查询优惠券
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("storeId", storeId);
        queryMap.put("id", couponId);
        Coupon coupon = couponMapper.queryCouponById(queryMap);
        setCouponDetail(coupon);
        //2.查询优惠券券码
        return new CouponDetails(coupon, couponCodeMapper.queryCouponCodeByCouponId(couponId));
    }

    /**
     * 根据会员id查询会员领取优惠券信息
     *
     * @param status     null:全部 1:可用 2:已使用 3:已过期
     * @param customerId 会员id
     * @return 会员领取优惠券信息集合
     */
    @Override
    public PageHelper<CouponCode> queryCouponCodeByCustomerId(PageHelper<CouponCode> pageHelper, long customerId, String status) {
        logger.debug("queryCouponCodeByCustomerId and  customerId:{}\r\n status:{}", customerId, status);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        if (!StringUtils.isEmpty(status)) {
            params.put("status", status);
        }
        List<CouponCode> couponCodeList = couponCodeMapper.queryCouponCodeByCustomerId(pageHelper.getQueryParams(params, couponCodeMapper.queryCouponCodeCountByCustomerId(params))).stream().map(CouponCode::getCouponCodeToChangeStoreName).collect(toList())
                .stream().map(couponCode -> getCouponDetailInfo(couponCode)).collect(toList());
        return pageHelper.setListDates(couponCodeList);
    }

    @Override
    public List<CouponCode> queryCustomerCanUserCoupon(Long storeId, Long customerId) {
        logger.debug("queryCustomerCanUserCoupon and storeId:{} \r\n customerId:{}", storeId, customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("customerId", customerId);

        // 用户还未使用的优惠券并且时间在当前时间的
        List<CouponCode> couponCodes = couponCodeMapper.queryCustomerCanUserCoupon(params);

        if (CollectionUtils.isEmpty(couponCodes)) {
            return couponCodes;
        }

        // 设置优惠券的详情
        setCouponCodeDetail(couponCodes);

        return couponCodes;
    }

    @Override
    public int setCouponUsed(Long customerId, String couponCode,long orderId) {
        logger.debug("setCouponUsed and customerId:{} \r\n couponCode:{},orderId:{}", customerId, couponCode,orderId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("orderId", orderId);
        params.put("couponCode", couponCode);
        return couponCodeMapper.setCouponUsed(params);
    }

    @Override
    public void backCoupon(long customerId, String couponCode) {
        logger.debug("backCoupon and customerId:{} \r\n couponCode:{}", customerId, couponCode);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("couponCode", couponCode);
        couponCodeMapper.backCoupon(params);
    }

    /**
     * 用户领取优惠券
     *
     * @param customerId 用户id
     * @param couponId   优惠券id
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券不存在或已失效(删除状态) -6 系统繁忙，请重试
     */
    @Transactional
    @Override
    public int receiveCoupon(long customerId, Long couponId, long useType) {
        logger.debug("receiveCoupon and customerId:{} \r\n couponId:{}", customerId, couponId);
        if (ObjectUtils.isEmpty(customerId) || ObjectUtils.isEmpty(couponId)) {
            logger.error("receiveCoupon fail:customerId or couponId is empty");
            return -1;
        }
        Coupon coupon = couponMapper.queryCouponByIdForReceive(couponId, useType);
        if (ObjectUtils.isEmpty(coupon)) {
            logger.error("receiveCoupon error : coupon is not exist");
            return -5;
        }
        if (coupon.getEndTime().isBefore(LocalDateTime.now())) {
            logger.error("receiveCoupon error : activity finish");
            return -2;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("couponId", couponId);
        if (couponCodeMapper.queryReceivedCouponCodeByCustomerId(map) >= coupon.getLimitNum()) {
            logger.error("receiveCoupon error : over limitNum");
            return -4;
        }
        // 可以领取的优惠券
        List<CouponCode> couponCodeList = couponCodeMapper.queryCanReceiveCouponCode(couponId);
        if (couponCodeList.size() < 1) {
            logger.error("receiveCoupon error : coupon run out");
            return -3;
        }
        CouponCode couponCode = couponCodeList.get(0);
        couponCode.setCustomerId(customerId);
        int i = couponCodeMapper.updateReceivedCoupon(couponCode);
        return updateCouponReceived(i, couponId, couponCodeList);
    }

    /**
     * 用户通过券码领取优惠券
     *
     * @param customerId 用户id
     * @param code       优惠券code
     * @return 返回码 1：成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券不存在或已失效(删除状态) -6：该优惠券已被领取 -7：优惠券不存在
     */
    @Transactional
    @Override
    public int receiveCouponByCode(long customerId, String code, long useType) {
        logger.debug("receiveCouponByCode and customerId:{} \r\n code:{} \r\n useType:{}", customerId, code, useType);
        if (ObjectUtils.isEmpty(customerId) || ObjectUtils.isEmpty(code)) {
            logger.error("receiveCouponByCode fail:customerId or code is empty");
            return -1;
        }
        Long couponId = couponCodeMapper.queryCouponIdByCode(code);
        if (ObjectUtils.isEmpty(couponId)) {
            logger.error("receiveCouponByCode fail:coupon is not exist");
            return -7;
        }
        Coupon coupon = couponMapper.queryCouponByIdForReceive(couponId, useType);
        if (ObjectUtils.isEmpty(coupon)) {
            logger.error("receiveCouponByCode error : coupon is not exist");
            return -5;
        }
        if (coupon.getEndTime().isBefore(LocalDateTime.now())) {
            logger.error("receiveCouponByCode error : activity finish");
            return -2;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("couponId", couponId);
        if (couponCodeMapper.queryReceivedCouponCodeByCustomerId(map) >= coupon.getLimitNum()) {
            logger.error("receiveCouponByCode error : over limitNum");
            return -4;
        }
        // 可以领取的优惠券
        List<CouponCode> couponCodeList = couponCodeMapper.queryCanReceiveCouponCode(couponId);
        if (couponCodeList.size() < 1) {
            logger.error("receiveCouponByCode error : coupon run out");
            return -3;
        }
        CouponCode couponCode = new CouponCode();
        couponCode.setCode(code);
        couponCode.setCustomerId(customerId);
        int i = couponCodeMapper.updateReceivedCouponByCode(couponCode);
        return updateCouponReceived(i, couponId, couponCodeList);
    }

    /**
     * 根据id查询优惠券信息（pc用）
     *
     * @param couponId 优惠券id
     * @return 优惠券
     */
    @Override
    public Coupon queryCouponByCouponId(Long couponId) {
        logger.debug("queryCouponByCouponId and couponId:{} ", couponId);
        if (ObjectUtils.isEmpty(couponId)) {
            logger.error("queryCouponByCouponId fail :couponId is null");
            return null;
        }
        Coupon coupon = couponMapper.queryCouponByIdForReceive(couponId, -1);
        if (ObjectUtils.isEmpty(coupon)) {
            logger.error("queryCouponByCouponId fail :coupon is null");
            return null;
        }
        if (coupon.isFall()) {
            coupon.setCouponFall(couponFallMapper.queryCouponFallById(coupon.getId()));
        }
        if (coupon.isFullType()) {
            coupon.setCouponFull(couponFullMapper.queryCouponFullById(coupon.getId()));
        }
        return coupon;
    }


    @Override
    public int queryCustomerCouponCount(long customerId) {
        logger.debug("queryCustomerCouponCount and customerId:{}", customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("status", "1");
        return couponCodeMapper.queryCouponCodeCountByCustomerId(params);
    }

    @Override
    public PageHelper<Coupon> queryCouponForSite(PageHelper<Coupon> pageHelper, long storeId) {
        logger.debug("queryCouponForPc and storeId :{}", storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        return pageHelper.setListDates(couponMapper.queryCouponForPc(pageHelper.getQueryParams(params, couponMapper.queryCouponForPcCount(params)))
                .stream().map(coupon -> {
                    coupon.buildCanReceiveCount(couponCodeMapper.queryCanReceiveCouponCodeCount(coupon.getId()));
                    setCouponDetail(coupon);
                    return coupon.buildIsRunOut();
                }).collect(toList())
        );
    }

    /**
     * 设置优惠券详情
     *
     * @param coupon 优惠券
     */
    private void setCouponDetail(Coupon coupon) {
        // 设置设置直降详情
        if (coupon.isFall()) {
            coupon.setCouponFall(couponFallMapper.queryCouponFallById(coupon.getId()));
        } else {
            // 设置满减详情
            coupon.setCouponFull(couponFullMapper.queryCouponFullById(coupon.getId()));
        }
    }

    /**
     * 设置优惠券的详情,主要是设置优惠券的满减价格 和直降价格
     *
     * @param couponCodes 优惠券信息
     */
    private void setCouponCodeDetail(List<CouponCode> couponCodes) {

        // 设置直降的价格
        couponCodes.stream().filter(CouponCode::isFall).forEach(couponCode -> {
            CouponFall couponFall = couponFallMapper.queryCouponFallById(couponCode.getCouponId());
            if (Objects.nonNull(couponFall)) {
                couponCode.setFallPrice(couponFall.getPrice());
            }
        });

        // 设置满减的价格
        couponCodes.stream().filter(CouponCode::isFullType).forEach(couponCode -> {
            CouponFull couponFull = couponFullMapper.queryCouponFullById(couponCode.getCouponId());
            if (Objects.nonNull(couponFull)) {
                couponCode.setFullPrice(couponFull.getFullPrice());
                couponCode.setPrice(couponFull.getPrice());
            }
        });
    }

    /**
     * 将优惠券状态标识转为中文
     *
     * @param status 优惠券状态  0 未领取 1已领取未使用 2 已使用 3 已失效
     * @return 优惠券状态  0 未领取 1已领取未使用 2 已使用 3 已失效
     */
    private String getCouponStatus(String status) {
        String statusString = "未知状态";
        if ("0".equals(status)) {
            statusString = "未领取";
        }
        if ("1".equals(status)) {
            statusString = "已领取未使用";
        }
        if ("2".equals(status)) {
            statusString = "已使用";
        }
        if ("3".equals(status)) {
            statusString = "已失效";
        }
        return statusString;
    }

    /**
     * 获取优惠券详情
     *
     * @param couponCode 优惠券
     * @return 优惠券
     */
    private CouponCode getCouponDetailInfo(CouponCode couponCode) {
        logger.debug("getCouponDetailInfo and couponCode", couponCode);
        if (couponCode.getType() == 1) {
            CouponFull couponFull = couponFullMapper.queryCouponFullById(couponCode.getCouponId());
            if (!ObjectUtils.isEmpty(couponFull)) {
                couponCode.setFullPrice(couponFull.getFullPrice());
                couponCode.setPrice(couponFull.getPrice());
            } else {
                logger.error("getCouponDetailInfo fail: queryCouponFullById null");
            }
        }
        if (couponCode.getType() == 2) {
            CouponFall couponFall = couponFallMapper.queryCouponFallById(couponCode.getCouponId());
            if (!ObjectUtils.isEmpty(couponFall)) {
                couponCode.setFallPrice(couponFall.getPrice());
            } else {
                logger.error("getCouponDetailInfo fail: queryCouponFallById null");
            }
        }
        return couponCode;
    }

    /**
     * 导出优惠券信息
     *
     * @param couponCodes 优惠券券码集合
     * @param os          输出流
     */
    private void exportCouponCodeList(final List<CouponCode> couponCodes, final OutputStream os) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("优惠券券码列表");
        // 创建excel的基本信息
        createExecleBase(wb, sheet);
        // 创建导出的数据信息
        createExecleData(sheet, couponCodes, 1);
        try {
            // 放入输出流
            wb.write(os);
        } catch (IOException e) {
            logger.error("exportCouponCodeList fail \r\n", e);
        }
    }


    /**
     * 创建excel的基本信息
     *
     * @param wb    excel对象
     * @param sheet excel中的sheet对象
     */
    private void createExecleBase(final HSSFWorkbook wb, final HSSFSheet sheet) {

        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置宽度
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 11000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("券码");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("券码状态");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("领取人");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("领取时间");
        cell.setCellStyle(style);
    }

    /**
     * 创建导入execel的数据
     *
     * @param sheet       excel中的sheet对象
     * @param couponCodes 优惠券券码集合
     * @param offset      从第几行开始输出数据
     */
    private void createExecleData(final HSSFSheet sheet, final List<CouponCode> couponCodes, final int offset) {
        if (CollectionUtils.isEmpty(couponCodes)) {
            return;
        }
        final StringBuilder skip = new StringBuilder("0");
        IntStream.range(0, couponCodes.size()).forEach(index -> {
            HSSFRow row = sheet.createRow(offset + index + Integer.parseInt(skip.toString()));
            CouponCode couponCode = couponCodes.get(index);
            row.createCell(0).setCellValue(index + 1);
            row.createCell(1).setCellValue(couponCode.getCode());
            row.createCell(2).setCellValue(getCouponStatus(couponCode.getStatus()));
            row.createCell(3).setCellValue(couponCode.getUsername());
            row.createCell(4).setCellValue(Objects.isNull(couponCode.getReceiveTime()) ? "" : couponCode.getReceiveTime().toString().replace("T", " "));
        });
    }

    /**
     * 判断优惠券是否领完，领完则更新优惠券状态为已领完
     *
     * @param i              更新优惠券领取状态返回码
     * @param couponId       优惠券id
     * @param couponCodeList 优惠券码集合
     * @return 1 成功  -6 该优惠券已被领取
     */
    private int updateCouponReceived(int i, Long couponId, List<CouponCode> couponCodeList) {
        if (i > 0) {
            // 如果可以领取的优惠券没有了 则更新优惠券状态为已领完
            if (couponCodeList.size() == 1) {
                logger.debug("coupon has all received.....");
                couponMapper.updateCouponAllReceived(couponId);
            }

            logger.info("receiveCoupon success");
            return 1;
        } else {
            logger.error("receiveCoupon error : already been received");
            return -6;
        }
    }

}
