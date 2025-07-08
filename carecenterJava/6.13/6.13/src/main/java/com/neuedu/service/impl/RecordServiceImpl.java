package com.neuedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Record;
import com.neuedu.mapper.RecordMapper;
import com.neuedu.service.RecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-21
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Override
    public Boolean add(Long cuid, Long cid, String name, String content ,String nursepeople,Long nurseid) {
        return this.save(new Record(name,content,cuid,cid, LocalDateTime.now(), nursepeople, nurseid));
    }
}
