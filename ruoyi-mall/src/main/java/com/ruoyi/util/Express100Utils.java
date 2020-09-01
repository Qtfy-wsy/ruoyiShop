package com.ruoyi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 快递100工具类
 */
public class Express100Utils {

    /**
     * 快递100获取查询地址的接口地址
     */
    private final static String KUAIDI_URL = "https://www.kuaidi100.com/chaxun?com=%s&nu=%s";
    /**
     * H5查询物流信息地址
     */
    private final static String H5_KUAIDI_URL = "https://m.kuaidi100.com/app/query/?coname=lecshop&com=%s&nu=%s&callbackurl=%s";
    /**
     * 日志
     */
    static Logger logger = LoggerFactory.getLogger(Express100Utils.class);

    /**
     * 获得htmlapi的快递查询地址
     *
     * @param express100Code 快递代号
     * @param waybillCode    运单号
     */
    public static String getHtmlApiURl(String express100Code, String waybillCode) {
        logger.debug("getHtmlApiURl and express100Code:{} \r\n waybillCode:{}", express100Code, waybillCode);
        return String.format(KUAIDI_URL, express100Code, waybillCode);
    }

    /**
     * 生成H5查询物流信息url
     *
     * @param express100Code 快递代号
     * @param waybillCode    运单号
     * @param callBackUrl    回跳地址(http开头）
     * @return h5物流信息url
     */
    public static String formatH5Url(String express100Code, String waybillCode, String callBackUrl) {
        return String.format(H5_KUAIDI_URL, express100Code, waybillCode, callBackUrl);
    }

}
