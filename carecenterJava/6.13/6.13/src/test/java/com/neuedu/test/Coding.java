package com.neuedu.test;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.neuedu.core.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class Coding {
    public static void main(String[] args) {
        // 定义 url
        String url = "jdbc:mysql://10.25.128.190:3307/qiming";
        // 定义项目路径
        String basePath = System.getProperty("user.dir");
        // 定义表
        String table = "lifereminder";
        // 定义代码生成规则
        FastAutoGenerator.create(url, "root", "123456")
                // 全局配置
                .globalConfig(conf -> {
                    // 禁用打开目录
                    conf.disableOpenDir()
                            // 设置项目路径
                            .outputDir(basePath + "/src/main/java")
                            // 设置作者
                            .author("zhaojie")
                            .enableSwagger();
                })
                // 包配置
                .packageConfig(conf -> {
                    // 将xml路径更改为 resources下
                    Map<OutputFile, String> pathInfo = new HashMap<>();
                    pathInfo.put(OutputFile.xml, basePath + "/src/main/resources/com/neuedu/mapper");
                    // 设置父包
                    conf.parent("com.neuedu")
                            .pathInfo(pathInfo);
                })
                // 策略配置
                .strategyConfig(conf -> {
                    // 生成的表
                    conf.addInclude(table)
                            // entity策略 下划线转驼峰
                            .entityBuilder()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            // 加Lombok注解
                            .enableLombok()
                            // 每个entity都要继承BaseEntity
                            .superClass(BaseEntity.class)
                            // 每个entity都不再生成id属性
                            .addSuperEntityColumns("id")
                            // controller 配置策略
                            .controllerBuilder()
                            // 每个controller都加RestController注解
                            .enableRestStyle()
                            // service 配置策略
                            .serviceBuilder()
                            //去掉service接口前面的I
                            .formatServiceFileName("%sService");
                })
                // 配置模版引擎 , 我使用的是freemarker 所以必须导入freemarker的jar
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行生成代码
                .execute();
    }
}
