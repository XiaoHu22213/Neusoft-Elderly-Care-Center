package com.neuedu.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neuedu.entity.UploadMealfile;
import com.neuedu.mapper.UploadMealfileMapper;
import com.neuedu.service.UploadMealfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author limengya
 * @since 2024-08-08
 */
@Service
public class UploadMealfileServiceImpl extends ServiceImpl<UploadMealfileMapper, UploadMealfile> implements UploadMealfileService {
    @Value("${minio.config.endpoint}")
    String endpoint;
    @Value("${minio.config.username}")
    String username;
    @Value("${minio.config.password}")
    String password;
    @Override
    public UploadMealfile get(String md5, Long size, String contentType) {
        QueryWrapper<UploadMealfile> wrapper = new QueryWrapper<>();
        wrapper.eq("md5", md5)
                .eq("size", size)
                .eq("content_type", contentType);
        return this.getOne(wrapper);
    }

    @Override
    public String upload(String bucket, MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 拿到文件的md5 大小  类型
        long size = file.getSize();
        String contentType = file.getContentType();
        String md5 = DigestUtils.md5Hex(file.getInputStream());
        // 根据 这三个属性查询 是否上传过
        UploadMealfile uploadMealfile = this.get(md5, size, contentType);
        if (null != uploadMealfile) {
            return uploadMealfile.getPath();
        }
        // 创建Minio客户端
        MinioClient client = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
        // 文件名重命名
        String fileName = NanoIdUtils.randomNanoId() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        // 调用Minio客户端上传
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucket)
                .contentType(contentType)
                .stream(file.getInputStream(), size, 0)
                .object(fileName)
                .build();
        // 上传文件
        client.putObject(args);
        String path = "/" + bucket + "/" + fileName;
        uploadMealfile = new UploadMealfile(md5, size, contentType, path);
        this.save(uploadMealfile);
        return path;
    }
}
