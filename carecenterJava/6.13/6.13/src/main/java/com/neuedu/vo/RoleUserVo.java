package com.neuedu.vo;

import com.neuedu.entity.User;
import com.neuedu.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserVo {
    private List<User> userList;
    private List<UserRole> userRoleList;
}
