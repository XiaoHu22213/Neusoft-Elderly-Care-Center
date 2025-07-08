package com.neuedu.controller;

import com.neuedu.entity.SysResource;
import com.neuedu.service.RoleResourceService;
import com.neuedu.service.SysResourceService;
import com.neuedu.vo.ResultVo;
import com.neuedu.vo.RoleResourceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/roleResource")
public class RoleResourceController {
    @Resource
    SysResourceService sysResourceService;
    @Resource
    RoleResourceService roleResourceService;
    @GetMapping("/getResource")
    ResultVo<RoleResourceVO> getResource(Long roleId) {
        return ResultVo.success(new RoleResourceVO(sysResourceService.getList(),roleResourceService.getByRoleId(roleId)));
    }
    @PostMapping("/save")
    ResultVo<Boolean> save(Long roleId, Long[] menuIds, Long[] btnIds) {
        return ResultVo.success(roleResourceService.save(roleId, menuIds, btnIds), "保存成功");
    }
}
