package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.neuedu.entity.RoleResource;
import com.neuedu.mapper.RoleResourceMapper;
import com.neuedu.service.RoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 角色和权限关联表 服务实现类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-21
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

    @Override
    @Transactional
    public boolean save(Long roleId, Long[] menuIds, Long[] btnIds) {
        UpdateWrapper<RoleResource> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id", roleId);
        this.remove(wrapper);
        List<RoleResource> list = new ArrayList<>();
        if (null != menuIds) {
            for (Long resourceId : menuIds) {
                list.add(new RoleResource(roleId,resourceId,1));
            }
        }
        if (null != btnIds) {
            for (Long resourceId : btnIds) {
                list.add(new RoleResource(roleId,resourceId,0));
            }
        }
        return this.saveBatch(list);
    }

    @Override
    public List<RoleResource> getByRoleId(Long roleId) {
        QueryWrapper<RoleResource> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        return this.list(wrapper);
    }
}
