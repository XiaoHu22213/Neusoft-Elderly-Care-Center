package com.neuedu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neuedu.core.BaseEntity;

import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User对象", description = "")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public User(Long id, Boolean status) {
        super(id);
        this.status = status;
    }
    public User(Long id, String password) {
        super(id);
        this.password = password;
    }

    public User(String name, Integer sex, LocalDate birthday, String type, LocalDate worktime, String introduction, String phone, String email, String password) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.type = type;
        this.worktime = worktime;
        this.introduction = introduction;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, Integer sex, LocalDate birthday, String type, LocalDate worktime,String introduction, String phone, String email) {
        super(id);
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.type = type;
        this.worktime = worktime;
        this.introduction = introduction;
        this.phone = phone;
        this.email = email;
    }

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("员工类别")
    private String type;

    @ApiModelProperty("入职时间")
    private LocalDate worktime;

    @ApiModelProperty("人员状态 0-离职 1-在职")
    private Boolean status;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;

}
