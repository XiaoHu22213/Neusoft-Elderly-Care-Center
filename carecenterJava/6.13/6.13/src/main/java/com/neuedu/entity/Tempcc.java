package com.neuedu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tempcc {
    private Long cuid;

    private Long cid;

    private Integer executecycle;

    private Integer executenub;
    private String nursecontent;
    private Integer lastn;

    private Integer buy;

    private Integer sum;

    private Integer leftn;

    private LocalDate time;



    private String memo;

}
