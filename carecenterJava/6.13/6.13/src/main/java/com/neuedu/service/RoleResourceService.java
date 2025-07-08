package com.neuedu.service;

import com.neuedu.entity.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色和权限关联表 服务类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-21
 */
public interface RoleResourceService extends IService<RoleResource> {
    boolean save(Long roleId, Long[] menuIds, Long[] btnIds);
    List<RoleResource> getByRoleId(Long roleId);
}
