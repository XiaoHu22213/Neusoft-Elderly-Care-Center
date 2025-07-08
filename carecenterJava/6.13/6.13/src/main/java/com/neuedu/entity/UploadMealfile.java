package com.neuedu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.neuedu.core.BaseEntity;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("upload_mealflie")
public class UploadMealfile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 路径
     */
    private String path;


}
