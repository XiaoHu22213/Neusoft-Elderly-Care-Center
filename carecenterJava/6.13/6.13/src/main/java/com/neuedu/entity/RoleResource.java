package com.neuedu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("role_resource")
@ApiModel(value = "RoleResource对象", description = "角色和权限关联表")
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("权限id")
    private Long resourceId;

    @ApiModelProperty("权限类型")
    private Integer type;


}
