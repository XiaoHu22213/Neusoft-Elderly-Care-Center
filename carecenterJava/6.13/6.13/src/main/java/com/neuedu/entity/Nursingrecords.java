package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nursingrecords extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer sex;

    private LocalDate birthday;

  //  private String bed;

    private Integer nursingLevel;

//    private String nursingContent;

//    private Integer executecycle;
//    private Integer executenub;
}
