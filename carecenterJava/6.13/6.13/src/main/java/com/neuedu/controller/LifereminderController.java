package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Busroute;
import com.neuedu.entity.Lifereminder;
import com.neuedu.entity.Record;
import com.neuedu.service.BusrouteService;
import com.neuedu.service.LifereminderService;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/lifereminder")
public class LifereminderController {
    @Resource
    LifereminderService lifereminderService;
    @GetMapping("/list")
    ResultVo<Page<Lifereminder>> list(Integer pageNo, Integer pageSize,String name) throws InterruptedException {
        if(
                null!=name
        )
        {
            QueryWrapper<Lifereminder> wrapper=new QueryWrapper<>();
            wrapper.like("name",name);
            return  ResultVo.success(lifereminderService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return ResultVo.success(lifereminderService.page(new Page<>(pageNo, pageSize)));
        }


    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(lifereminderService.check(id, field,value));
    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String name, String phone, String rememberthing, String thingtime) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime starter = LocalDateTime.parse(thingtime, formatter);



        return ResultVo.success(lifereminderService.add(name, phone, rememberthing,starter), "添加成功") ;
    }
    @PostMapping("/remind")
    ResultVo<Boolean> remind(Long id ) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UpdateWrapper<Lifereminder>wrapper=new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("status","已提醒");
        wrapper.set("remerbertime",LocalDateTime.now());
        return ResultVo.success(lifereminderService.update(wrapper));

    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Integer id) {

        return ResultVo.success(lifereminderService.del(id),  "班车删除成功");
    }
}
