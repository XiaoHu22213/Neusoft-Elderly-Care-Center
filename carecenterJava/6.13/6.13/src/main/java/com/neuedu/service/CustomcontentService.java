package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Customcontent;
import com.neuedu.entity.Nurse;
import com.neuedu.entity.Nursecontent;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-20
 */
public interface CustomcontentService extends IService<Customcontent> {

    List<Customcontent> getlist(Integer cuid);

    Boolean update(Integer cuid, Integer cid, String executecycle, String executenub);

    List<Nursecontent> getcontent(Integer cuid);

    List<Nurse> getnurse();

    Customcontent getByccId(Integer cuid, Integer cid);



    List<Nurse> getallcontent( );
}
