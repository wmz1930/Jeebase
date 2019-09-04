package com.jeebase.system.common.controller;

import com.jeebase.common.annotation.auth.NoAuthentication;
import com.jeebase.common.base.Result;
import com.jeebase.system.config.QiNiuProperties;
import com.qiniu.util.Auth;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jeebase
 */
@RestController
@RequestMapping("/qiniu")
public class QiNiuYunController {

    /**
     * 七牛配置
     */
    @Autowired
    private QiNiuProperties qiniu;

    /**
     * 查询上传token
     */
    @GetMapping("/token")
    @NoAuthentication
    public Result<Map<String, String>> tokenInfo() {
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        String upToken = auth.uploadToken(qiniu.getBucket());
        Map<String, String> qiniuMap = new HashMap<>();
        qiniuMap.put("token", upToken);
        qiniuMap.put("bucket", qiniu.getBucket());
        qiniuMap.put("baseUrl", qiniu.getBaseUrl());
        return new Result<Map<String, String>>().success().put(qiniuMap);
    }

    /**
     * 查询私有控件上传token
     */
    @GetMapping("/private/token")
    @RequiresAuthentication
    public Result<Map<String, String>> privateTokenInfo() {
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        String upToken = auth.uploadToken(qiniu.getBucketPrivate());
        Map<String, String> qiniuMap = new HashMap<>();
        qiniuMap.put("token", upToken);
        qiniuMap.put("bucket", qiniu.getBucketPrivate());
        qiniuMap.put("baseUrl", qiniu.getBaseUrlPrivate());
        return new Result<Map<String, String>>().success().put(qiniuMap);
    }


    /**
     * 查询私有图片可访问链接
     */
    @PostMapping("/private/image")
    @RequiresAuthentication
    public Result<?> privateImage(@RequestBody Map<String,String> params ) {
        String imgUrl = params.get("imgUrl");
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        //1小时，可以自定义链接过期时间
        long expireInSeconds = 3600;
        String finalUrl = auth.privateDownloadUrl(imgUrl, expireInSeconds);
        Map<String, String> qiniuMap = new HashMap<>();
        qiniuMap.put("imgUrl", finalUrl);
        return new Result<Map<String, String>>().success().put(qiniuMap);
    }

    /**
     * 当通过https访问公共图片时，需要
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/images")
    @NoAuthentication
    public void images(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeKey = request.getParameter("key");
        if (codeKey != null && !codeKey.trim().isEmpty()) {
            response.setContentType("image/gif");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);

            //new一个URL对象
            URL url = new URL("http://img.jeebase.cn/" + codeKey);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(120 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //创建输出流
            ServletOutputStream outStream = response.getOutputStream();
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();
        }

    }

    public static byte[] readInputStream(InputStream inStream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
