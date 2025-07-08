package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Lifereminder对象", description = "")
public class Lifereminder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户姓名")
    private String name;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("事件")
    private String rememberthing;

    @ApiModelProperty("事件时间")
    private LocalDateTime thingtime;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("提醒时间")
    private LocalDateTime remerbertime;


    public Lifereminder(String name, String phone, String rememberthing, LocalDateTime thingtime) {
        this.name=name;
        this.phone=phone;
        this.rememberthing=rememberthing;
        this.thingtime=thingtime;
        this.status="未提醒";
    }
}
