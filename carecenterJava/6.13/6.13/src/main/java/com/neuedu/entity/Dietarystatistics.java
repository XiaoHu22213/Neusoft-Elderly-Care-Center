package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dietarystatistics extends BaseEntity {

    public Dietarystatistics(Long id, String name, String days, String breakfast, String lunch, String dinner) {
        super(id);
        this.name = name;
        this.days = days;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;

    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("日期")
    private String days;

    @ApiModelProperty("早餐")
    private String breakfast;

    @ApiModelProperty("午餐")
    private String lunch;

    @ApiModelProperty("晚餐")
    private String dinner;


}
