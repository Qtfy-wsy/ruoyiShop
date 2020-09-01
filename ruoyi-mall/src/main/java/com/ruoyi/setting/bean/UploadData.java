package com.ruoyi.setting.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 上传对象实体类
 */
@Data
public class UploadData {

    MultipartFile multipartFile;
    /**
     * 输入流
     */
    private InputStream inputStream;

    /**
     * 数据
     */
    private byte[] datas;


    /**
     * 用户上传的文件名
     */
    private String fileOriginName;

    /**
     * 上传的类型 0 图片 1 视频
     */
    private String type;


    /**
     * 构造上传对象
     *
     * @param inputStream 输入流
     * @param datas       数据
     * @return 返回上传对象
     */
    public static UploadData build(InputStream inputStream, byte[] datas, String fileOriginName, String type, MultipartFile multipartFile) {
        UploadData uploadData = new UploadData();
        uploadData.inputStream = inputStream;
        uploadData.datas = datas;
        uploadData.fileOriginName = fileOriginName;
        uploadData.type = type;
        uploadData.multipartFile = multipartFile;
        return uploadData;
    }

}
