package com.ruoyi;

import com.ruoyi.common.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class RuoYiApplication {
    public static void main(String[] args)  throws UnknownHostException {
        System.out.println("start11-------------");
        // SpringApplication.run(MallPortalApplication.class, args);
        ConfigurableApplicationContext application = SpringApplication.run(RuoYiApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");

        log.info("\n----------------------------------------------------------\n\t" +
                "Application mallplus-Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port  + "/\n\t" +
                "External: \thttp://" + ip + ":" + port  + "/\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port  + "/swagger-ui.html\n\t" +
                "https://gitee.com/zscat/mallplus/wikis/pages/preview?sort_id=2168368&doc_id=326093"+
                "-----------------页面请部署 mallplus-admin-web-----------------------------------------");
        System.out.println("end-------------");
    }


    /**
     * 序列号生成器
     */
    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 1);
        return snowflakeIdWorker;
    }
}
