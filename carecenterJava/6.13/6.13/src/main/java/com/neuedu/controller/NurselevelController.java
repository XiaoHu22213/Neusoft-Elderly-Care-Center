package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Nurselevel;
import com.neuedu.service.NurselevelService;
import com.neuedu.vo.ResultVo;
import io.minio.errors.*;
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
@RequestMapping("/nurselevel")
public class NurselevelController {
@Resource
    NurselevelService nurselevelService;
    @GetMapping("/list")
    ResultVo<Page<Nurselevel>> list(Integer pageNo, Integer pageSize, String level) throws InterruptedException {
        if(null != level)
        {       QueryWrapper<Nurselevel> wrapper=new QueryWrapper<>();
            wrapper.like("level",level);
            return  ResultVo.success(nurselevelService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(nurselevelService.page(new Page<>(pageNo, pageSize))) ;
        }
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Integer id, String field, String value) {
        return ResultVo.success(nurselevelService.check(id, field, value));
    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String level, Boolean status,String memo) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(nurselevelService.add(level, status,  memo), "添加护理等级成功") ;
    }

    @PostMapping("/del")
    ResultVo<Boolean> del(Integer id, Boolean status) {

        return ResultVo.success(nurselevelService.del(id, status), status ? "启用护理等级成功" : "禁用护理等级成功");
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Integer id, String level, String memo) {

        return ResultVo.success(nurselevelService.update(id, level, memo), "修改护理等级成功");
    }
    @GetMapping("/effctivelist")
    ResultVo<List<Nurselevel>> list() throws InterruptedException {

        QueryWrapper<Nurselevel> wrapper=new QueryWrapper<>();
        wrapper.eq("status",1);
        return  ResultVo.success(nurselevelService.list(wrapper)) ;

    }

}
