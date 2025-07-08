package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Feedback对象", description = "")
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户姓名")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("时间")
    private LocalDate ntime;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("处理人")
    private String people;

    @ApiModelProperty("处理内容")
    private String content;

    @ApiModelProperty("事项")
    private String thing;


    public Feedback(String name, String sex, String thing, LocalDate ntime, String memo, String status) {
        this.name=name;
        this.sex=sex;
        this.thing=thing;
        this.ntime=ntime;
        this.memo=memo;
        this.status=status;
    }
}
