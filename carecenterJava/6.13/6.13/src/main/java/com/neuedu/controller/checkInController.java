package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.CheckIn;
import com.neuedu.service.CheckInService;
import com.neuedu.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/checkIn")
public class checkInController {
    @Resource
    CheckInService checkInService;
    @GetMapping("/list")
    ResultVo<Page<CheckIn>> list(Integer pageNo, Integer pageSize,String customername) throws InterruptedException {
        if(null != customername)
        {       QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.like("customername",customername);
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize))) ;
        }
    }
    @GetMapping("/getlist")
    ResultVo<Page<CheckIn>> getlist(Integer pageNo, Integer pageSize,String customername) throws InterruptedException {
        if(null != customername)
        {       QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.like("customername",customername);
            wrapper.eq("delflag",1);
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.eq("delflag",1);
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize),wrapper)) ;
        }
    }
    @GetMapping("/type")
//    @ApiOperation("分页查询管理员列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页号", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
//    })
    ResultVo<List<CheckIn>> type() throws InterruptedException {
        return ResultVo.success(checkInService.list());
    }
    @GetMapping("/elderlist")
    ResultVo<Page<CheckIn>> elderlist(Integer pageNo, Integer pageSize,Integer eldertype) throws InterruptedException {
        if(null != eldertype)
        {       QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.like("eldertype",eldertype);
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize))) ;
        }
    }
    @GetMapping("/checkoutlist")
    ResultVo<Page<CheckIn>> checkoutlist(Integer pageNo, Integer pageSize,String customername) throws InterruptedException {
        if(null != customername)
        {       QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.like("customername",customername);
            wrapper.in("status", Arrays.asList(0, 1, 2));
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize))) ;
        }
    }
    @GetMapping("/gooutlist")
    ResultVo<Page<CheckIn>> gooutlist(Integer pageNo, Integer pageSize,String customername) throws InterruptedException {
        if(null != customername)
        {       QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.like("customername",customername);
            wrapper.in("gooutstatus", Arrays.asList(0, 1, 2));
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize), wrapper));
        }
        else {
            return  ResultVo.success(checkInService.page(new Page<>(pageNo, pageSize))) ;
        }
    }
    @GetMapping("/check")
      boolean check(String value) {
       return checkInService.check(value);
    }
    @PostMapping("/add")
   ResultVo<Boolean>  add(String customername,Integer customerage,Integer customersex,String idcard,String roomid,String buildingid,String recordid,Integer eldertype,LocalDate checkindate,LocalDate expirationdate,String contacttel,String remarks,String nursingLevel)throws InterruptedException {
        return  ResultVo.success(checkInService.add(customername, customerage, customersex, idcard, roomid, buildingid, recordid, eldertype, checkindate, expirationdate, contacttel, remarks,nursingLevel),"添加客户成功") ;
    }

    @GetMapping("/getById")
    ResultVo<CheckIn> getById(Long id) throws InterruptedException {
        return ResultVo.success(checkInService.getById(id));
    }
@PostMapping("/update")
    ResultVo<Boolean>  update(CheckIn checkIn) throws InterruptedException {
        return ResultVo.success(checkInService.updateById(checkIn),"修改用户信息成功");
}
@PostMapping("/del")
    ResultVo<Boolean>  del(Long id,Boolean delflag) throws InterruptedException {
          return ResultVo.success(checkInService.del(id,delflag),delflag ? "启用客户成功":"禁用客户成功");
}
    @GetMapping("/getCanCheckOut")
    ResultVo<List<CheckIn>> getCanCheckOut() throws InterruptedException {
             QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
            wrapper.like("status",3);
            return  ResultVo.success(checkInService.list(wrapper));
    }
    @GetMapping("/getCanGoOut")
    ResultVo<List<CheckIn>> getCanGoOut() throws InterruptedException {
        QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
        wrapper.like("gooutstatus",3);
        return  ResultVo.success(checkInService.list(wrapper));
    }
    @PostMapping("/checkoutbanli")
    ResultVo<Boolean>  checkoutbanli(String recordid,Integer checkouttype,String checkoutreason,LocalDate checkoutdate,LocalDate asktime,String checkoutremarks) throws InterruptedException {
        return ResultVo.success(checkInService.checkoutbanli(recordid,checkouttype,checkoutreason, checkoutdate, asktime,checkoutremarks),"已申请");
    }
    @PostMapping("/goOutbanli")
    ResultVo<Boolean>  goOutbanli(String recordid,String gooutreason,LocalDate goouttime,LocalDate wantbacktime,String companions,String relationship,String companionstel,String gooutremarks) throws InterruptedException {
        return ResultVo.success(checkInService.goOutbanli( recordid, gooutreason, goouttime, wantbacktime, companions, relationship, companionstel, gooutremarks),"登记成功");
    }
}


