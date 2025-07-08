package com.neuedu.service;

public interface CodeService{
    void saveCode(String username, String code) ;// 保存验证码到Redis
    boolean verifyCode(String username, String code); // 校验验证码
}
