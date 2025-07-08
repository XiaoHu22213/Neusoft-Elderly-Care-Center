package com.neuedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neuedu.entity.Customcontent;
import com.neuedu.entity.Nurse;
import com.neuedu.entity.Nursecontent;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CustomcontentMapper extends BaseMapper<Customcontent> {

    List<Customcontent> getlist(Integer cuid);

    List<Nursecontent> getcontent(Integer cuid);

    List<Nurse> getnurse();

    Customcontent getByccId(@Param("cuid") Integer cuid, @Param("cid") Integer cid);


    List<Nurse> getallcontent();
}
