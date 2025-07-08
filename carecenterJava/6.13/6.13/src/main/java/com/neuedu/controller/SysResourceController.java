package com.neuedu.controller;

import com.neuedu.entity.SysResource;
import com.neuedu.service.SysResourceService;
import com.neuedu.vo.ResultVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/sysResource")
@Api(tags = "权限接口")
public class SysResourceController {
    @Resource
    SysResourceService sysResourceService;
    @GetMapping("/list")
    ResultVo<List<SysResource>> list() {
        return ResultVo.success(sysResourceService.getList());
    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type) {
        return ResultVo.success(sysResourceService.add(name, description, parentId, frontUrl, backUrl, level, type), "添加权限成功");
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type) {
        return ResultVo.success(sysResourceService.update(id,name, description, parentId, frontUrl, backUrl, level, type), "修改权限成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Long id) {
        return ResultVo.success(sysResourceService.del(id), "删除权限成功");
    }
    @GetMapping("/getById")
    ResultVo<SysResource> getById(Long id) {
        return ResultVo.success(sysResourceService.getById(id));
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(sysResourceService.check(id, field, value));
    }

}
