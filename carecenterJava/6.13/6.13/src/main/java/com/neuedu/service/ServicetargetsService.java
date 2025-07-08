package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Servicetargets;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 服务对象 服务类
 * </p>
 *
 * @author zhaojie
 * @since 2024-08-30
 */
public interface ServicetargetsService extends IService<Servicetargets> {
    boolean check(Long id, String field, String value);
    boolean update(Long id, Long Sid, String name, String phone, String floor, String notes, String time, String toname) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean set(Long Sid, String name, String phone, String floor, String notes, String time, String toname) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean del(Long id, Boolean status);
}
