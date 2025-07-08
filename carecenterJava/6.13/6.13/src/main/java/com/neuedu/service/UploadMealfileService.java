package com.neuedu.service;

import com.neuedu.entity.UploadMealfile;
import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author limengya
 * @since 2024-08-08
 */
public interface UploadMealfileService extends IService<UploadMealfile> {
    UploadMealfile get(String md5, Long size, String contentType);

    String upload(String bucket, MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
