package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Bedroom;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2024-08-17
 */
public interface BedroomService extends IService<Bedroom> {
    boolean check(Long id,String field,String value);

    Boolean removepeopleById(Long id);

    Boolean updateBypeopleId(Bedroom bedroom);

//    Boolean updateBybedId(Bedroom bedroom);
}
