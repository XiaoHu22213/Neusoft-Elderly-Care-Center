package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Feedback;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaojie
 * @since 2024-09-02
 */
public interface FeedbackService extends IService<Feedback> {

    Boolean check(Long id, String field, String value);

    Boolean add(String name, String sex, String thing, LocalDate ntime, String memo, String status);

    Integer updatememo(String content, Integer id, String people);

    Boolean del(Integer id);
}
