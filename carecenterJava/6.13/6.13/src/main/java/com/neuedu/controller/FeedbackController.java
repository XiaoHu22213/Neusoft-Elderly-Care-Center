package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Feedback;
import com.neuedu.entity.Lifereminder;
import com.neuedu.service.FeedbackService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Resource
    FeedbackService feedbackService;
    @GetMapping("/list")
    ResultVo<Page<Feedback>> list(Integer pageNo, Integer pageSize, String name) throws InterruptedException {
        if(
                null!=name
        )
        {
            QueryWrapper<Feedback> wrapper=new QueryWrapper<>();
            wrapper.like("name",name);
            return  ResultVo.success(feedbackService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return ResultVo.success(feedbackService.page(new Page<>(pageNo, pageSize)));
        }


    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Integer id) {

        return ResultVo.success(feedbackService.del(id),  "班车删除成功");
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(feedbackService.check(id, field,value));
    }
    @PostMapping("/add")
    ResultVo<Boolean> addadd(String name, String sex, String thing, LocalDate ntime, String memo, String status) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime starter = LocalDateTime.parse(thingtime, formatter);



        return ResultVo.success(feedbackService.add(name, sex, thing,ntime,memo,status), "添加成功") ;
    }
    @PostMapping("/updatememo")
    ResultVo<Integer>updatememo(String content,Integer id,String people)
    {
        return ResultVo.success(feedbackService.updatememo(content,id,people));
    }
}
