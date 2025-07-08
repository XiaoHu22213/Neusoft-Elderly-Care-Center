package com.neuedu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.neuedu.core.BaseEntity;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_resource")
@ApiModel(value = "SysResource对象", description = "权限表")
public class SysResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public SysResource(String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type) {
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
        this.level = level;
        this.type = type;
    }

    public SysResource(Long id, String name, String description, Long parentId, String frontUrl, String backUrl, Integer level, Integer type) {
        super(id);
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
        this.level = level;
        this.type = type;
    }

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限描述")
    private String description;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("前端地址")
    private String frontUrl;

    @ApiModelProperty("后端地址")
    private String backUrl;

    @ApiModelProperty("当前层级")
    private Integer level;

    @ApiModelProperty("1-目录 0-按钮")
    private Integer type;

    @TableField(exist = false)
    private List<SysResource> children;
}
