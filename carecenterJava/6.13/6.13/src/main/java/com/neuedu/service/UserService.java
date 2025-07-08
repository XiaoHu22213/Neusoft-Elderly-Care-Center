package com.neuedu.service;

import com.neuedu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.vo.LoginVO;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author limengya
 * @since 2024-08-14
 */
public interface UserService extends IService<User> {
    List<User> getList();
    boolean check(Long id, String field, String value);
    User add(String name, Integer sex, LocalDate birthday, String type, LocalDate worktime,String introduction, String phone, String email, String password);
    boolean update(Long id, String name, Integer sex, LocalDate birthday, String type, LocalDate worktime,String introduction, String phone, String email);
    boolean delete(Long id , Boolean status);
    LoginVO login(String username, String password);
    User change(String email, String password);
}
