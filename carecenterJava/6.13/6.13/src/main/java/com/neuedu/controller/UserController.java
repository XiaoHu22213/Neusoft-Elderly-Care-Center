package com.neuedu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.User;
import com.neuedu.service.UserService;
import com.neuedu.util.JwtUtil;
import com.neuedu.vo.LoginVO;
import com.neuedu.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class UserController {
    @Resource
    UserService userService;
    @GetMapping("/list")
    @ApiOperation("分页查询管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    ResultVo<IPage<User>> list(Integer pageNo, Integer pageSize) {
        return  ResultVo.success(userService.page(new Page<>(pageNo, pageSize))) ;
    }
    @GetMapping("/type")
//    @ApiOperation("分页查询管理员列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
//    })
    ResultVo<List<User>> list() throws InterruptedException {
        return ResultVo.success(userService.list());
    }
    @PostMapping("/add")
    @ApiOperation("添加管理员")
    ResultVo<String> add(String name, Integer sex, LocalDate birthday, String type, LocalDate worktime, String introduction, String phone, String email, String password) {
        return ResultVo.success(JwtUtil.create(userService.add(name, sex, birthday, type, worktime, introduction, phone, email, password)), "添加管理员成功");
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id, String field, String value) {
        return ResultVo.success(userService.check(id, field,value));
    }
    @GetMapping("/getById")
    ResultVo<User> getById(Long id) {
        return ResultVo.success(userService.getById(id));
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(Long id, String name, Integer sex, LocalDate birthday, String type, LocalDate worktime, String introduction, String phone, String email) {
        return ResultVo.success(userService.update(id, name,sex, birthday, type, worktime, introduction, phone, email), "修改管理员成功");
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Long id, Boolean status) {
        return ResultVo.success(userService.delete(id, status), status ? "启用管理员成功" : "禁用管理员成功");
    }
    @PostMapping("/login")
    ResultVo<LoginVO> login(String username, String password) {
        return ResultVo.success(userService.login(username, password));
    }
    @PostMapping("/change")
    ResultVo<String> change(String email, String password) {
        return ResultVo.success(JwtUtil.create(userService.change(email, password)));
    }

}
