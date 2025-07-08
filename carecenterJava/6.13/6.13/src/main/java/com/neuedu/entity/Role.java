package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public Role(Long id, Boolean status) {
        super(id);
        this.status = status;
    }

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色说明
     */
    private String description;

    /**
     * 状态
     */
    private Boolean status;


}
