package com.zsTrade.common.utils;

public class Test {

	public static void main(String[] args) {
//		LogUtils.ACCESS_LOG.error("error");
//		LogUtils.ACCESS_LOG.debug("debug");
//		LogUtils.ACCESS_LOG.warn("earn");
//		LogUtils.ACCESS_LOG.info("info");
		
//		LogUtils.ERROR_LOG.error("error");
//		LogUtils.ERROR_LOG.debug("debug");
//		LogUtils.ERROR_LOG.warn("earn");
//		LogUtils.ERROR_LOG.info("info");
		
		LogUtils.BUSSINESS_LOG.error("error");
		LogUtils.BUSSINESS_LOG.debug("debug");
		LogUtils.BUSSINESS_LOG.warn("earn");
		LogUtils.BUSSINESS_LOG.info("info");
System.out.println(System.getProperty("user.dir"));
	}
}
