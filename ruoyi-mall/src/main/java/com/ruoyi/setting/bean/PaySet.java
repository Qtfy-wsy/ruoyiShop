package com.ruoyi.setting.bean;


/**
 * 支付接口设置数据库映射类
 *
 * @author 伊甸园商城 on 2017/5/17.
 */

public class PaySet {
    /**
     * 主键id
     */
    private long id;
    /**
     * 支付方式 1:支付宝 2:微信 3:银联 4:预存款 5:微信APP支付 6:微信小程序支付
     */
    private String codeType;
    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 字段值
     */
    private String columnValue;

    /**
     * 组装数据-用于向数据库插入数据
     *
     * @param paySet      数据库映射对象
     * @param codeType    支付设置类型 1:支付宝 2:微信 3:银联 4:预存款 5:微信APP支付 6:微信小程序支付
     * @param columnValue 字段的值
     * @param columnName  字段名称
     * @return paySet对象
     */
    public static PaySet getPaySet(PaySet paySet, String codeType, String columnValue, String columnName) {
        paySet.setCodeType(codeType);
        paySet.setColumnName(columnName);
        paySet.setColumnValue(columnValue);
        return paySet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
