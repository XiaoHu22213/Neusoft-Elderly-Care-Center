package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Nursingrecords;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-09
 */
public interface NursingrecordsService extends IService<Nursingrecords> {


    Boolean add(String name, Integer sex, LocalDate birthday, Integer nurselevel);
}
