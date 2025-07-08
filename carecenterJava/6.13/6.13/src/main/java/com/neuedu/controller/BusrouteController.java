package com.neuedu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Busroute;
import com.neuedu.entity.Nursecontent;
import com.neuedu.service.BusrouteService;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


@RestController
@RequestMapping("/busroute")
public class BusrouteController {
    @Resource
    BusrouteService busrouteService;
    @GetMapping("/list")
    ResultVo<Page<Busroute>> list(Integer pageNo, Integer pageSize) throws InterruptedException {

        return  ResultVo.success(busrouteService.page(new Page<>(pageNo, pageSize))) ;

    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String name, String bustime, String route) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        LocalTime localTime = toLocalTime(bustime);

        return ResultVo.success(busrouteService.add(name, localTime, route), "添加班车成功") ;
    }
    public static LocalTime toLocalTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(timeStr, formatter);
    }


    @PostMapping("/update")
    ResultVo<Boolean> update(Integer id, String name, String bustime, String route) {

        LocalTime localTime = toLocalTime(bustime);
        return ResultVo.success(busrouteService.update(id, name, localTime,route), "修改班车成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Integer id) {

        return ResultVo.success(busrouteService.del(id),  "班车删除成功");
    }
}
