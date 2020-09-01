package com.ruoyi.common.utils.bean;

import lombok.Data;

/**
 * 梦网云短信发送返回参数
 */
@Data
public class MonYunSmsResponse {
    /**
     * 短信发送请求处理结果：0代表成功；非0代表失败。错误代码详见附录
     */
    Integer result;
    /**
     * 平台流水号：非0代表64位整型，对应Java和C#的long，不可用int解析。result非0时，msgid为0
     */
    Long msgid;
    /**
     * 用户自定义流水号：默认与请求报文multimt包结构内第一条数据的custid保持一致，若请求报文中没有custid参数或值为空，则返回由梦网生成的代表本批短信的唯一编号，result非0时，custid为空
     */
    String custid;

}
