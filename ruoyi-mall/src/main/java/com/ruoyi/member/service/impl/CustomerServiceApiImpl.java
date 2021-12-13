package com.ruoyi.member.service.impl;


import com.ruoyi.goods.service.IPmsAttentionService;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.marketing.service.CouponService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.CustomerServiceApi;
import com.ruoyi.member.service.IUmsBrowseRecordService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.vo.CustomerStatistics;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.setting.domain.LsCity;
import com.ruoyi.setting.domain.LsDistrict;
import com.ruoyi.setting.domain.LsProvince;
import com.ruoyi.setting.service.AreaService;
import com.ruoyi.setting.service.ILsStationLetterService;
import com.ruoyi.store.service.AttentionStoreService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by 伊甸园商城 on 17/12/5.
 * 会员服务api实现类
 */
@Service
public class CustomerServiceApiImpl implements CustomerServiceApi {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomerServiceApiImpl.class);

    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入订单服务接口
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入站内信服务接口
     */
    @Autowired
    private ILsStationLetterService stationLetterService;

    /**
     * 注入预存款服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;

    /**
     * 注入会员积分服务接口
     */
    @Autowired
      private CustomerPointService customerPointService;

    /**
     * 商品关注服务接口
     */
    @Autowired
    private IPmsAttentionService attentionService;

    /**
     * 注入关注店铺service
     */
     @Autowired
     private AttentionStoreService attentionStoreService;

    /**
     * 自动注入浏览历史service
     */
    @Autowired
    private IUmsBrowseRecordService browseRecordService;

    /**
     * 注入优惠券服务接口
     */
     @Autowired
     private CouponService couponService;

    /**
     * 注入区域服务
     */
    @Autowired
    private AreaService areaService;

    @Override
    public CustomerStatistics queryCustomerStatistics(long customerId) {
        logger.debug("queryCustomerStatistics and customerId:{}", customerId);

        // 会员统计
        CustomerStatistics customerStatistics = CustomerStatistics.build(customerService.queryCustomerWithCustomerLevel(customerId));

        // 设置订单消息总数
        setOrderMessageCount(customerStatistics, customerId);

        // 设置未读消息数量
        customerStatistics.setMessageCount(stationLetterService.unReadNum(customerId));

        // 设置预存款总额
        customerStatistics.setPredepositMoney(predepositRecordService.queryCutomerAllPredeposit(customerId));

        // 设置积分数量
          customerStatistics.setPoint(customerPointService.queryCustomerPointCount(customerId));

        // 设置用户优惠券数量
          customerStatistics.setCouponNum(couponService.queryCustomerCouponCount(customerId));

        // 设置用户关注的商品数量
        customerStatistics.setFollowSkuNum(attentionService.queryAttentionCount(customerId));

        // 设置用户关注的店铺数量
         customerStatistics.setFollowStoreNum(attentionStoreService.queryCustomerAttentionStoreCount(customerId));

        // 设置用户浏览纪录的数量
        customerStatistics.setHistoryNum(browseRecordService.queryCustomerBrowseRecordCount(customerId));

        return customerStatistics;
    }


    @Override
    public Void exportCheckedCustomer(OutputStream outputStream, Long[] ids) {
        logger.debug("exportCheckedCustomer and ids:{}", Arrays.toString(ids));
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("exportCheckedCustomer fail :ids is empty");
            return null;
        }
        exportCustomer(customerService.queryCustomersByIds(Arrays.asList(ids)), outputStream);
        return null;
    }

    @Override
    public Void exportAllCustomer(OutputStream outputStream) {
        logger.debug("exportAllCustomer......");
        exportCustomer(customerService.queryAllCustomer(), outputStream);
        return null;
    }

    /**
     * 导出用户信息
     *
     * @param customers 用户信息集合
     * @param os        输出流
     */
    private void exportCustomer(final List<UmsMember> customers, final OutputStream os) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("会员信息");
        // 创建excel的基本信息
        createExecleBase(wb, sheet);
        // 创建导出的数据信息
        createExecleData(sheet, customers, 1);
        try {
            // 放入输出流
            wb.write(os);
        } catch (IOException e) {
            logger.error("export member fail \r\n", e);
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
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        // 设置列头信息
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("手机");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("邮箱");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("身份证");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("所在地");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("积分");
        cell.setCellStyle(style);
    }

    /**
     * 创建导入execel的数据
     *
     * @param sheet     excel中的sheet对象
     * @param customers 用户信息集合
     * @param offset    从第几行开始输出数据
     */
    private void createExecleData(final HSSFSheet sheet, final List<UmsMember> customers, final int offset) {
        if (CollectionUtils.isEmpty(customers)) {
            return;
        }
        final StringBuilder skip = new StringBuilder("0");
        IntStream.range(0, customers.size()).forEach(index -> {
            HSSFRow row = sheet.createRow(offset + index + Integer.parseInt(skip.toString()));
            UmsMember cus = customers.get(index);
            row.createCell(0).setCellValue(cus.getUsername());
            if (cus.getGender() != null) {
                if ("1".equals(cus.getGender())) {
                    row.createCell(1).setCellValue("男");
                } else if ("2".equals(cus.getGender())) {
                    row.createCell(1).setCellValue("女");
                } else {
                    row.createCell(1).setCellValue("保密");
                }

            }
            if (cus.getMobile() != null) {
                row.createCell(2).setCellValue(cus.getMobile());
            }

            if (cus.getEmail() != null) {
                row.createCell(3).setCellValue(cus.getEmail());
            }

            if (cus.getCardid() != null) {
                row.createCell(4).setCellValue(cus.getCardid());
            }
            this.buildAreaAndPoint(cus);
            if (!StringUtils.isEmpty(cus.getDetailaddress())) {
                row.createCell(5).setCellValue(cus.getDetailaddress());
            }
            row.createCell(6).setCellValue(cus.getCustomerPoint());
        });
    }

    /**
     * 组装地址信息和积分信息
     *
     * @param customer 用户实体
     */
    private UmsMember buildAreaAndPoint(UmsMember customer) {
        // member.setCustomerPoint(customerPointService.queryCustomerPointCount(member.getId()));
        String detailAdress = "";
        if (customer.getProvince() != 0) {
            LsProvince province = areaService.queryProvinceById(customer.getProvince());
            if (!ObjectUtils.isEmpty(province)) {
                detailAdress += province.getName();
            }
        }
        if (customer.getCity() != 0) {
            LsCity city = areaService.queryCityById(customer.getCity());
            if (!ObjectUtils.isEmpty(city)) {
                detailAdress += city.getName();
            }
        }
        if (customer.getCounty() != 0) {
            LsDistrict district = areaService.queryDistrictById(customer.getCounty());
            if (!ObjectUtils.isEmpty(district)) {
                detailAdress += district.getName();
            }
        }
        detailAdress += customer.getDetailaddress() == null ? "" : customer.getDetailaddress();
        customer.setDetailaddress(detailAdress);
        return customer;
    }

    /**
     * 设置消息订单总数
     *
     * @param customerStatistics 会员统计
     * @param customerId         会员id
     */
    private void setOrderMessageCount(CustomerStatistics customerStatistics, long customerId) {
        logger.debug("setOrderMessageCount....");
        customerStatistics.setToPayCount(orderService.toPayOrderCount(customerId));
        customerStatistics.setToDeliverCount(orderService.toDeliverOrderCount(customerId));
        customerStatistics.setToEvaluateCount(orderService.toEvaluateOrderCount(customerId));
        customerStatistics.setToReceiptCount(orderService.toReceiptOrderCount(customerId));
    }
}
