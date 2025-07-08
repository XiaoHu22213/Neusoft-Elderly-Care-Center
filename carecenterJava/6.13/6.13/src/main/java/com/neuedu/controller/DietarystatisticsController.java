package com.neuedu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Dietarystatistics;
import com.neuedu.service.DietarystatisticsService;
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
@RequestMapping("/dietarystatistics")
public class DietarystatisticsController {
    @Resource
    DietarystatisticsService dietarystatisticsService;

    @GetMapping("/list")
    ResultVo<Page<Dietarystatistics>> list(Integer pageNo, Integer pageSize, String days) throws InterruptedException {
//        if(null != days)
//        {       QueryWrapper<Dietarystatistics> wrapper=new QueryWrapper<>();
//            wrapper.like("days",days);
////            wrapper.eq("mealtime",mealtime);
//            return  ResultVo.success(dietarystatisticsService.page(new Page<>(pageNo, pageSize), wrapper));
//        }
//        else {
            return  ResultVo.success(dietarystatisticsService.page(new Page<>(pageNo, pageSize))) ;
//        }
    }

    @GetMapping("/type")
//    @ApiOperation("分页查询管理员列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
//    })
    ResultVo<List<Dietarystatistics>> list() throws InterruptedException {
        return ResultVo.success(dietarystatisticsService.list());
    }

//    @PostMapping("/add")
//    ResultVo<Boolean> add(Integer mealtime, String type, String mealname, String price, Integer qingzhen) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        return ResultVo.success(dietarycalendaService.add(mealtime, type, mealname, price, qingzhen), "添加管理员成功") ;
//    }

    @PostMapping("/add")
    ResultVo<Boolean> add(String name, String days, String breakfast, String lunch, String dinner) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarystatisticsService.add(name, days, breakfast, lunch, dinner), "添加菜单成功") ;
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(dietarystatisticsService.check(id, field,value));
    }
    @GetMapping("/getById")
    ResultVo<Dietarystatistics> getById(Long id) {
        return ResultVo.success(dietarystatisticsService.getById(id));
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, String name, String days, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarystatisticsService.update(id, name, days, breakfast, lunch, dinner), "修改菜单成功");
    }

    @PostMapping("/set")
    ResultVo<Boolean> set(Long id,String name, String days, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(dietarystatisticsService.set(id, name, days, breakfast, lunch, dinner), "添加人员成功");
    }
}
