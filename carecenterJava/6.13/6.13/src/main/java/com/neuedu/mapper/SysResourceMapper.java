package com.neuedu.mapper;

import com.neuedu.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface SysResourceMapper extends BaseMapper<SysResource> {
    List<SysResource> getByUserId(Long userId);
}
