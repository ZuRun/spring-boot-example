package me.zuhr.demo.wxapp.mapper;

import me.zuhr.demo.mybatisplus.base.SuperMapper;
import me.zuhr.demo.wxapp.entity.WxAppUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zurun
 * @date 2018/5/1 15:55:58
 */
@Mapper
public interface WxAppUserInfoMapper<T extends WxAppUser> extends SuperMapper<WxAppUser> {

    /**
     * 根据openid获取微信用户信息
     *
     * @param openId
     * @return
     */
    T findByOpenId(String openId);


}
