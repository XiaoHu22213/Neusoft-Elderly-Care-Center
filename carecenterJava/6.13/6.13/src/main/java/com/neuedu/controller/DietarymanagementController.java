package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Dietarymanagement;
import com.neuedu.service.DietarymanagementService;
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


@RestController
@RequestMapping("/dietarymanagement")
public class DietarymanagementController {

    @Resource
    DietarymanagementService dietarymanagementService;
//    @GetMapping("/list")
//    ResultVo<IPage<Dietarymanagement>> list(Integer pageNo, Integer pageSize) throws InterruptedException {
//        return  ResultVo.success(dietarymanagementService.page(new Page<>(pageNo, pageSize))) ;
//    }


    @GetMapping("/list")
    ResultVo<Page<Dietarymanagement>> list(Integer pageNo, Integer pageSize,String name) throws InterruptedException {
        if(null != name)
        {       QueryWrapper<Dietarymanagement> wrapper=new QueryWrapper<>();
            wrapper.like("name",name);
            return  ResultVo.success(dietarymanagementService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(dietarymanagementService.page(new Page<>(pageNo, pageSize))) ;
        }
    }


    @PostMapping("/add")
    ResultVo<Boolean> add(Dietarymanagement dietarymanagement) {
        return ResultVo.success(dietarymanagementService.save(dietarymanagement), "添加人员成功");
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(dietarymanagementService.check(id, field,value));
    }
    @GetMapping("/getById")
    ResultVo<Dietarymanagement> getById(Long id) {
        return ResultVo.success(dietarymanagementService.getById(id));
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, String name, Integer sex, String age, String hobby, String note, String notes) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarymanagementService.update(id, name, sex, age, hobby, note, notes), "修改人员信息成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Long id, Boolean status) {
        return ResultVo.success(dietarymanagementService.del(id, status), status ? "恢复数据成功" : "删除记录成功");
    }
//    @PostMapping("/set")
//    ResultVo<Boolean> set(Long id, String name, Integer sex, String age, String hobby, String note, String notes, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        return ResultVo.success(dietarymanagementService.set(id, name, sex, age, hobby, note, notes, breakfast, lunch, dinner), "添加人员成功");
//    }
    @PostMapping("/set")
    ResultVo<Boolean> set(Long id, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarymanagementService.set(id, breakfast, lunch, dinner), "添加人员成功");
    }
}
