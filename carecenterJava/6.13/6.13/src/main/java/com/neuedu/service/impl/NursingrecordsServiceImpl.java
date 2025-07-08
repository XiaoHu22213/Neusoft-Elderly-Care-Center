package com.neuedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Customcontent;
import com.neuedu.entity.Nursingrecords;
import com.neuedu.entity.Tem;
import com.neuedu.mapper.LccontrastMapper;
import com.neuedu.mapper.NursingrecordsMapper;
import com.neuedu.service.CustomcontentService;
import com.neuedu.service.NursingrecordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-09
 */
@Service
public class NursingrecordsServiceImpl extends ServiceImpl<NursingrecordsMapper, Nursingrecords> implements NursingrecordsService {
@Resource
NursingrecordsMapper nursingrecordsMapper;
@Resource
CustomcontentService customcontentService;
@Resource
LccontrastMapper lccontrastMapper;
    @Override
    public Boolean add(String name, Integer sex, LocalDate birthday, Integer nurselevel) {
        Nursingrecords nur=new Nursingrecords(name,sex,birthday,nurselevel);
        int bl= nursingrecordsMapper.insert(nur);
        Long id= nur.getId();
       List<Tem> ls=lccontrastMapper.getbyID(nurselevel);
        for (Tem tem : ls) {
            customcontentService.save(new Customcontent(id, tem.getCid(), tem.getExecutecycle(),tem.getExecutenub())) ;
        }
        return bl>0;
    }

}
