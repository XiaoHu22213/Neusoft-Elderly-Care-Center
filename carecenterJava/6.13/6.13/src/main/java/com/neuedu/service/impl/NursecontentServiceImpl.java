package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Nursecontent;
import com.neuedu.mapper.NursecontentMapper;
import com.neuedu.service.NursecontentService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-13
 */
@Service
public class NursecontentServiceImpl extends ServiceImpl<NursecontentMapper, Nursecontent> implements NursecontentService {
    @Resource
    NursecontentMapper nursecontentMapper;
    @Override
    public boolean add(String nursecontent, String cdescribe, Integer price, String memo, Integer status) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        return this.save(new Nursecontent(
                nursecontent,
                cdescribe,
                price,
                memo,
                status
        ));
    }
    @Override
    public Boolean del(Integer id, Boolean status) {
        UpdateWrapper<Nursecontent> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", status).eq("id", id);
        return nursecontentMapper.update(null, updateWrapper) > 0;
    }
   public Integer updatememo(String memo,Integer id){

       UpdateWrapper<Nursecontent> updateWrapper = new UpdateWrapper<>();
       updateWrapper.eq("id", id)
               .set("memo", memo);

       // 执行更新操作
      ;

       return   nursecontentMapper.update(null, updateWrapper);
    }
    @Override
    public List<Nursecontent> getcontentList() {
        QueryWrapper<Nursecontent> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        return this.list(wrapper);
    }

    @Override
    public Boolean check(Integer id, String field, String value) {
        QueryWrapper<Nursecontent> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return this.count(wrapper) == 0;
    }

    @Override
    public boolean update(Integer id, String nursecontent, String cdescribe, Integer price, String memo, Integer status) {
        UpdateWrapper<Nursecontent> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id); // 确保只更新具有指定id的记录
        // 设置要更新的字段
        updateWrapper.set("nursecontent", nursecontent);
        updateWrapper.set("cdescribe", cdescribe);
        updateWrapper.set("price", price);
        updateWrapper.set("memo", memo);
        updateWrapper.set("status", status);
        // 执行更新操作，返回更新结果
        return nursecontentMapper.update(null, updateWrapper) > 0;
    }
}
