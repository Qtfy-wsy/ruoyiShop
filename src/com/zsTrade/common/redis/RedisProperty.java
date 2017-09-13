package com.zsTrade.common.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : sz[1439226817].
 * @version : 1.0
 * @created on  : 2016-07-06,  11:09.
 * @copyright : Copyright(c) 2015  北京瑞华信通信息技术有限公司
 */
public class RedisProperty {
   
    private static int port;
    private static String ip="";
   

    static {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties");) {
            Properties p = new Properties();
            p.load(in);
            //从一卡通文件中读取一卡通地址
            port = Integer.parseInt(p.getProperty("redis.port"));
            ip = p.getProperty("redis.ip");
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public static int getPort() {
		return port;
	}


	public static void setPort(int port) {
		RedisProperty.port = port;
	}


	public static String getIp() {
		return ip;
	}


	public static void setIp(String ip) {
		RedisProperty.ip = ip;
	}

    
   
}
