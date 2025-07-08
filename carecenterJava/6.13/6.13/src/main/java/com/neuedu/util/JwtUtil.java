package com.neuedu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.neuedu.core.NeueduException;
import com.neuedu.entity.User;

import java.util.Date;

public class JwtUtil {
    private static final String KEY = "dongruan";
    public static String create(User user) {
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("time", new Date())
                .sign(Algorithm.HMAC256(KEY));
    }
    public static long decode(String jwt) throws NeueduException {
        try {
            return JWT.require(Algorithm.HMAC256(KEY))
                    .build()
                    .verify(jwt)
                    .getClaim("id").asLong();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NeueduException("非法请求");
        }
    }
}
