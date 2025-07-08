package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nursecontent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String nursecontent;

    private String cdescribe;

    private Integer price;

    private String memo;

    private Integer status;


}
