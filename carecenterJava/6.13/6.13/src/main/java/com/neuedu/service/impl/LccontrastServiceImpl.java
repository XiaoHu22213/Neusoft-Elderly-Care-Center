package com.neuedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuedu.entity.Lccontrast;
import com.neuedu.entity.Temp;
import com.neuedu.mapper.LccontrastMapper;
import com.neuedu.service.LccontrastService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mengxiang
 * @since 2024-08-15
 */
@Service
public class LccontrastServiceImpl extends ServiceImpl<LccontrastMapper, Lccontrast> implements LccontrastService {
  @Resource
    LccontrastMapper lccontrastMapper;

//    @Override
//    public List<Lccontrast> getBylId(Integer id) {
//        QueryWrapper<Lccontrast> wrapper = new QueryWrapper<>();
//        wrapper.eq("lid", id);
//        return this.list(wrapper);
//    }

    @Override
    public List <Temp> getBylId(Integer id) {
      System.out.println(lccontrastMapper.selectFullJoinData(id));
        return lccontrastMapper.selectFullJoinData(id);
    }

  @Override
  public Boolean check(Integer lid, String field, Integer value) {
    QueryWrapper<Lccontrast> wrapper = new QueryWrapper<>();
    wrapper.eq(field, value);
    if (null != lid) {
      wrapper.eq("lid", lid);
    }
    return this.count(wrapper) == 0;
  }

  @Override
  public Boolean add(long lid, long cid, Integer executecycle, Integer executenub, Integer sort, String memo) {
    return  this.save( new Lccontrast(lid,cid,executecycle,executenub,sort,memo));
  }
  @Override
  public Boolean del(Integer cid, Integer lid) {
    UpdateWrapper<Lccontrast> updateWrapper = new UpdateWrapper<>();
   updateWrapper.eq("lid",lid).eq("cid",cid);

    return lccontrastMapper.delete(updateWrapper) > 0;
  }

  @Override
  public Boolean update(long lid, long cid, Integer executecycle, Integer executenub, Integer sort, String memo) {
    UpdateWrapper<Lccontrast> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("lid", lid).eq("cid",cid); // 确保只更新具有指定id的记录
    // 设置要更新的字段
    updateWrapper.set("executecycle", executecycle);
    updateWrapper.set("executenub", executenub);
    updateWrapper.set("sort", sort);
    updateWrapper.set("memo", memo);
    // 执行更新操作，返回更新结果
    return lccontrastMapper.update(null, updateWrapper) > 0;
  }
}
