package com.ruoyi.common.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.PutObjectRequest;
import com.ruoyi.common.utils.bean.OssYunConf;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by 商城
 * 又拍云上传工具
 */
public class YunUploadUtils {

    private static final YunUploadUtils INSTANCE = new YunUploadUtils();
    /**
     * 调试工具
     */
    private Logger logger = LoggerFactory.getLogger(YunUploadUtils.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    private YunUploadUtils() {
    }

    public static YunUploadUtils getInstance() {
        return INSTANCE;
    }


    /**
     * 获取文件保存路径
     *
     * @param fileOriginName 用户上传文件名
     * @return 文件保存路径
     */
    private String getFilePath(String fileOriginName) {
        String fileType = "jpg";
        if (!StringUtils.isEmpty(fileOriginName) && fileOriginName.contains(".")) {
            fileType = fileOriginName.substring(fileOriginName.lastIndexOf(".") + 1);
        }
        String fileName = String.valueOf(System.currentTimeMillis()) + "." + fileType;
        String date = simpleDateFormat.format(Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).getTime());
        return "/" + date + "/" + fileName;
    }

    /**
     * 判断是否是图片
     *
     * @param bytes 字节数组
     * @return 是图片返回true  否则返回false
     */
    private boolean isPicture(byte[] bytes) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
            return image != null;
        } catch (IOException e) {
            logger.error("file is not a picture ...", e);
            return false;
        }
    }

    /**
     * 上传腾讯云
     *
     * @param file
     * @param bytes 图片字节
     * @param type  上传的类型 0 图片 1 视频
     * @return 返回图片在又拍云的地址
     */
    public String uploadToQqOss(OssYunConf cloudStorageConfig, MultipartFile file, byte[] bytes, String fileOriginName, String type) {
        return QqOssClient.uploadToQqOss(cloudStorageConfig, file, bytes, fileOriginName, type);
    }

    /**
     * 上传又拍云
     *
     * @param upYunConf   又拍云设置
     * @param inputStream 输入流
     * @param bytes       图片字节
     * @return 返回图片在又拍云的地址
     */
    public String uploadToQqForBase64(OssYunConf upYunConf, InputStream inputStream, byte[] bytes, String fileOriginName) {
        logger.debug("Being to uploadToUpYun.....");
        if (org.apache.commons.lang3.ArrayUtils.isEmpty(bytes)) {
            logger.error("uploadToUpYun fail due to bytes is empty ....");
            return "";
        }
        return QqOssClient.uploadToUpYunForBase64(upYunConf, inputStream, bytes, fileOriginName, bytes);
    }

    /**
     * 上传阿里云
     *
     * @param inputStream 输入流
     * @param bytes       图片字节
     * @param type        上传的类型 0 图片 1 视频
     * @return 返回图片在又拍云的地址
     */
    public String uploadToOssYun(OssYunConf config, InputStream inputStream, byte[] bytes, String fileOriginName, String type) {
        String url = null;
        try {
            url = upload(config.getAccessKeyId(), config.getAccessKeySecret(), config.getBucketName(), config.getEndPoint(),
                    config.getPrefix(), getKey(config.getBucketName(), ""), inputStream);

        } catch (Exception e) {
            return e.getMessage();
        }
        return url;
    }

    /**
     * 上传又拍云
     *
     * @param inputStream 输入流
     * @param bytes       图片字节
     * @return 返回图片在又拍云的地址
     */
    public String uploadToOssYunForBase64(OssYunConf config, InputStream inputStream, byte[] bytes, String fileOriginName) {
        String url = null;
        try {
            url = upload(config.getAccessKeyId(), config.getAccessKeySecret(), config.getBucketName(), config.getEndPoint(),
                    config.getPrefix(), getKey(config.getBucketName(), ""), inputStream);

        } catch (Exception e) {
            return e.getMessage();
        }
        return url;
    }

    /**
     * 上传文件<>基础方法</>
     *
     * @param accessKeyId     授权 ID
     * @param accessKeySecret 授权密钥
     * @param bucketName      桶名
     * @param endpoint        节点名
     * @param styleName       样式名
     * @param key             文件名
     * @param inputStream     文件流
     * @return 访问路径 ，结果为null时说明上传失败
     */
    public String upload(String accessKeyId, String accessKeySecret, String bucketName, String endpoint, String
            styleName, String key, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        String url = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint.trim(), accessKeyId.trim(), accessKeySecret.trim());
        try {
            // 带进度条的上传
            ossClient.putObject(new PutObjectRequest(bucketName, key, inputStream));
        } catch (OSSException oe) {
            oe.printStackTrace();
            key = null;
        } catch (ClientException ce) {
            ce.printStackTrace();
            key = null;
        } catch (Exception e) {
            e.printStackTrace();
            key = null;
        } finally {
            ossClient.shutdown();
        }
        if (key != null) {
            // 拼接文件访问路径。由于拼接的字符串大多为String对象，而不是""的形式，所以直接用+拼接的方式没有优势
            StringBuffer sb = new StringBuffer();
            sb.append("http://").append(bucketName).append(".").append(endpoint).append("/").append(key);
            if (StringUtils.isNotBlank(styleName)) {
                sb.append("/").append(styleName);
            }
            url = sb.toString();
        }
        return url;
    }

    /**
     * 获取文件名（bucket里的唯一key）
     * 上传和删除时除了需要bucketName外还需要此值
     *
     * @param prefix 前缀（非必传），可以用于区分是哪个模块或子项目上传的文件
     * @param suffix 后缀（非必传）, 可以是 png jpg 等
     * @return
     */
    public String getKey(final String prefix, final String suffix) {
        String defaultSuffix = "jpg";
        //生成uuid,替换 - 的目的是因为后期可能会用 - 将key进行split，然后进行分类统计
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.parseDateToStr("yyyyMMdd", new Date()) + "-" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "-" + path;
        }
        if (suffix != null) {
            if (suffix.startsWith(".")) {
                path = path + suffix;
            } else {
                path = path + "." + defaultSuffix;
            }
        }
        return path;
    }

}
