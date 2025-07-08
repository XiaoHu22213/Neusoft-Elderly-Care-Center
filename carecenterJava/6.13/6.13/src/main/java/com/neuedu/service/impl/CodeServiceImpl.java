package com.neuedu.service.impl;

import com.neuedu.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 保存验证码到Redis
//    @Cacheable(value="Code",key="'code'")
    public void saveCode(String username, String code) {
        // 保存验证码，并设置过期时间为5分钟
        redisTemplate.opsForValue().set(username, code, 60, TimeUnit.MINUTES);
    }

    // 校验验证码
    public boolean verifyCode(String username, String code) {
        String storedCode = redisTemplate.opsForValue().get(username);
        return code != null && code.equals(storedCode);
    }
}
