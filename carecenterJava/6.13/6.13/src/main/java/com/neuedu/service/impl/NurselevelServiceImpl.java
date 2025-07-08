package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Nurselevel;
import com.neuedu.mapper.NurselevelMapper;
import com.neuedu.service.NurselevelService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-12
 */
@Service
public class NurselevelServiceImpl extends ServiceImpl<NurselevelMapper, Nurselevel> implements NurselevelService {
    @Resource
    NurselevelMapper nurselevelMapper;
    @Override
    public boolean add(String level, Boolean status, String memo) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        return this.save(new Nurselevel(
                level,
                status,
                memo
        ));
    }

    @Override
    public Boolean del(Integer id, Boolean status) {
        UpdateWrapper<Nurselevel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", status).eq("id", id);
        return nurselevelMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public Boolean check(Integer id, String field, String value) {
        QueryWrapper<Nurselevel> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }

    @Override
    public boolean update(Integer id, String level, String memo) {
        UpdateWrapper<Nurselevel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id); // 确保只更新具有指定id的记录
        // 设置要更新的字段
        updateWrapper.set("level", level);
        updateWrapper.set("memo", memo);

        // 执行更新操作，返回更新结果
        return nurselevelMapper.update(null, updateWrapper) > 0;
    }
}
