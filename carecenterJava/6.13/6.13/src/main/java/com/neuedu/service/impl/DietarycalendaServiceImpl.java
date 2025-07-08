package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Dietarycalendar;
import com.neuedu.mapper.DietarycalendaMapper;
import com.neuedu.service.DietarycalendaService;
import com.neuedu.service.UploadMealfileService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class DietarycalendaServiceImpl extends ServiceImpl<DietarycalendaMapper, Dietarycalendar> implements DietarycalendaService {

    @Resource
    UploadMealfileService uploadMealfileService;

    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<Dietarycalendar> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        System.out.println(id);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }


    @Override
    public boolean add(String mealtime, String type, String mealname, String price, Integer qingzhen, String days, MultipartFile file) throws  IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return this.save(new Dietarycalendar(
                mealtime,
                type,
                mealname,
                price,
                qingzhen,
                days,
                uploadMealfileService.upload("images", file)
        ));
    }
    @Override
    public boolean update(Long id, String mealtime, String type, String mealname, String price, Integer qingzhen, String days, MultipartFile file)  throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Dietarycalendar dietarycalendar = new Dietarycalendar(
                id,
                mealtime,
                type,
                mealname,
                price,
                qingzhen,
                days
        );
        if (null != file) {
            dietarycalendar.setIcon(uploadMealfileService.upload("images", file));
        }
        return this.updateById(dietarycalendar);
    }

    @Override
    public boolean del(Long id, Boolean status) {
        return this.updateById(new Dietarycalendar(id, status));
    }
}
