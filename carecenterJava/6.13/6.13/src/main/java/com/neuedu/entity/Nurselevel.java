package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nurselevel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String level;

    private Boolean status;

    private String memo;

}
