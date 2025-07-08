package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.core.NeueduException;
import com.neuedu.entity.SysResource;
import com.neuedu.mapper.SysResourceMapper;
import com.neuedu.service.SysResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author limengya
 * @since 2024-08-09
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {
    @Resource
    SysResourceMapper sysResourceMapper;
    @Override
    @Cacheable(value = "sys", key = "'resources'")
    public List<SysResource> getList() {
        return getByParentId(0L);
    }

    private List<SysResource> getByParentId(Long parentId) {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<SysResource> list = this.list(wrapper);
        for (SysResource resource : list) {
            List<SysResource> children = this.getByParentId(resource.getId());
            resource.setChildren(children);
        }
        return list;
    }

    @Override
    @CacheEvict(value = "sys", key = "'resources'")
    public boolean add(String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type) {
        return this.save(new SysResource(name, description, parentId, frontUrl, backUrl, level, type));
    }

    @Override
    @CacheEvict(value = "sys", key = "'resources'")
    public boolean update(Long id, String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type) {
        return this.updateById(new SysResource(id, name, description, parentId, frontUrl, backUrl, level, type));
    }

    @Override
    @CacheEvict(value = "sys", key = "'resources'")
    public boolean del(Long id)  {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        long count = this.count(wrapper);
        if(count>0)
        {
            throw new NeueduException("该权限存在未删除的下级,无法删除");
        }
        return this.removeById(id);

    }

    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }

    @Override
    public List<SysResource> getByUserId(Long userId) {
        return sysResourceMapper.getByUserId(userId);
    }

}
