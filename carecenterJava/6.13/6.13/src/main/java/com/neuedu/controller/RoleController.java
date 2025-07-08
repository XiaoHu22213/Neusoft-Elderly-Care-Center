package com.neuedu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neuedu.entity.Role;
import com.neuedu.service.RoleService;
import com.neuedu.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    RoleService roleService;
    @GetMapping("/list")
    ResultVo<IPage<Role>> list(Integer pageNo, Integer pageSize) {
        return ResultVo.success(roleService.list(pageNo, pageSize));
    }
    @PostMapping("/add")
    ResultVo<Boolean> add(String name, String description) {
        return ResultVo.success(roleService.add(name, description), "添加角色成功");
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, String name, String description) {
        return ResultVo.success(roleService.update(id, name, description), "修改角色成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Long id, Boolean status) {
        return ResultVo.success(roleService.del(id, status), status ? "启用角色成功" : "禁用角色成功");
    }
    @GetMapping("/getById")
    ResultVo<Role> getById(Long id) {
        return ResultVo.success(roleService.getById(id));
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(roleService.check(id, field, value));
    }
}
