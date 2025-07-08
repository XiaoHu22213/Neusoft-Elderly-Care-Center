package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Busroute;

import java.time.LocalTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaojie
 * @since 2024-09-02
 */
public interface BusrouteService extends IService<Busroute> {

    Boolean add(String name, LocalTime bustime, String route);

    boolean update(Integer id, String name, LocalTime bustime, String route);

    Boolean del(Integer id);
}
