package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Servicetargets;
import com.neuedu.service.ServicetargetsService;
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
@RequestMapping("/servicetargets")
public class ServicetargetsController {
    @Resource
    ServicetargetsService servicetargetsService;

    @GetMapping("/list")
    ResultVo<Page<Servicetargets>> list(Integer pageNo, Integer pageSize, String name) throws InterruptedException {
        if(null != name)
        {       QueryWrapper<Servicetargets> wrapper=new QueryWrapper<>();
            wrapper.like("name",name);
            return  ResultVo.success(servicetargetsService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(servicetargetsService.page(new Page<>(pageNo, pageSize))) ;
        }
    }
    @GetMapping("/type")
//    @ApiOperation("分页查询管理员列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
//    })
    ResultVo<List<Servicetargets>> list() throws InterruptedException {
        return ResultVo.success(servicetargetsService.list());
    }


    @PostMapping("/add")
    ResultVo<Boolean> add(Servicetargets servicetargets) {
        return ResultVo.success(servicetargetsService.save(servicetargets), "添加人员成功");
    }
    @PostMapping("/set")
    ResultVo<Boolean> set(Long Sid, String name, String phone, String floor, String notes, String time, String toname) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(servicetargetsService.set(Sid, name, phone, floor, notes, time, toname), "添加人员成功");
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(servicetargetsService.check(id, field,value));
    }
    @GetMapping("/getById")
    ResultVo<Servicetargets> getById(Long id) {
        return ResultVo.success(servicetargetsService.getById(id));
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, Long sid, String name, String phone, String floor, String notes, String time, String toname) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResultVo.success(servicetargetsService.update(id, sid, name, phone, floor, notes, time, toname), "修改人员信息成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Long id, Boolean status) {
        return ResultVo.success(servicetargetsService.del(id, status), status ? "恢复数据成功" : "删除记录成功");
    }
}
