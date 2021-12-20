package com.ruoyi.setting.mapper;


import com.ruoyi.setting.bean.OssSetting;
import org.springframework.stereotype.Repository;

/**
 * Created by luozhuo on 2020/7/7
 * 云存储设置数据库接口
 */
@Repository
public interface OssMapper {

    /**
     * 查询系统设置的云存储信息
     *
     * @return 返回云存储信息
     */

    OssSetting queryOssSetting(String activeName);

    /**
     * 修改又拍云信息
     *
     * @param ossSetting 云存储信息
     * @return 成功返回1 失败返回0
     */

    int updateOss(OssSetting ossSetting);
}
