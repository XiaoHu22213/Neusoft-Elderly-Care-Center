package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Customcontent;
import com.neuedu.entity.Nurse;
import com.neuedu.entity.Nursecontent;
import com.neuedu.mapper.CustomcontentMapper;
import com.neuedu.service.CustomcontentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-20
 */
@Service
public class CustomcontentServiceImpl extends ServiceImpl<CustomcontentMapper, Customcontent> implements CustomcontentService {
@Resource
    CustomcontentMapper customcontentMapper;
    @Override
    public List<Customcontent> getlist( Integer cuid) {
        return customcontentMapper.getlist(cuid);
    }

    @Override
    public Boolean update(Integer cuid, Integer cid, String executecycle, String executenub) {
        UpdateWrapper<Customcontent> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("cuid", cuid).eq("cid",cid); // 确保只更新具有指定id的记录
        // 设置要更新的字段
        updateWrapper.set("executecycle", executecycle);
        updateWrapper.set("executenub", executenub);

        // 执行更新操作，返回更新结果
        return customcontentMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public List<Nursecontent> getcontent(Integer cuid) {
        return customcontentMapper.getcontent(cuid);
    }


    @Override
    public List<Nurse> getnurse() {
        return customcontentMapper.getnurse();
    }

    @Override
    public Customcontent getByccId(Integer cuid, Integer cid) {
        return customcontentMapper.getByccId(cuid,cid);
    }

    @Override
    public List<Nurse> getallcontent() {
        return customcontentMapper.getallcontent();
    }

}
