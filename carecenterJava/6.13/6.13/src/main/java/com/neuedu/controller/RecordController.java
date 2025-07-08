package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Customcontent;
import com.neuedu.entity.Record;
import com.neuedu.service.CustomcontentService;
import com.neuedu.service.RecordService;
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
@RequestMapping("/record")
public class RecordController {
    @Resource
    RecordService recordService;
    @Resource
    CustomcontentService customcontentService;
    @GetMapping("/list")
    ResultVo<Page<Record>> list(Integer pageNo, Integer pageSize,String name) throws InterruptedException {
        if(
                null!=name
        )
        {
            QueryWrapper<Record> wrapper=new QueryWrapper<>();
            wrapper.like("name",name);
            return  ResultVo.success(recordService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return ResultVo.success(recordService.page(new Page<>(pageNo, pageSize)));
        }
    }
    @PostMapping("/add")
    ResultVo<Boolean> add( Long cuid,Long cid,String name,String content,String nursepeople,Long nurseid) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Customcontent customcontent=customcontentService.getByccId(Math.toIntExact(cuid), Math.toIntExact(cid));
        if (customcontent == null) {
            System.out.println("未找到对应的护理记录");
        }
        System.out.println( customcontent.toString());
        UpdateWrapper<Customcontent> wrapper1=new UpdateWrapper<>();
        wrapper1.eq("cuid",cuid);
        wrapper1.eq("cid",cid);
        wrapper1.set("leftn", customcontent.getLeftn()-1);
        customcontentService.update(wrapper1);
        return ResultVo.success(recordService.add(cuid, cid,  name, content, nursepeople, nurseid), "添加成功") ;
    }
}
