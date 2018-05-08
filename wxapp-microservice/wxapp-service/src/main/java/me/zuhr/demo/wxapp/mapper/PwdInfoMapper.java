package me.zuhr.demo.wxapp.mapper;

import me.zuhr.demo.mybatisplus.base.SuperMapper;
import me.zuhr.demo.wxapp.entity.PasswordInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zurun
 * @date 2018/5/1 15:55:58
 */
@Mapper
public interface PwdInfoMapper<T extends PasswordInfo> extends SuperMapper<PasswordInfo> {

}
