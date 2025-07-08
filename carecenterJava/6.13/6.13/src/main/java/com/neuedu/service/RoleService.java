package com.neuedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neuedu.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author limengya
 * @since 2024-08-06
 */
public interface RoleService extends IService<Role> {
    IPage<Role> list(Integer pageNo, Integer pageSize);
    boolean add(String name, String description);
    boolean update(Long id, String name, String description);
    boolean del(Long id, Boolean status);
    boolean check(Long id, String field, String value);
}
