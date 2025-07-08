package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Mealtype对象", description = "菜品类型表")
public class Mealtype extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜品类型")
    private String mealtype;


}
