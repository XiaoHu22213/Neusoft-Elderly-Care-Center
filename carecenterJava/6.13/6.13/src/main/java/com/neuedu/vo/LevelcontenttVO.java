package com.neuedu.vo;

import com.neuedu.entity.Nursecontent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelcontenttVO {
    private  List<Nursecontent> nursecontentList;
    private List <Integer> nursecontent;

}
