package com.neuedu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuedu.entity.Nursecontent;
import com.neuedu.entity.Temp;
import com.neuedu.service.LccontrastService;
import com.neuedu.service.NursecontentService;
import com.neuedu.vo.ResultVo;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping("/lccontrast")
public class LccontrastController {
@Resource
    LccontrastService lccontrastService;
@Resource
    NursecontentService nursecontentService;

//    @GetMapping("/list")
//    ResultVo<Page<Lccontrast>> list(Integer pageNo, Integer pageSize, Integer id) throws InterruptedException {
//
//            QueryWrapper<Lccontrast> wrapper=new QueryWrapper<>();
//            wrapper.like("lid",id);
//            return  ResultVo.success(lccontrastService.page(new Page<>(pageNo, pageSize), wrapper));
//
//    }
@GetMapping("/list")
public  ResultVo<Page<Temp>>list(Integer id, Integer pageNo, Integer pageSize) throws InterruptedException {
    Page<Temp> page = new Page<>(pageNo, pageSize);
    List<Temp>ls= lccontrastService.getBylId(id);
    // 4System.out.println(ls);
    page.setRecords(ls);
    return   ResultVo.success( page);
//return ResultVo.success( lccontrastService.getBylId(id));

    // 将 page 对象作为参数传递给 ResultVo.success() 方法
//    return ResultVo.success(new LevelcontenttVO(nursecontentService.getcontentList(), lccontrastService.getById(id)));

}


@PostMapping("/save")
ResultVo<Boolean> add(long lid, long cid, Integer executecycle,  Integer executenub,  Integer sort, String memo) throws InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    return ResultVo.success(lccontrastService.add(lid, cid, executecycle, executenub, sort, memo), "添加护理内容成功") ;
}
    @GetMapping("/lccontent")
    ResultVo<Nursecontent> getContentName(Integer id) {

        return ResultVo.success(nursecontentService.getById(id));
    }
    @GetMapping("/check")
    ResultVo<Boolean> check(Integer lid,String field, Integer value) {
        return ResultVo.success(lccontrastService.check(lid, field, value));
    }
    @PostMapping("/del")
    ResultVo<Boolean> del(Integer cid, Integer lid) {

        return ResultVo.success(lccontrastService.del(cid, lid),  "删除护理内容成功");
    }
    @PostMapping("/update")
    ResultVo<Boolean> update(long lid, long cid, Integer executecycle,  Integer executenub,  Integer sort, String memo) {

        return ResultVo.success(lccontrastService.update(lid, cid, executecycle, executenub, sort, memo), "修改护理内容成功");
    }
}
