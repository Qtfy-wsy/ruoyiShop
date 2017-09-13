/**
 * 
 */
package com.zsTrade.common.jackson;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * <p>Title: JsonValueProcessorImpl.java</p>
 * <p>Description: net.js.json 特殊值处理</p>
 * <p>Copyright: Copyright (c) 2014-2018</p>
 * <p>Company: shop.com</p>
 * @author lza
 * @date 2015年7月17日
 * @version 1.0
 */
public class JsonValueProcessorImpl implements JsonValueProcessor {
	
	private String format = "yyyy-MM-dd HH:mm:ss";

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if(value instanceof Date[]){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date[] date = (Date[])value;
			for (int i = 0; i < date.length; i++) {
				 obj[i] = sdf.format(date[i]);
			}
		}
		return obj;
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if(value instanceof java.util.Date){
			String str = new  SimpleDateFormat(format).format(value);
			return str;
		}
		return value.toString();
	}

}
