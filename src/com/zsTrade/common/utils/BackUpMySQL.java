package com.zsTrade.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rguess on 2015/1/9.
 */
public class BackUpMySQL {

    private static Logger log = LoggerFactory.getLogger(BackUpMySQL.class);

    private static final String BASE_PATH_LINUX = "/ps/lice/sql/";
    private static final String BASE_PATH_WINDEWS = "E://";
    private static final String sqlname = DateUtils.getDate() + ".sql";

    public static void backwindows() {
        try {
            String mysql = "mysqldump -u root --password=root thinker > " +
                    BASE_PATH_WINDEWS + sqlname;
            java.lang.Runtime.getRuntime().exec("cmd /c " + mysql);
            log.info("数据库备份成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void backlinux() {
        try {
            String mysql = "mysqldump -u root --password=root thinker > " +
                    BASE_PATH_LINUX + sqlname;
            java.lang.Runtime.getRuntime().exec(
                    new String[]{"sh", "-c", mysql});
            log.info("数据库备份成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
