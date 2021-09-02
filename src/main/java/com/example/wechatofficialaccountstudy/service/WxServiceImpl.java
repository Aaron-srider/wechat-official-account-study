package com.example.wechatofficialaccountstudy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wechatofficialaccountstudy.WechatOfficialAccountStudyApplication;
import com.example.wechatofficialaccountstudy.model.AccessToken;
import com.example.wechatofficialaccountstudy.utils.WeChatUtil;
import org.springframework.stereotype.Service;

import java.security.AccessControlContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


@Service
public class WxServiceImpl implements WxService {
    public static final String TOKEN = "123456";
    public static final String APPID = "wx5092b941529f1a9c";
    public static final String SECRET = "803e83e31c328606345f207b2af04b29";

    public static AccessToken accessToken = null;

    @Override
    public boolean check(String sig, String times, String nonce)  {

        String[] arr = new String[]{TOKEN, times, nonce};
        Arrays.sort(arr);

        String str = arr[0] + arr[1] + arr[2];

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("sha1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] digest = messageDigest.digest(str.getBytes());

        StringBuilder stringBuilder = new StringBuilder();

        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (byte b : digest) {
            stringBuilder.append(chars[(b >> 4) & 15]);
            stringBuilder.append(chars[b & 15]);
        }

        String mySig = stringBuilder.toString();

        return mySig.equalsIgnoreCase(sig);
    }

    public String getAccessToken(String appid, String secret ) {

        if(accessToken == null || accessToken.isExpired() ) {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +appid+ "&secret=" + secret;

            String result = WeChatUtil.httpRequest(url, "GET", null);

            JSONObject jsonObject = JSONObject.parseObject(result);

            String token = (String) jsonObject.get("access_token");
            String expireIn = String.valueOf(jsonObject.getInteger("expires_in"));

            accessToken = new AccessToken(token, expireIn);
        }

        return accessToken.getToken();
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"touser\": \"oTnyb6Zodo_FVu-PytHebDxaTYwU\",\n" +
                "    \"template_id\": \"Abkry73zdcluUIC-e0rL_FDD9axcgTcvfIocn65Y_D4\",\n" +
                "    \"url\": \"http://weixin.qq.com/download\",\n" +
                "    \"topcolor\": \"#FF0000\",\n" +
                "    \"data\": {\n" +
                "        \"fist\": {\n" +
                "            \"value\": \"工单审批提醒\",\n" +
                "            \"color\": \"#173177\"\n" +
                "        },\n" +
                "        \"workOrder\": {\n" +
                "            \"value\": \"测试工单\",\n" +
                "            \"color\": \"#173177\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(json);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + new WxServiceImpl().getAccessToken(APPID, SECRET);
        String result = WeChatUtil.httpPost(url, jsonObject);
        System.out.println(result);
    }
}
