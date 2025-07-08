package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Dietarycalendar;
import com.neuedu.entity.Mealtype;
import com.neuedu.service.DietarycalendaService;
import com.neuedu.service.MealtypeService;
import com.neuedu.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/mealtype")
public class MealtypeController {
    @Resource
    MealtypeService mealtypeService;

    @GetMapping("/type")
//    @ApiOperation("分页查询管理员列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
//    })
    ResultVo<List<Mealtype>> list() throws InterruptedException {
        return ResultVo.success(mealtypeService.list());
    }
}
