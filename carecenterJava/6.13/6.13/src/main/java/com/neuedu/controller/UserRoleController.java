package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.neuedu.entity.Role;
import com.neuedu.entity.User;
import com.neuedu.service.RoleService;
import com.neuedu.service.UserRoleService;
import com.neuedu.service.UserService;
import com.neuedu.vo.ResultVo;
import com.neuedu.vo.RoleUserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    @Resource
    UserService userService;
    @Resource
    RoleService roleService;
    @Resource
    UserRoleService userRoleService;
    @GetMapping("/getUser")
    ResultVo<RoleUserVo> getUserList(Long roleId){

        return ResultVo.success(new RoleUserVo(userService.getList(),userRoleService.getByRoleId(roleId)));
    }
    @PostMapping("/save")
    ResultVo<Boolean> save(Long roleId, Long[] userIds){
        Role role= roleService.getById(roleId);
        for (Long userId : userIds) {
            UpdateWrapper<User>wrapper=new UpdateWrapper<>();
            wrapper.eq("id",userId);
            wrapper.set("type",role.getName());
            userService.update(wrapper);

        }
        return ResultVo.success(userRoleService.save(roleId, userIds));
    }

}
