package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Record;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-21
 */
public interface RecordService extends IService<Record> {

    Boolean add(Long cuid, Long cid, String name, String content,String nursepeople,Long nurseid);
}
