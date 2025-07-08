package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Role;
import com.neuedu.mapper.RoleMapper;
import com.neuedu.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author limengya
 * @since 2024-08-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public IPage<Role> list(Integer pageNo, Integer pageSize) {
        return this.page(new Page<>(pageNo, pageSize));
    }

    @Override
    public boolean add(String name, String description) {
        return this.save(new Role(name, description));
    }

    @Override
    public boolean update(Long id, String name, String description) {
        return this.updateById(new Role(id, name, description));
    }

    @Override
    public boolean del(Long id, Boolean status) {
        return this.updateById(new Role(id, status));
    }

    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }
}
