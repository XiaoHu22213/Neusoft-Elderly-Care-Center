package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.CheckIn;

import java.time.LocalDate;
import java.util.List;

public interface CheckInService extends IService<CheckIn> {
    List<CheckIn> list();
    boolean add(String customername,Integer customerage,Integer customersex,String idcard,String roomid,String buildingid,String recordid,Integer eldertype,LocalDate checkindate,LocalDate expirationdate,String contacttel,String remarks,String nursingLevel);
    boolean del(Long id,Boolean delflag);
    boolean check(String value);
    Boolean checkoutbanli(String recordid, Integer checkouttype, String checkoutreason, LocalDate checkoutdate, LocalDate asktime, String checkoutremarks);
    Boolean goOutbanli(String recordid, String gooutreason, LocalDate goouttime, LocalDate wantbacktime, String companions, String relationship, String companionstel, String gooutremarks) ;

}
