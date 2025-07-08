package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Servicetargets;
import com.neuedu.mapper.ServicetargetsMapper;
import com.neuedu.service.ServicetargetsService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 服务对象 服务实现类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-30
 */
@Service
public class ServicetargetsServiceImpl extends ServiceImpl<ServicetargetsMapper, Servicetargets> implements ServicetargetsService {
    @Override
    public boolean check(Long id, String field, String value) {
        QueryWrapper<Servicetargets> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        System.out.println(id);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }


    @Override
    public boolean set(Long Sid, String name, String phone, String floor, String notes, String time, String toname) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return this.save(new Servicetargets(
                Sid,
                name,
                phone,
                floor,
                notes,
                time,
                toname
        ));
    }
    @Override
    public boolean update(Long id, Long Sid, String name, String phone, String floor, String notes, String time, String toname)  throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Servicetargets servicetargets = new Servicetargets(
                id,
                Sid,
                name,
                phone,
                floor,
                notes,
                time,
                toname
        );
        return this.updateById(servicetargets);
    }

    @Override
    public boolean del(Long id, Boolean status) {
        return this.updateById(new Servicetargets(id, status));
    }

}
