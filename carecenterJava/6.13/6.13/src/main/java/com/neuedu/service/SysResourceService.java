package com.neuedu.service;

import com.neuedu.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author limengya
 * @since 2024-08-09
 */
public interface SysResourceService extends IService<SysResource> {
    List<SysResource> getList();
    boolean add(String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type);
    boolean update(Long id, String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type);
    boolean del(Long id);
    boolean check(Long id, String field, String value);
    List<SysResource> getByUserId(Long userId);
}
