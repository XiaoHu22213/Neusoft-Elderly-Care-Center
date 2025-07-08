package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Bedroom;
import com.neuedu.entity.Outin;
import com.neuedu.service.BedroomService;
import com.neuedu.service.OutinService;
import com.neuedu.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/outin")
public class OutinController {
    @Resource
    OutinService outinService;
    @Resource
    BedroomService bedroomService;
    @GetMapping("/list")
    ResultVo<IPage<Outin>> list(Integer pageNo, Integer pageSize, String name) {
        if (null != name)
        {

            QueryWrapper<Outin> queryWrapper = new QueryWrapper<>();
            queryWrapper.or().like("outinname", name).or().like("bednum",name);
            return ResultVo.success(outinService.page(new Page<>(pageNo, pageSize), queryWrapper));
        }
        else
        {
            return ResultVo.success(outinService.page(new Page<>(pageNo, pageSize)));
        }
    }
//    @PostMapping("/add")
//    ResultVo<Boolean> add(Bedroom bedroom) {
//        return  ResultVo.success(bedroomService.save(bedroom),"添加成功");
//    }

    // 检查卧室信息是否存在
    @PostMapping("/add")
    ResultVo<Boolean> add(Outin outin) {
        boolean addSuccess = outinService.save(outin);
       UpdateWrapper<Bedroom> wrapper=new UpdateWrapper<>();
        wrapper.eq("bedid",outin.getBednum());
        wrapper.set("status","离席");
        bedroomService.update(wrapper);
        return ResultVo.success(addSuccess, "添加成功");
    }

    @GetMapping("/check")
    ResultVo<Boolean> check(Long id,   String field, String value) {

        return ResultVo.success( outinService.check(id,field, value),"添加成功");
    }
    @GetMapping("/getById")
    ResultVo<Outin> getById(Integer id) {
        return ResultVo.success(outinService.getById(id));
    }
    @PostMapping ("/update")
    ResultVo<Boolean> update(Outin outin) {
        return ResultVo.success(outinService.updateById(outin),"修改管理员成功");
    }
    @DeleteMapping("/delete/{id}")
    ResultVo<Boolean> delete(@PathVariable Long id) {
        return ResultVo.success(outinService.removeById(id), "删除成功");
    }



    }


