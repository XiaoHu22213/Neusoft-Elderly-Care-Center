package com.neuedu.entity;


import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dietarycalendar extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public Dietarycalendar(Long id, Boolean status) {
        super(id);
        this.status = status;
    }

    public Dietarycalendar(String mealtime, String type, String mealname, String price, Integer qingzhen, String days, String icon) {
        this.mealtime = mealtime;
        this.type = type;
        this.mealname = mealname;
        this.price = price;
        this.qingzhen = qingzhen;
        this.days = days;
        this.icon = icon;
    }

    public Dietarycalendar(Long id, String mealtime, String type, String mealname, String price, Integer qingzhen, String days) {
        super(id);
        this.mealtime = mealtime;
        this.type = type;
        this.mealname = mealname;
        this.price = price;
        this.qingzhen = qingzhen;
        this.days = days;
    }


    /**
     * 时间段
     */
    private String mealtime;

    /**
     * 菜品类型
     */
    private String type;

    /**
     * 姓名
     */
    private String mealname;

    /**
     * 价格
     */
    private String price;

    /**
     * 是否清真
     */
    private Integer qingzhen;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 日期
     */
    private String days;

    /**
     * 菜品图片
     * */
    private String icon;
}
