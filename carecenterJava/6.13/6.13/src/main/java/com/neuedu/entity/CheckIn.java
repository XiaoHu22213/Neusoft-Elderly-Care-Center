package com.neuedu.entity;

import com.neuedu.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckIn extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
 private String customername;
 private Integer customerage;
 private Integer customersex;
 private String idcard;
    private String roomid;
    private String buildingid;
    private String recordid;
    private Integer eldertype;
    private LocalDate checkindate;
    private LocalDate expirationdate;
    private String contacttel;
    private String remarks;
    private Boolean delflag;
    private LocalDate checkoutdate;
    private Integer checkouttype;
    private String checkoutreason;
    private Integer status;
    private LocalDate asktime ;
    private String auditopinion;
    private String auditperson;
    private LocalDate audittime ;
    private String checkoutremarks;
    private Boolean checkoutdelflag;
    private String gooutreason;
    private LocalDate goouttime ;
    private LocalDate wantbacktime ;
    private LocalDate truebacktime ;
    private String companions;
    private String relationship;
    private String companionstel;
    private String gooutauditperson;
    private String gooutaudittime;
    private String gooutremarks;
    private Integer gooutstatus;
   private  String nursingLevel;








    public CheckIn(Long id, Boolean delflag) {
        super(id);
        this.delflag=delflag;
    }
    public CheckIn(String customername, Integer customerage, Integer customersex, String idcard, String roomid, String buildingid, String recordid, Integer eldertype, LocalDate checkindate, LocalDate expirationdate, String contacttel, String remarks,String nursingLevel) {
        this.buildingid = buildingid;
        this.customername = customername;
        this.customerage = customerage;
        this.customersex = customersex;
        this.idcard = idcard;
        this.roomid = roomid;
        this.recordid = recordid;
        this.eldertype = eldertype;
        this.checkindate = checkindate;
        this.expirationdate = expirationdate;
        this.contacttel = contacttel;
        this.remarks = remarks;
        this.nursingLevel=nursingLevel;
    }
}
