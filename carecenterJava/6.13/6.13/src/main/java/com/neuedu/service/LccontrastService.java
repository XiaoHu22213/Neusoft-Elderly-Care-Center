package com.neuedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neuedu.entity.Lccontrast;
import com.neuedu.entity.Temp;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-15
 */
public interface LccontrastService extends IService<Lccontrast> {

//    List<LevelcontenttVO> listMaps(Page<LevelcontenttVO> page, Integer id);
//    List<Lccontrast> getById(Integer id);

   List<Temp> getBylId(Integer id);

   Boolean check(Integer lid, String field, Integer value);
   Boolean add(long lid, long cid, Integer executecycle, Integer executenub, Integer sort, String memo);

   Boolean del(Integer cid, Integer lid);

   Boolean update(long lid, long cid, Integer executecycle, Integer executenub, Integer sort, String memo);
}
