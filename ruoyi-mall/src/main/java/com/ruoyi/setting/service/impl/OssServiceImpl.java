package com.ruoyi.setting.service.impl;


import com.ruoyi.common.utils.YunUploadUtils;
import com.ruoyi.setting.bean.OssSetting;
import com.ruoyi.setting.bean.UploadData;
import com.ruoyi.setting.mapper.OssMapper;
import com.ruoyi.setting.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by luozhuo on 2020/7/7.
 * 云存储服务实现类
 */
@Service
public class OssServiceImpl implements OssService {

    private Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);

    @Autowired
    private OssMapper ossMapper;

    @Override
    public List<String> uploadToQqOss(List<UploadData> uploadDatas) {
        logger.debug("Begin to uploadToQqOss....");
        if (CollectionUtils.isEmpty(uploadDatas)) {
            logger.error("uploadDatas fail  due to params is empty...");
            return Arrays.asList("");
        }
        return uploadDatas.stream().filter(Objects::nonNull).map(uploadData -> YunUploadUtils.getInstance().uploadToQqOss(queryOssSetting().getUpYunConf(), uploadData.getMultipartFile(), uploadData.getDatas(), uploadData.getFileOriginName(), uploadData.getType())).collect(Collectors.toList());

    }

    @Override
    public List<String> uploadToQqOssForBase64(List<UploadData> uploadDatas) {
        if (CollectionUtils.isEmpty(uploadDatas)) {
            logger.error("uploadToQqOssForBase64 fail  due to params is empty...");
            return Arrays.asList("");
        }
        return uploadDatas.stream().filter(Objects::nonNull).map(uploadData -> YunUploadUtils.getInstance().
                uploadToQqForBase64(queryOssSetting().getUpYunConf(), uploadData.getInputStream(), uploadData.getDatas(), uploadData.getFileOriginName())).collect(Collectors.toList());

    }

    @Override
    public List<String> uploadToOss(List<UploadData> uploadDatas) {
        logger.debug("Begin to uploadToOss....");
        if (CollectionUtils.isEmpty(uploadDatas)) {
            logger.error("uploadDatas fail  due to params is empty...");
            return Arrays.asList("");
        }
        return uploadDatas.stream().filter(Objects::nonNull).map(uploadData -> YunUploadUtils.getInstance().uploadToOssYun(queryOssSetting().getUpYunConf(), uploadData.getInputStream(), uploadData.getDatas(), uploadData.getFileOriginName(), uploadData.getType())).collect(Collectors.toList());

    }

    @Override
    public List<String> uploadToOssForBase64(List<UploadData> uploadDatas) {
        if (CollectionUtils.isEmpty(uploadDatas)) {
            logger.error("uploadToOssForBase64 fail  due to params is empty...");
            return Arrays.asList("");
        }

        return uploadDatas.stream().filter(Objects::nonNull).map(uploadData -> YunUploadUtils.getInstance().
                uploadToQqForBase64(queryOssSetting().getUpYunConf(), uploadData.getInputStream(), uploadData.getDatas(), uploadData.getFileOriginName())).collect(Collectors.toList());

    }

    @Override
    public OssSetting queryOssSetting() {
        logger.debug("Begin to queryUpYunSetting....");
        return ossMapper.queryOssSetting();
    }

    @Override
    public int updateOss(OssSetting ossSetting) {
        logger.error("updateOss and OssSetting:{}", ossSetting);
        return ossMapper.updateOss(ossSetting);
    }
}
