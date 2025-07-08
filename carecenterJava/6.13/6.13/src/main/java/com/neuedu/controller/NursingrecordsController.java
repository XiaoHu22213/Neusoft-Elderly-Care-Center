package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Nursingrecords;
import com.neuedu.service.NursingrecordsService;
import com.neuedu.vo.ResultVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/nurse")
public class NursingrecordsController {
    @Resource
    NursingrecordsService nursingrecordsService;
    @Resource
    PasswordEncoder passwordEncoder;


    @GetMapping("/list")
    ResultVo<Page<Nursingrecords>> list(Integer pageNo, Integer pageSize,String name) throws InterruptedException {
        if(null != name)
        {       QueryWrapper<Nursingrecords> wrapper=new QueryWrapper<>();
            wrapper.like("name",name);
            return  ResultVo.success(nursingrecordsService.page(new Page<>(pageNo, pageSize), wrapper));
        }
          else {
            return  ResultVo.success(nursingrecordsService.page(new Page<>(pageNo, pageSize))) ;
        }
    }




}
