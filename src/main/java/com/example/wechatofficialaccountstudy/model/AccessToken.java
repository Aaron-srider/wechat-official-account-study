package com.example.wechatofficialaccountstudy.model;


import lombok.Data;

import java.util.Date;

@Data
public class AccessToken {
    private String token;

    /**
     * 过期时间，单位毫秒
     */
    private String expireIn;

    public AccessToken(String token, String expireIn) {
        this.token = token;
        Long s = Long.valueOf(expireIn) * 1000 + System.currentTimeMillis();
        this.expireIn = s + "";
    }

    public boolean isExpired() {
        Long now = new Date().getTime() / 1000;
        return System.currentTimeMillis() >  Long.valueOf(expireIn);
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        AccessToken accessToken = new AccessToken("123456", "7200");
        System.out.println(accessToken);
        System.out.println(accessToken.isExpired());
    }


}
