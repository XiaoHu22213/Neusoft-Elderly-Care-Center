package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Busroute;
import com.neuedu.entity.Nursecontent;
import com.neuedu.mapper.BusrouteMapper;
import com.neuedu.service.BusrouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaojie
 * @since 2024-09-02
 */
@Service
public class BusrouteServiceImpl extends ServiceImpl<BusrouteMapper, Busroute> implements BusrouteService {

    @Resource
    BusrouteMapper busrouteMapper;
    @Override
    public Boolean add(String name, LocalTime bustime, String route) {
        return this.save(new Busroute(name,bustime,route));
    }

    @Override
    public boolean update(Integer id, String name, LocalTime bustime, String route) {
        UpdateWrapper<Busroute> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id); // 确保只更新具有指定id的记录
        // 设置要更新的字段
        updateWrapper.set("name", name);
        updateWrapper.set("bustime", bustime);
        updateWrapper.set("route", route);

        // 执行更新操作，返回更新结果
        return busrouteMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public Boolean del(Integer id) {
        return busrouteMapper.deleteById(id)>0;
    }
}
