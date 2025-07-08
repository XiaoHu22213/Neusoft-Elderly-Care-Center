package com.neuedu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temp {
    private Long lid;

    private Long cid;

    private Integer executecycle;

    private Integer executenub;

    private Integer sort;

    private String memo;
    private String nursecontent;
}
