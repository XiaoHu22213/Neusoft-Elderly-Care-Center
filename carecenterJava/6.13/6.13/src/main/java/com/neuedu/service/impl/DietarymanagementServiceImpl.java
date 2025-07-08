package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Dietarymanagement;
import com.neuedu.mapper.DietarymanagementMapper;
import com.neuedu.service.DietarymanagementService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 膳食管理 服务实现类
 * </p>
 *
 * @author zza
 * @since 2024-08-15
 */
@Service
public class DietarymanagementServiceImpl extends ServiceImpl<DietarymanagementMapper, Dietarymanagement> implements DietarymanagementService {

    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<Dietarymanagement> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        System.out.println(id);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }

    @Override
    public boolean update(Long id, String name, Integer sex, String age, String hobby, String note, String notes) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Dietarymanagement dietarymanagement = new Dietarymanagement(
                id,
                name,
                sex,
                age,
                hobby,
                note,
                notes
        );
        return this.updateById(dietarymanagement);
    }

    @Override
    public boolean del(Long id, Boolean status) {
        return this.updateById(new Dietarymanagement(id, status));
    }

//    @Override
//    public boolean set(Long id, String name, Integer sex, String age, String hobby, String note, String notes, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        Dietarymanagement dietarymanagement = new Dietarymanagement(
//                id,
//                name,
//                sex,
//                age,
//                hobby,
//                note,
//                notes,
//                breakfast,
//                lunch,
//                dinner
//        );
//        return this.updateById(dietarymanagement);
//    }

    @Override
    public boolean set(Long id, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Dietarymanagement dietarymanagement = new Dietarymanagement(
                id,
                breakfast,
                lunch,
                dinner
        );
        return this.updateById(dietarymanagement);
    }
}
