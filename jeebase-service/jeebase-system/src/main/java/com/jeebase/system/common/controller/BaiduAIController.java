package com.jeebase.system.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.jeebase.common.annotation.auth.NoAuthentication;
import com.jeebase.common.base.Result;
import com.jeebase.ocr.baidu.domain.BaiduAccessToken;
import com.jeebase.ocr.baidu.domain.BaiduOcr;
import com.jeebase.ocr.baidu.service.IBaiduAIService;
import com.jeebase.ocr.baidu.util.BaiduOcrUtil;
import com.jeebase.ocr.baidu.util.HttpUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jeebase
 */
@RestController
@RequestMapping("/baidu/ai")
public class BaiduAIController {

    @Autowired
    private IBaiduAIService baiduAIService;

    @Value("${baidu.ai.clientId}")
    private String clientId;

    @Value("${baidu.ai.clientSecret}")
    private String clientSecret;

    /**
     * 查询上传token
     */
    @GetMapping("/token")
    @RequiresAuthentication
    public Result<Map<String, String>> tokenInfo() {
        BaiduAccessToken baiduToken = baiduAIService.queryAccessToken(clientId, clientSecret);
        Map<String, String> baiduMap = new HashMap<String, String>();
        baiduMap.put("token", baiduToken.getAccess_token());
        return new Result<Map<String, String>>().success().put(baiduMap);
    }

    /**
     * 识别身份证信息
     */
    @PostMapping("/ocr/idcard")
    @NoAuthentication
    public Result<Map<String, String>> ocrIdcard(@RequestBody BaiduOcr idCardData) {
        Map<String, String> baiduMap = new HashMap<String, String>();
        // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        // 图片
        String imgData = idCardData.getIdCardImgData();
        try {
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(imgData, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            BaiduAccessToken baiduToken = baiduAIService.queryAccessToken(clientId, clientSecret);
            String accessToken = baiduToken.getAccess_token();
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);
            baiduMap = BaiduOcrUtil.getHashMapByJson(result);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<Map<String, String>>().success().put(baiduMap);
    }

    /**
     * 识别银行卡信息
     */
    @PostMapping("/ocr/bankcard")
    @NoAuthentication
    public Result<Map<String, String>> ocrBankCard(@RequestBody BaiduOcr bankCardData) {

        Map<String, String> baiduMap = new HashMap<String, String>();
        // 银行卡识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";
        // 图片
        String imgData = bankCardData.getBankCardImgData();
        try {
            // 识别银行卡
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgData, "UTF-8");
            BaiduAccessToken baiduToken = baiduAIService.queryAccessToken(clientId, clientSecret);

            String accessToken = baiduToken.getAccess_token();
            String result = HttpUtil.post(idcardIdentificate, accessToken, params);

            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject bankResult = jsonObject.getJSONObject("result");

            baiduMap.put("cardNumber", bankResult.getString("bank_card_number"));
            baiduMap.put("bankNumber", bankResult.getString("bank_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<Map<String, String>>().success().put(baiduMap);
    }
}
