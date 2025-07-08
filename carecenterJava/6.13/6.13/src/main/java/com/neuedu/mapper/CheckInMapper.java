package com.neuedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuedu.entity.CheckIn;

import java.util.List;

public interface CheckInMapper extends BaseMapper<CheckIn> {
    List<CheckIn> list();
}
