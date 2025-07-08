package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Outin;
import com.neuedu.mapper.OutinMapper;
import com.neuedu.service.OutinService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2024-08-19
 */
@Service
public class OutinServiceImpl extends ServiceImpl<OutinMapper, Outin> implements OutinService {
    public boolean check(Long id, String field, String value)
    {   QueryWrapper<Outin> wrapper = new QueryWrapper<Outin>();
        wrapper.eq(field,value);
        if(null != id){
            wrapper.ne("id",id);
        }
        return this.count(wrapper)==0;
    }


}
