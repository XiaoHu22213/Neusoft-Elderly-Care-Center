package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.core.NeueduException;
import com.neuedu.entity.SysResource;
import com.neuedu.entity.User;
import com.neuedu.mapper.UserMapper;
import com.neuedu.service.SysResourceService;
import com.neuedu.service.UserRoleService;
import com.neuedu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.util.JwtUtil;
import com.neuedu.vo.LoginVO;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author limengya
 * @since 2024-08-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    SysResourceService sysResourceService;
    @Resource(name = "vredis")
    RedisTemplate<String, Object> redisTemplate;
    @Override
    public List<User> getList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        return this.list(wrapper);
    }

    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        System.out.println(id);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }

    @Override
    public User add(String name, Integer sex, LocalDate birthday, String type, LocalDate worktime, String introduction, String phone, String email, String password) {
        User user = new User(
                name,
                sex,
                birthday,
                type,
                worktime,
                introduction,
                phone,
                email,
                passwordEncoder.encode(password)
        );
        this.save(user);
        return user;
    }

    @Override
    public boolean update(Long id, String name, Integer sex, LocalDate birthday, String type, LocalDate worktime, String introduction, String phone, String email) {
        User user = new User(
                id,
                name,
                sex,
                birthday,
                type,
                worktime,
                introduction,
                phone,
                email
        );
        return this.updateById(user);
    }

    @Override
    public boolean delete(Long id, Boolean status) {
        return this.updateById(new User(id, status));
    }

    @Override
    public LoginVO login(String username, String password) {
        User user = validateLogin(username, password);
        return new LoginVO(JwtUtil.create(user),this.getResourceByUserId(user.getId()));
    }

    private User validateLogin(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", username);
        User user = this.getOne(wrapper);
        if (null == user || !passwordEncoder.matches(password, user.getPassword())) {
            throw new NeueduException("用户名或密码错误");
        }
        if (!user.getStatus()) {
            throw new NeueduException("管理员已经禁用，无法登录");
        }
        return user;
    }

    public List<SysResource> getResourceByUserId(Long userId) {
        List<SysResource> menu = new ArrayList<>();
        Set<String> backUrls = new HashSet<>();
        List<SysResource> source = sysResourceService.getByUserId(userId);
        for(SysResource sysResource : source) {
            if(sysResource.getType().intValue() == 1) {
                if(sysResource.getParentId().intValue() == 0) {
                    menu.add(sysResource);
                }
                else{
                    for (SysResource parent : source) {
                        if(parent.getId().intValue() == sysResource.getParentId().intValue()) {
                            List<SysResource> children = parent.getChildren();
                            if(null == children) {
                                children = new ArrayList<>();
                                parent.setChildren(children);
                            }
                            children.add(sysResource);
                            break;
                        }
                    }
                }
            }
            if(StringUtils.isNoneBlank(sysResource.getBackUrl())){
                String[] backs = sysResource.getBackUrl().split("\\,");
                for(String url : backs){
                    backUrls.add(url.trim());
                }
            }
        }

        redisTemplate.opsForValue().set("user::" + userId + "::resource", backUrls,30, TimeUnit.MINUTES);
        return menu;
    }

    @Override
    public User change(String email, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User user = this.getOne(wrapper);
        if (null == user) {
            throw new NeueduException("邮箱错误");
        }
        if (!user.getStatus()) {
            throw new NeueduException("用户已经禁用，无法修改密码");
        }
        if (null == password || "".equals(password)) {
            throw new NeueduException("密码不能为空");
        }
        this.updateById(new User(user.getId(), passwordEncoder.encode(password)));
        return user;
    }
}
