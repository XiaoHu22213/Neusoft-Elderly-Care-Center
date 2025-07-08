package com.neuedu.vo;

import com.neuedu.entity.RoleResource;
import com.neuedu.entity.SysResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResourceVO {
    private List<SysResource> resourcesList;
    private List<RoleResource> roleResourceList;
}
