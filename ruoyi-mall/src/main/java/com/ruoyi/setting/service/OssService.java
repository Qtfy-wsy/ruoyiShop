package com.ruoyi.setting.service;


import com.ruoyi.setting.bean.OssSetting;
import com.ruoyi.setting.bean.UploadData;

import java.util.List;

/**
 * Created by luozhuo on 2020/7/7.
 * 云存储服务接口
 */
public interface OssService {

    /**
     * 上传到云存储
     *
     * @param uploadDatas 上传的参数
     * @return 返回图片在云存储的地址
     */
    List<String> uploadToQqOss(List<UploadData> uploadDatas);

    /**
     * 上传到腾讯云存储（上传base64图片）
     *
     * @param uploadDatas 上传的参数
     * @return 返回图片在云存储的地址
     */
    List<String> uploadToQqOssForBase64(List<UploadData> uploadDatas);

    /**
     * 上传到腾讯云存储
     *
     * @param uploadDatas 上传的参数
     * @return 返回图片在云存储的地址
     */
    List<String> uploadToOss(List<UploadData> uploadDatas);

    /**
     * 上传到云存储（上传base64图片）
     *
     * @param uploadDatas 上传的参数
     * @return 返回图片在云存储的地址
     */
    List<String> uploadToOssForBase64(List<UploadData> uploadDatas);

    /**
     * 查询系统设置的云存储信息
     *
     * @return 返回云存储信息
     */
    OssSetting queryOssSetting(String activeName);

    /**
     * 修改云存储设置信息
     *
     * @param OssSetting 云存储设置实体
     * @return 成功返回1  失败返回0
     */
    int updateOss(OssSetting OssSetting);
}
