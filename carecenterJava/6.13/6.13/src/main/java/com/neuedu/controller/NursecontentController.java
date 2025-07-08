package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Nursecontent;
import com.neuedu.service.NursecontentService;
import com.neuedu.vo.ResultVo;
import io.minio.errors.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping("/nursecontent")
public class NursecontentController {
    @Resource
    NursecontentService nursecontentService;
    @Resource
    PasswordEncoder passwordEncoder;


    @GetMapping("/list")
    ResultVo<Page<Nursecontent>> list(Integer pageNo, Integer pageSize) throws InterruptedException {

            return  ResultVo.success(nursecontentService.page(new Page<>(pageNo, pageSize))) ;

    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Integer id, String field, String value) {
        return ResultVo.success(nursecontentService.check(id, field, value));
    }
    @GetMapping("/effctivelist")
    ResultVo<List<Nursecontent>> list() throws InterruptedException {

        QueryWrapper<Nursecontent> wrapper=new QueryWrapper<>();
        wrapper.eq("status",1);
        return  ResultVo.success(nursecontentService.list(wrapper)) ;

    }
    @PostMapping("/updatememo")
    ResultVo<Integer>updatememo(String description,Integer id)
    {
        return ResultVo.success(nursecontentService.updatememo(description,id));
    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String nursecontent, String cdescribe, Integer price, String memo, Integer status) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(nursecontentService.add(nursecontent, cdescribe, price, memo, status), "添加护理内容成功") ;
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Integer id, String nursecontent, String cdescribe, Integer price, String memo, Integer status) {

        return ResultVo.success(nursecontentService.update(id, nursecontent, cdescribe,price,memo,status), "修改护理内容成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Integer id, Boolean status) {

        return ResultVo.success(nursecontentService.del(id, status), status ? "启用护理内容成功" : "禁用护理内容成功");
    }
}
