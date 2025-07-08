package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Lifereminder;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaojie
 * @since 2024-09-02
 */
public interface LifereminderService extends IService<Lifereminder> {

    Boolean check(Long id, String field, String value);

    Boolean add(String name, String phone, String rememberthing, LocalDateTime thingtime);

    Boolean del(Integer id);
}
