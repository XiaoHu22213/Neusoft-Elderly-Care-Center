package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Dietarycalendar;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface DietarycalendaService extends IService<Dietarycalendar>{
    boolean check(Long id, String field, String value);
    boolean add(String mealtime, String type, String mealname, String price, Integer qingzhen, String days, MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean update(Long id, String mealtime, String type, String mealname, String price, Integer qingzhen, String days, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean del(Long id, Boolean status);
}
