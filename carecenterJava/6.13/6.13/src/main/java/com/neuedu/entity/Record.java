package com.neuedu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {


    private String name;
    private  String content;
    private Long cuid;

    private Long cid;

    private LocalDateTime date;

    private  String nursepeople;
    private  Long nurseid;



}
