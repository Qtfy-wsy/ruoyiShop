package com.ruoyi.common.utils;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.ruoyi.common.md5.MD5;
import com.ruoyi.common.utils.bean.OssYunConf;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Author: SunJunxian
 * @Date: 2018/12/17
 * @Description: java实现腾讯云存储服务（COSClient）
 * @REGIONID 区域
 * @KEY 上传上云之后的名字
 * @KEY01 需要删除的文件
 */
public class QqOssClient {


    private static final String ACCESSKEY = "AKID1oQ4hjfItRjjplnu6HFwouHOEo7AOQgl";
    private static final String SECRETKEY = "ysBEBsLZonV1KPIu3CUWWGfngdIhpiKn";
    private static final String BUCKETNAME = "wanshunfu-1301582899";
    private static final String APPID = "12517827811";
    private static final String REGIONID = "ap-guangzhou";
    private static final String KEY = "MyFile1/zookeeper-3.4.8.tar.gz";
    private static final String KEY01 = "MyFile1/1.jpg";

    /**
     * 上传腾讯云
     *
     * @param file
     * @param bytes 图片字节
     * @param type  上传的类型 0 图片 1 视频
     * @return 返回图片在又拍云的地址
     */
    public static String uploadToQqOss(OssYunConf cloudStorageConfig, MultipartFile file, byte[] bytes, String fileOriginName, String type) {
        String url = null;

        String accessKey = cloudStorageConfig.getAccessKeyId();
        String secretKey = cloudStorageConfig.getAccessKeySecret();
        String bucket = cloudStorageConfig.getEndPoint();
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = cloudStorageConfig.getBucketName();
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        //  String suffix = fileOriginName.substring(fileOriginName.lastIndexOf(".")).toLowerCase();
        //获取腾讯云路径前缀
        String dir = cloudStorageConfig.getPrefix();

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = null;
        try {
            // 获取字符串的MD5结果，file.getBytes()--输入的字符串转换成字节数组
            String md5 = MD5.getMessageDigest(file.getBytes());
            String filePath = String.format("%1$s/%2$s%3$s", dir, md5, fileOriginName);
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = filePath;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            String qCloudDomain = cloudStorageConfig.getAddress();
            url = String.format("%1$s/%2$s", qCloudDomain, filePath);

        } catch (IOException e) {

        } finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }

        return url;
    }

    /**
     * 初始化CosClient相关配置， appid、accessKey、secretKey、region
     *
     * @return
     */
    public static COSClient getCosClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(ACCESSKEY, SECRETKEY);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region(REGIONID));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        //String bucketName = BUCKETNAME;
        return cosClient;
    }

    /**
     * 上传文件
     *
     * @return //绝对路径和相对路径都OK
     */
    public static String uploadFile() {
//        File localFile = new File("E:\\software\\JavaProject\\demo\\demo20\\src\\main\\resources\\1.jpg");
        File localFile = new File("C:\\Users\\DEL\\Desktop\\pic\\logo.png");
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETNAME, localFile.getName(), localFile);

        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia),一般为标准的
        putObjectRequest.setStorageClass(StorageClass.Standard);

        COSClient cc = getCosClient();
        try {
            PutObjectResult putObjectResult = cc.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();

            System.out.println(etag);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cc.shutdown();
        return null;
    }

    /**
     * 下载文件
     *
     * @param bucketName
     * @param key
     * @return
     */
    public static String downLoadFile(String bucketName, String key) {
        File downFile = new File("E:\\software\\1.jpg");
        COSClient cc = getCosClient();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);

        ObjectMetadata downObjectMeta = cc.getObject(getObjectRequest, downFile);
        cc.shutdown();
        String etag = downObjectMeta.getETag();
        return etag;
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param key
     */
    public static void deleteFile(String bucketName, String key) {
        COSClient cc = getCosClient();
        try {
            cc.deleteObject(bucketName, key);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cc.shutdown();
        }

    }

    /**
     * 创建桶
     *
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static Bucket createBucket(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        Bucket bucket = null;
        try {
            bucket = cc.createBucket(bucketName);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
        }
        return bucket;
    }

    ;

    public static String uploadToUpYunForBase64(OssYunConf cloudStorageConfig, InputStream inputStream, byte[] bytes, String fileOriginName, byte[] bytes1) {
        String url = null;

        String accessKey = cloudStorageConfig.getAccessKeyId();
        String secretKey = cloudStorageConfig.getAccessKeySecret();
        String bucket = cloudStorageConfig.getEndPoint();
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = cloudStorageConfig.getBucketName();
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        //  String suffix = fileOriginName.substring(fileOriginName.lastIndexOf(".")).toLowerCase();
        //获取腾讯云路径前缀
        String dir = cloudStorageConfig.getPrefix();

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        try {
            // 获取字符串的MD5结果，file.getBytes()--输入的字符串转换成字节数组
            String md5 = MD5.getMessageDigest(bytes);
            String filePath = String.format("%1$s/%2$s%3$s", dir, md5, fileOriginName);

            // 指定要上传到 COS 上的路径
            String key = filePath;
            ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setContentType("image/jpeg");
            String backUrl = "";
            metadata.setContentLength(bytes.length);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new ByteArrayInputStream(bytes),
                    metadata);

            // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
            putObjectRequest.setStorageClass(StorageClass.Standard_IA);
            COSClient cc = getCosClient();
            try {
                PutObjectResult putObjectResult1 = cc.putObject(putObjectRequest);
                // putobjectResult会返回文件的etag
                backUrl = cloudStorageConfig.getAddress() + "/" + key;
                return backUrl;
            } catch (CosServiceException e) {
                e.printStackTrace();
            } catch (CosClientException e) {
                e.printStackTrace();
            }
            // 关闭客户端
            cc.shutdown();

            return backUrl;

        } catch (Exception e) {

        } finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }

        return url;
    }

    /**
     * 判断桶是否存在
     *
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static boolean doesBucketExist(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        boolean bucketExistFlag = cc.doesBucketExist(bucketName);
        return bucketExistFlag;
    }

    ;

    /**
     * 查看桶文件
     *
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static ObjectListing listObjects(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();

        // 获取 bucket 下成员（设置 delimiter）
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        // 设置 list 的 prefix, 表示 list 出来的文件 key 都是以这个 prefix 开始
        listObjectsRequest.setPrefix("");
        // 设置 delimiter 为/, 即获取的是直接成员，不包含目录下的递归子成员
        listObjectsRequest.setDelimiter("/");
        // 设置 marker, (marker 由上一次 list 获取到, 或者第一次 list marker 为空)
        listObjectsRequest.setMarker("");
        // 设置最多 list 100 个成员,（如果不设置, 默认为 1000 个，最大允许一次 list 1000 个 key）
        listObjectsRequest.setMaxKeys(100);

        ObjectListing objectListing = cc.listObjects(listObjectsRequest);
        // 获取下次 list 的 marker
        String nextMarker = objectListing.getNextMarker();
        // 判断是否已经 list 完, 如果 list 结束, 则 isTruncated 为 false, 否则为 true
        boolean isTruncated = objectListing.isTruncated();
        List<COSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (COSObjectSummary cosObjectSummary : objectSummaries) {
            // get file path
            String key = cosObjectSummary.getKey();
            // get file length
            long fileSize = cosObjectSummary.getSize();
            // get file etag
            String eTag = cosObjectSummary.getETag();
            // get last modify time
            Date lastModified = cosObjectSummary.getLastModified();
            // get file save type
            String StorageClassStr = cosObjectSummary.getStorageClass();
        }
        return objectListing;
    }

    ;

    /**
     * 查询一个 Bucket 所在的 Region。
     *
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static String getBucketLocation(String bucketName) throws CosClientException, CosServiceException {
        COSClient cosClient = getCosClient();
        // bucket 的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String location = cosClient.getBucketLocation(bucketName);
        return location;
    }

    public static void main(String[] args) {
        uploadFile();
//          downLoadFile(BUCKETNAME , KEY);
        // deleteFile(BUCKETNAME , KEY01);
//        createBucket("sunjunxian01-1251782781");
        //deleteBucket();
//        doesBucketExist("sunjunxian01-1251782781");
//        System.out.println(listObjects(BUCKETNAME));
        //System.out.println("BUCKETNAME的位置：" + getBucketLocation(BUCKETNAME));
    }

    /**
     * 删除桶
     *
     * @param bucketName
     * @throws CosClientException
     * @throws CosServiceException
     */
    public void deleteBucket(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        try {
            cc.deleteBucket(bucketName);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
