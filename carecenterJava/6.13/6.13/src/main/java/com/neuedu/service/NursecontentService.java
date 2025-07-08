package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Nursecontent;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-13
 */
public interface NursecontentService extends IService<Nursecontent> {
    boolean add(String nursecontent, String cdescribe, Integer price, String memo, Integer status) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    Integer updatememo(String memo,Integer id);

    Boolean del(Integer id, Boolean status);

    List<Nursecontent> getcontentList();

    Boolean check(Integer id, String field, String value);

    boolean update(Integer id, String nursecontent, String cdescribe, Integer price, String memo, Integer status);
}
