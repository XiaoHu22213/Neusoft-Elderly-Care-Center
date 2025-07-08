package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Busroute对象", description = "")
public class Busroute extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("班车号")
    private String name;

    @ApiModelProperty("班车时间")
    private LocalTime bustime;

    @ApiModelProperty("班车路线")
    private String route;


}
