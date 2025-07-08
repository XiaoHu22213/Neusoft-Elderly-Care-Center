package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Outin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2024-08-19
 */
public interface OutinService extends IService<Outin> {

    boolean check(Long id,String field,String value);
}
