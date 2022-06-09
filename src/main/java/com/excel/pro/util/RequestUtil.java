package com.excel.pro.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestUtil {
    public static String getJsonObjectData(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader();) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonObjectData = sb.toString();
        return jsonObjectData;
    }
    public static String getObjectValue(String jsonObjectData,String key){
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonObjectData);
        Map<String, Object> mapJson = net.sf.json.JSONObject.fromObject(jsonObject);
        String value ="";
        for(Map.Entry<String,Object> entry : mapJson.entrySet()){
            if(entry.getKey().equals(key)){
                Object strval1 = entry.getValue();
                System.out.println(key+":"+entry.getValue()+"\n");
                value = entry.getValue()+"";
            }
        }
        return value;
    }
}
