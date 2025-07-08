package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Dietarystatistics;
import com.neuedu.mapper.DietarystatisticsMapper;
import com.neuedu.service.DietarystatisticsService;
import com.neuedu.service.UploadMealfileService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 膳食统计 服务实现类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-29
 */
@Service
public class DietarystatisticsServiceImpl extends ServiceImpl<DietarystatisticsMapper, Dietarystatistics> implements DietarystatisticsService {
    @Resource
    UploadMealfileService uploadMealfileService;

    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<Dietarystatistics> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        System.out.println(id);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }


    @Override
    public boolean add(String name, String days, String breakfast, String lunch, String dinner) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return this.save(new Dietarystatistics(
                name,
                days,
                breakfast,
                lunch,
                dinner
        ));
    }
    @Override
    public boolean update(Long id, String name, String days, String breakfast, String lunch, String dinner)  throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Dietarystatistics dietarystatistics = new Dietarystatistics(
                id,
                name,
                days,
                breakfast,
                lunch,
                dinner
        );
        return this.updateById(dietarystatistics);
    }

    @Override
    public boolean set(Long id, String name, String days, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Dietarystatistics dietarystatistics = new Dietarystatistics(
                id,
                name,
                days,
                breakfast,
                lunch,
                dinner
        );
        return this.updateById(dietarystatistics);
    }
}
