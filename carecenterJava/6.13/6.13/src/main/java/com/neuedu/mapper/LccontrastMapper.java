package com.neuedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuedu.entity.Lccontrast;
import com.neuedu.entity.Tem;

import java.util.List;



public interface LccontrastMapper extends BaseMapper<Lccontrast> {

    List<Tem> getbyID(Integer id) ;


     List selectFullJoinData(Integer lid);
}
