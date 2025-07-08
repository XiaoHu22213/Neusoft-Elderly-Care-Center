package com.neuedu.test;

import java.security.SecureRandom;
import java.util.Random;

public class code {
    public static void main(String[] args) {


        String SYMBOLS = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXYZ";
        Random RAND = new SecureRandom();

            String code="";
            for(int i = 0; i < 6; i++) {
                code += SYMBOLS.charAt(RAND.nextInt(SYMBOLS.length()));
                //  RAND.nextInt(SYMBOLS.length()) 生成一个随机的索引值;
                //  RAND.nextInt(n) 生成一个0到n-1之间的随机整数;
            }
            System.out.println(code);












    }



}
