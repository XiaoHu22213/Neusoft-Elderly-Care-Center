package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Servicetargets对象", description = "服务对象")
public class Servicetargets extends BaseEntity {

    public Servicetargets(Long id, Boolean status) {
        super(id);
        this.status = status;
    }

    public Servicetargets(Long sid, String name, String phone, String floor, String notes, String time, String toname) {
        this.Sid = sid;
        this.name = name;
        this.phone = phone;
        this.floor = floor;
        this.notes = notes;
        this.time = time;
        this.toname = toname;

    }

    public Servicetargets(Long id, Long sid, String name, String phone, String floor, String notes, String time, String toname) {
        super(id);
        this.Sid = sid;
        this.name = name;
        this.phone = phone;
        this.floor = floor;
        this.notes = notes;
        this.time = time;
        this.toname = toname;

    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管家id")
    private Long Sid;

    @ApiModelProperty("管家姓名")
    private String name;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("服务楼层")
    private String floor;

    @ApiModelProperty("备注")
    private String notes;

    @ApiModelProperty("操作时间")
    private String time;

    @ApiModelProperty("状态")
    private Boolean status;

    @ApiModelProperty("老人姓名")
    private String toname;

}
