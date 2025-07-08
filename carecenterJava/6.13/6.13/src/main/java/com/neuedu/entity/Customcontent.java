package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customcontent {

    private Long cuid;

    private Long cid;

    private Integer executecycle;

    private Integer executenub;

    private Integer lastn;

    private Integer buy;

    private Integer sum;

    private Integer leftn;

    private LocalDate time;



    private String memo;

    public Customcontent(Long cuid, Long cid, Integer executecycle, Integer executenub)
    {
        this.cuid=cuid;
        this.cid=cid;
        this.executecycle=executecycle;
        this.executenub=executenub;
    }
}
