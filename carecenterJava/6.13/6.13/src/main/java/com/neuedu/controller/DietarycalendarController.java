package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Dietarycalendar;
import com.neuedu.service.DietarycalendaService;
import com.neuedu.vo.ResultVo;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/dietarycalendar")
public class DietarycalendarController {
    @Resource
    DietarycalendaService dietarycalendaService;


//    @GetMapping("/list")
////    @ApiOperation("分页查询管理员列表")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
////            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
////    })
//    ResultVo<IPage<Dietarycalendar>> list(Integer pageNo, Integer pageSize) throws InterruptedException {
//        return  ResultVo.success(dietarycalendaService.page(new Page<>(pageNo, pageSize))) ;
//    }

    @GetMapping("/list")
    ResultVo<Page<Dietarycalendar>> list(Integer pageNo, Integer pageSize,String days) throws InterruptedException {
        if(null != days)
        {       QueryWrapper<Dietarycalendar> wrapper=new QueryWrapper<>();
            wrapper.like("days",days);
//            wrapper.eq("mealtime",mealtime);
            return  ResultVo.success(dietarycalendaService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(dietarycalendaService.page(new Page<>(pageNo, pageSize))) ;
        }
    }

    @GetMapping("/type")
//    @ApiOperation("分页查询管理员列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
//    })
    ResultVo<List<Dietarycalendar>> list() throws InterruptedException {
        return ResultVo.success(dietarycalendaService.list());
    }

//    @PostMapping("/add")
//    ResultVo<Boolean> add(Integer mealtime, String type, String mealname, String price, Integer qingzhen) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        return ResultVo.success(dietarycalendaService.add(mealtime, type, mealname, price, qingzhen), "添加管理员成功") ;
//    }

    @PostMapping("/add")
    ResultVo<Boolean> add(String mealtime, String type, String mealname, String price, Integer qingzhen, String days, MultipartFile file) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarycalendaService.add(mealtime, type, mealname, price, qingzhen, days, file), "添加菜单成功") ;
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(dietarycalendaService.check(id, field,value));
    }
    @GetMapping("/getById")
    ResultVo<Dietarycalendar> getById(Long id) {
        return ResultVo.success(dietarycalendaService.getById(id));
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, String mealtime, String type, String mealname, String price, Integer qingzhen, String days, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarycalendaService.update(id, mealtime, type, mealname, price, qingzhen, days, file), "修改菜单成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Long id, Boolean status) {
        return ResultVo.success(dietarycalendaService.del(id, status), status ? "恢复菜单成功" : "删除菜单成功");
    }
}
