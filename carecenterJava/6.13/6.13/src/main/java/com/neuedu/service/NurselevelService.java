package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Nurselevel;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-12
 */
public interface NurselevelService extends IService<Nurselevel> {

    boolean add(String level, Boolean status, String memo) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    Boolean del(Integer id, Boolean status);

    Boolean check(Integer id, String field, String value);

    boolean update(Integer id, String level, String memo);
}
