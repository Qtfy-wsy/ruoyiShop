package com.zsTrade.common.constant;


public class ZsCatConstant {

	/**
	 * 多站式id
	 */
	public static final String SITEID = "SITEID";
	public static final String IMGSERVER = "http://image.zscat.com";
	public static final String pictureSaveFilePath ="C:\\info\\zscat\\upload";
	public static final String pictureProjectSaveFilePath ="C:\\info\\zscat\\upload\\project";
	public static final String pictureBrandSaveFilePath ="C:\\info\\zscat\\upload\\brand";
	//public static final String CMSpictureSaveFilePath ="C:\\info\\zscat\\upload\\"+RedisUtils.get(SITEID, "1");
	//订单初始状态 
	public static final int ORDER_ONE = 1;
	//付款成功
	public static final int ORDER_TWO = 2;
	//已发货 
	public static final int ORDER_THREE = 3;
		//已收货
	public static final int ORDER_FOUR = 4;
	//已删除
	public static final int ORDER_NiNe = 9;
}
