package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Dietarymanagement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public Dietarymanagement(Long id, Boolean status){
        super(id);
        this.status = status;
    }

    public Dietarymanagement(String name, Integer sex, String age, String hobby, String note, String notes) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.hobby = hobby;
        this.note = note;
        this.notes = notes;

    }

    public Dietarymanagement(Long id, String name, Integer sex, String age, String hobby, String note, String notes) {
        super(id);
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.hobby = hobby;
        this.note = note;
        this.notes = notes;

    }
//        public Dietarymanagement(Long id, String name, Integer sex, String age, String hobby, String note, String notes, String breakfast, String lunch, String dinner) {
//        super(id);
//        this.name = name;
//        this.sex = sex;
//        this.age = age;
//        this.hobby = hobby;
//        this.note = note;
//        this.notes = notes;
//        this.breakfast = breakfast;
//        this.lunch = lunch;
//        this.dinner = dinner;
//
//    }
    public Dietarymanagement(Long id, String breakfast, String lunch, String dinner) {
        super(id);
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;

    }


            /**
            * 姓名
            */
    private String name;

            /**
            * 0-女 1-男
            */
    private Integer sex;

            /**
            * 年龄
            */
    private String age;

            /**
            * 平时爱好
            */
    private String hobby;

            /**
            * 注意事项
            */
    private String note;

            /**
            * 备注
            */
    private String notes;

            /**
            * 状态
            */
    private Boolean status;

            /**
            * 图片
            */
    private String icon;

    /**
     * 早餐
     */
    private String breakfast;

    /**
     * 午餐
     */
    private String lunch;

    /**
     * 晚餐
     */
    private String dinner;


}
