package com.jeebase.ocr.baidu.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class BaiduOcrUtil {

    public static HashMap<String,String> getHashMapByJson(String jsonResult){
        HashMap map = new HashMap<String,String>();
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonResult);
            JSONObject words_result= jsonObject.getJSONObject("words_result");
            Iterator<String> it = words_result.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                JSONObject result = words_result.getJSONObject(key);
                String value=result.getString("words");
                switch (key){
                    case "姓名":
                        map.put("name",value);
                        break;
                    case "民族":
                        map.put("nation",value);
                        break;
                    case "住址":
                        map.put("address",value);
                        break;
                    case "公民身份号码":
                        map.put("IDCard",value);
                        break;
                    case "出生":
                        map.put("Birth",value);
                        break;
                    case "性别":
                        map.put("sex",value);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
