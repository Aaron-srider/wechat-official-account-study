package com.example.wechatofficialaccountstudy.service;

import java.security.NoSuchAlgorithmException;

public interface WxService {

    /**
     *
     * @param sig 签名
     * @param times 时间戳
     * @param nonce 随机数
     * @return 验证通过返回true，否则false
     */
    boolean check(String sig, String times, String nonce);
}
