package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.neuedu.entity.UserRole;
import com.neuedu.mapper.UserRoleMapper;
import com.neuedu.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    @Transactional
    public boolean save(Long roleId, Long[] userIds) {
        UpdateWrapper<UserRole> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id", roleId);
        this.remove(wrapper);

        List<UserRole> list = new ArrayList<>();
        if(userIds!=null&&userIds.length!=0){
            for(Long userid:userIds){
                list.add(new UserRole(userid,roleId));
            }
        }
        return this.saveBatch(list);
    }

    @Override
    public List<UserRole> getByRoleId(Long roleId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return this.list(wrapper);
    }
}
