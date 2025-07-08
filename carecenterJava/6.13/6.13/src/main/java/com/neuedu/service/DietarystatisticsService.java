package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Dietarystatistics;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 膳食统计 服务类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-29
 */
public interface DietarystatisticsService extends IService<Dietarystatistics> {
    boolean check(Long id, String field, String value);
    boolean add(String name, String days, String breakfast, String lunch, String dinner) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean update(Long id, String name, String days, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean set(Long id, String name, String days, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

}
