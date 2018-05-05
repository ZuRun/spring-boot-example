package me.zuhr.demo.wxapp.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.config.WxAppConfiguration;
import me.zuhr.demo.wxapp.config.WxService;
import me.zuhr.demo.wxapp.entity.WxAppUser;
import me.zuhr.demo.wxapp.mapper.WxAppUserInfoMapper;
import me.zuhr.demo.wxapp.utils.WxappUtils;
import me.zuhr.demo.wxapp.vo.SessionVo;
import me.zuhr.demo.wxapp.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zurun
 * @date 2018/3/19 12:28:32
 */
@Service
public class LoginService extends WxService {
    @Autowired
    WxAppConfiguration wxAppConfiguration;

    @Autowired
    WxappUtils wxappUtils;

    @Autowired
    WxAppUserInfoMapper<WxAppUser> userInfoMapper;

    public Result login(String jsCode) {
        String appId = wxAppConfiguration.getAppId();
        String appSecret = wxAppConfiguration.getAppSecret();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={js_code}&grant_type=authorization_code";
        SessionVo sessionVo = restTemplate.getForObject(url, SessionVo.class, appId, appSecret, jsCode);
        if (sessionVo.success()) {
            WxAppUser wxAppUser;
            List<WxAppUser> list = userInfoMapper.selectList(new EntityWrapper<WxAppUser>().eq("openid", sessionVo.getOpenid()));
            if (list.size() == 0) {
                wxAppUser = new WxAppUser();
                wxAppUser.setOpenid(sessionVo.getOpenid());
                wxAppUser.setUnionid(sessionVo.getOpenid());
                wxAppUser.setAvatarUrl("xxxurl");
                userInfoMapper.insert(wxAppUser);
            } else {
                wxAppUser = list.get(0);
                wxAppUser.setNickname("nnnnname");
                userInfoMapper.updateById(wxAppUser);
            }
            return Result.ok().addResult(wxappUtils.login(sessionVo));
        }
        return Result.fail(sessionVo.getErrcode(), sessionVo.getErrmsg());
    }


    public void updateUserInfo(String token,UserInfoVo userInfoVo){
        WxAppUser user=userInfoMapper.findByOpenId(userInfoVo.getOpenid());
        userInfoMapper.updateById(user);

    }
}
