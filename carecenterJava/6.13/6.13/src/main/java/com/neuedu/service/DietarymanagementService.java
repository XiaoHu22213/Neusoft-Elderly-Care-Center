package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Dietarymanagement;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 膳食管理 服务类
 * </p>
 *
 * @author zza
 * @since 2024-08-15
 */
public interface DietarymanagementService extends IService<Dietarymanagement> {
    boolean check(Long id, String field, String value);
//    boolean add(String mealtime, String type, String mealname, String price, Integer qingzhen) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean update(Long id, String name, Integer sex, String age, String hobby, String note, String notes) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean del(Long id, Boolean status);
//    boolean set(Long id, String name, Integer sex, String age, String hobby, String note, String notes, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean set(Long id, String breakfast, String lunch, String dinner) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

}
