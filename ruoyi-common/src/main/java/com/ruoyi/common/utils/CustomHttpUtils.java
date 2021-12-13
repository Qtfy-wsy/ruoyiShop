package com.ruoyi.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义http工具类
 *
 * @author 伊甸园商城 created on 2020/4/14
 */
@Slf4j
public class CustomHttpUtils {


    /**
     * 执行GET方法请求数据
     *
     * @param url 请求地址
     * @return 返回数据
     */
    public static String doGet(String url) {
        log.debug("CustomHttpUtils.doGet and url:{}", url);
        return getResult(getHttpClient(), new HttpGet(url));
    }

    /**
     * 执行POST方法form方式请求数据
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 返回数据
     */
    public static String postForm(String url, Map<String, String> params) {
        log.debug("CustomHttpUtils.postForm and url:{} \r\n params:{}", url, params);
        return getFormRequestResult(getHttpClient(), new HttpPost(url), params);
    }


    /**
     * 执行POST方法json方式请求数据
     *
     * @param url        请求地址
     * @param jsonString 请求参数（json字符串）
     * @return 返回数据
     */
    public static String postJson(String url, String jsonString) {
        log.debug("CustomHttpUtils.postJson and url:{} \r\n jsonString:{}", url, jsonString);
        return getJsonRequestResult(jsonString, getHttpClient(), new HttpPost(url));
    }


    /**
     * 执行DELETE方法请求数据
     *
     * @param url 请求地址
     * @return 返回数据
     */
    public static String doDelete(String url) {
        log.debug("CustomHttpUtils.doDelete and url:{}", url);
        return getResult(getHttpClient(), new HttpDelete(url));
    }


    /**
     * 执行put方法form方式请求数据
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 返回数据
     */
    public static String putForm(String url, Map<String, String> params) {
        log.debug("CustomHttpUtils.putForm and url:{} \r\n params:{}", url, params);
        return getFormRequestResult(getHttpClient(), new HttpPut(url), params);
    }


    /**
     * 执行put方法json方式请求数据
     *
     * @param url        请求地址
     * @param jsonString 请求参数（json字符串）
     * @return 返回数据
     */
    public static String putJson(String url, String jsonString) {
        log.debug("CustomHttpUtils.putJson and url:{} \r\n jsonString:{}", url, jsonString);
        return getJsonRequestResult(jsonString, getHttpClient(), new HttpPut(url));
    }


    /**
     * 从cookie中获取token
     */
    public static String getTokenFromCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return "";
        }
        for (Cookie temp : cookies) {
            if (temp.getName().equals("Authorization")) {
                token = temp.getValue();
            }
        }
        return token;
    }

    /**
     * 从header中获取token
     */
    public static String getTokenFromHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = "";
        // 认证信息在header 中的key
        final String authHeader = request.getHeader("Authorization");
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.info("getToken from header fail....");
        } else {
            token = authHeader.substring(7);
        }
        return token;
    }

    /**
     * 通用获取token(如果request头中有Auth信息，则返回头中的token，否则返回cookie中的token)
     *
     * @return token
     */
    private static String getTokenCommon() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!StringUtils.isEmpty(request.getHeader("Authorization"))) {
            log.info("getTokenCommon:getAuthorization from header is not empty");
            return getTokenFromHeader();
        }
        return getTokenFromCookie();
    }

//    /**
//     * 从token获取userName
//     *
//     * @param token     token
//     * @param publicKey 公钥
//     * @return userName
//     */
//    public static String getUserNameFromToken(String token, String publicKey) {
//        log.debug("getUserNameFromToken and token:{} \r\n publicKey:{}", token, publicKey);
//        if (StringUtils.isEmpty(token)) {
//            return "";
//        }
//        Jwt jwt = null;
//        try {
//            SignatureVerifier verifier = new RsaVerifier(publicKey);
//            jwt = JwtHelper.decodeAndVerify(token, verifier);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        if (Objects.nonNull(jwt)) {
//            log.debug("getUserNameFromToken and claims:{}", jwt.getClaims());
//            JSONObject claims = JSONObject.parseObject(jwt.getClaims());
//            // 判断有效期是否过期
//            if (Long.parseLong(claims.getString("exp")) > new Date().getTime() / 1000) {
//                return claims.getString("user_name");
//            }
//        }
//        return "";
//    }

    /**
     * 下载文件
     *
     * @param out 输出流
     * @param url 文件地址
     */
    public static void downLoadFile(OutputStream out, String url) {
        log.debug("downLoadFile and url:{}", url);
        if (StringUtils.isEmpty(url)) {
            log.debug("downLoadFile fail : url is empty");
            return;
        }
        InputStream inputStream = null;
        try {
            inputStream = getInputStreamResultFormUrl(url);
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = inputStream.read(buffer);
                //4.写到输出流(out)中
                out.write(buffer, 0, b);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                out.close();
                out.flush();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public static InputStream getInputStreamResultFormUrl(String url) throws IOException {
        return getInputStreamResult(getHttpClient(), new HttpGet(url));
    }

    /**
     * 设置下载信息
     *
     * @param response 返回
     * @param fileName 文件名
     */
    public static void setDownloadInfo(HttpServletResponse response, String fileName) {
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
    }

    /**
     * 获取httpClient
     */
    private static HttpClient getHttpClient() {
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        return HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
    }


    /**
     * 获取json请求返回值
     *
     * @param jsonString  请求参数
     * @param httpClient  http客户端
     * @param httpRequest 请求对象
     * @return 请求返回
     */
    private static String getJsonRequestResult(String jsonString, HttpClient httpClient, HttpEntityEnclosingRequestBase httpRequest) {
        String result = null;
        //设置token
//        httpRequest.setHeader("Authorization", "Bearer " + getTokenCommon());
        httpRequest.setHeader("Content-type", "application/json; charset=utf-8");
        StringEntity requestParam = new StringEntity(jsonString, "UTF-8");
        requestParam.setContentType("application/json");
        try {
            httpRequest.setEntity(requestParam);
            result = EntityUtils.toString(httpClient.execute(httpRequest).getEntity(), "UTF-8");//取出应答字符串
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.debug("getJsonRequestResult and result:{}", result == null ? "" : result);
        return result;
    }

    /**
     * 获取form请求返回值
     *
     * @param formParam   请求参数
     * @param httpClient  http客户端
     * @param httpRequest 请求对象
     * @return 请求返回
     */
    private static String getFormRequestResult(HttpClient httpClient, HttpEntityEnclosingRequestBase httpRequest, Map<String, String> formParam) {
        String result = null;
        List<NameValuePair> params = new ArrayList<>();
        if (Objects.nonNull(formParam)) {
            formParam.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
        }
//        httpRequest.setHeader("Authorization", "Bearer " + getTokenCommon());
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            result = EntityUtils.toString(httpClient.execute(httpRequest).getEntity(), "UTF-8");//取出应答字符串
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.debug("getFormRequestResult and result:{}", result == null ? "" : result);
        return result;
    }

    /**
     * 获取http请求返回
     *
     * @param httpClient  http客户端
     * @param httpRequest 请求对象
     * @return 请求返回
     */
    private static String getResult(HttpClient httpClient, HttpUriRequest httpRequest) {
        String result = null;
//        httpRequest.setHeader("Authorization", "Bearer " + getTokenCommon());
        try {
            result = EntityUtils.toString(httpClient.execute(httpRequest).getEntity(), Charset.forName("utf-8"));//取出应答字符串
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.debug("getResult and result:{}", result == null ? "" : result);
        return result;
    }

    /**
     * 获取http请求输入流返回
     *
     * @param httpClient  http客户端
     * @param httpRequest 请求对象
     * @return 请求返回
     */
    private static InputStream getInputStreamResult(HttpClient httpClient, HttpUriRequest httpRequest) throws IOException {
        return httpClient.execute(httpRequest).getEntity().getContent();
    }

}
