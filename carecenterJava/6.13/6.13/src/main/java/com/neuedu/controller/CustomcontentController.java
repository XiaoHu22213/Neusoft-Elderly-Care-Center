package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.neuedu.entity.Customcontent;
import com.neuedu.entity.Nurse;
import com.neuedu.entity.Nursecontent;
import com.neuedu.service.CustomcontentService;
import com.neuedu.service.NursingrecordsService;
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
import java.util.List;

@RestController
@RequestMapping("/customcontent")
public class CustomcontentController {
    @Resource
    CustomcontentService customcontentService;
    @Resource
    NursingrecordsService nursingrecordsService;
    @GetMapping("/list")
    public ResultVo<List<Customcontent>> getlist(Integer id)
    {
        return ResultVo.success( customcontentService.getlist(id));
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Integer cuid, Integer cid ,String executecycle,String executenub) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        System.out.println(cuid);
        return ResultVo.success(customcontentService.update(cuid, cid,  executecycle,executenub), "修改护理成功") ;
    }
    @PostMapping("/updatenub")
    ResultVo<Boolean> updatenub(Integer cuid,Integer cid, Integer num ,String memo) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        Customcontent customcontent=customcontentService.getByccId(cuid,cid);

     Integer leftn=customcontent.getLeftn();
     Integer sum=leftn+num;
        UpdateWrapper<Customcontent>wrapper=new UpdateWrapper<>();
                wrapper.eq("cuid",cuid);
                wrapper.eq("cid",cid);
        wrapper.set("lastn",leftn);
        wrapper.set("buy",num);
        wrapper.set("sum",sum);
        wrapper.set("leftn",sum);
                wrapper.set("time",LocalDate.now());

                wrapper.set("memo",memo);


        return ResultVo.success(customcontentService.update(wrapper), "购买成功") ;
    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String name, Integer sex, LocalDate birthday, Integer id) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        return ResultVo.success(nursingrecordsService.add(name, sex,  birthday,id), "添加成功") ;
    }
    @GetMapping("/getcontent")
    ResultVo<List<Nursecontent>>getcontent(Integer cuid)
    {
        //System.out.println(id);
        return ResultVo.success( customcontentService.getcontent(cuid));
    }
    @GetMapping("/getall")
    ResultVo<List<Nurse>>getallcontent()
    {
        //System.out.println(id);
        return ResultVo.success( customcontentService.getallcontent());
    }
    @GetMapping("/getnurse")
    ResultVo<List<Nurse>> getnurse() {
        {
            return ResultVo.success(customcontentService.getnurse());
        }
    }

}
