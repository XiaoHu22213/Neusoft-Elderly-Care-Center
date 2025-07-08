package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bedroom extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public Bedroom(Long id, String status) {
        super(id);
        this.status = status;
    }

    public String bedid;

    public String peoplename;

    public String status;
    public  Integer peopleid;

}
