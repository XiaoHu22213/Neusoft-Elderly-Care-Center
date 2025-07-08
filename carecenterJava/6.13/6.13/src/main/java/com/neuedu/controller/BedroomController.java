package com.neuedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Bedroom;
import com.neuedu.entity.CheckIn;
import com.neuedu.entity.Nurselevel;
import com.neuedu.service.BedroomService;
import com.neuedu.service.CheckInService;
import com.neuedu.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bedroom")
public class BedroomController {
    @Resource
    BedroomService bedroomService;
    @Resource
    CheckInService checkInService;

    @GetMapping("/list")
    ResultVo<IPage<Bedroom>> list(Integer pageNo, Integer pageSize, String name) throws InterruptedException,NullPointerException{
        System.out.println(null != name);

        if (null != name)
        {

            QueryWrapper<Bedroom> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("bedid", name).or().like("peoplename", name).or().like("status",name);
            return ResultVo.success(bedroomService.page(new Page<>(pageNo, pageSize), queryWrapper));
        }

        return  ResultVo.success(bedroomService.page(new Page<>(pageNo, pageSize))) ;

    }
//    @PostMapping("/add")
//    ResultVo<Boolean> add(Bedroom bedroom) {
//        return  ResultVo.success(bedroomService.save(bedroom),"添加成功");
//    }

    // 检查卧室信息是否存在
    @PostMapping("/add")
    ResultVo<Boolean> add(Bedroom bedroom) {
        if(bedroom.getPeoplename().length()>0)
        {
           // System.out.println(bedroom.getPeoplename());
            bedroom.setStatus("占用");
        }
       else{ bedroom.setStatus("空闲");}
        boolean addSuccess = bedroomService.save(bedroom);
        return ResultVo.success(addSuccess, "添加成功");
    }
 @PostMapping("/addstatus")
 ResultVo<Boolean> addstatus(Long id)
 {
     UpdateWrapper<Bedroom>wrapper =new UpdateWrapper<>();
     wrapper.eq("id",id);
     wrapper.set("status","占用");
     return ResultVo.success(bedroomService.update(wrapper));
 }
    @GetMapping("/check")
    ResultVo<Boolean> check(Long id,   String field, String value) {

        return ResultVo.success( bedroomService.check(id,field, value));
    }
    @GetMapping("/getById")
    ResultVo<Bedroom> getById(Integer id) {
        return ResultVo.success(bedroomService.getById(id));
    }
    @PostMapping ("/update")
    ResultVo<Boolean> update(Bedroom bedroom) {
        System.out.println(bedroom);
       return ResultVo.success(bedroomService.updateBypeopleId(bedroom),"修改成功");
    }
    @PostMapping("/delstatus")
    ResultVo<Boolean> delete( Long id) {
        return ResultVo.success(bedroomService.removepeopleById(id), "删除成功");
    }
    @DeleteMapping("/deletebed/{id}")
    ResultVo<Boolean> deletebed(@PathVariable Long id) {
        return ResultVo.success(bedroomService.removeById(id), "删除成功");
    }
    @GetMapping("/effctivelist")
    ResultVo<List<CheckIn>> effctivelist() throws InterruptedException {

        QueryWrapper<CheckIn> wrapper=new QueryWrapper<>();
        wrapper.eq("delflag",1);
        return  ResultVo.success(checkInService.list(wrapper)) ;

    }

}
