package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Bedroom;
import com.neuedu.mapper.BedroomMapper;
import com.neuedu.service.BedroomService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2024-08-17
 */
@Service
public class BedroomServiceImpl extends ServiceImpl<BedroomMapper, Bedroom> implements BedroomService {
    @Override
    public boolean check(Long id, String field, String value)
    {   QueryWrapper<Bedroom> wrapper = new QueryWrapper<Bedroom>();
        wrapper.eq(field,value);
        if(null != id){
            wrapper.ne("id",id);
        }
        return this.count(wrapper)==0;
    }

    @Override
    public Boolean removepeopleById(Long id) {
         UpdateWrapper<Bedroom> wrapper=new UpdateWrapper<>();
         wrapper.eq("id",id);
         wrapper.set("peoplename",null);
        wrapper.set("peopleid",null);
wrapper.set("status","空闲");
this.update(wrapper);
return this.count(wrapper)==0;
    }

    @Override
    public Boolean updateBypeopleId(Bedroom bedroom) {
        UpdateWrapper<Bedroom>wrapper=new UpdateWrapper<>();
        wrapper.eq("id",bedroom.getId());
        wrapper.set("peoplename",bedroom.getPeoplename());
        wrapper.set("peopleid",bedroom.getPeopleid());
        wrapper.set("status","占用");
        return  this.update(wrapper);
    }

//    @Override
//    public Boolean updateBybedId(Bedroom bedroom) {
//        UpdateWrapper<Bedroom>wrapper=new UpdateWrapper<>();
//        wrapper.eq("bedid",bedroom.getBedid());
//        wrapper.set()
//    }


}
