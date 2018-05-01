package me.zuhr.demo.wxapp.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.wxapp.config.WxAppConfiguration;
import me.zuhr.demo.wxapp.config.WxService;
import me.zuhr.demo.wxapp.entity.WxAppUser;
import me.zuhr.demo.wxapp.mapper.WxAppUserMapper;
import me.zuhr.demo.wxapp.utils.WxappUtils;
import me.zuhr.demo.wxapp.vo.SessionVo;
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
    WxAppUserMapper mapper;

    public Result login(String jsCode) {
        String appId = wxAppConfiguration.getAppId();
        String appSecret = wxAppConfiguration.getAppSecret();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={js_code}&grant_type=authorization_code";
        SessionVo sessionVo=  restTemplate.getForObject(url, SessionVo.class, appId, appSecret, jsCode);
        if(sessionVo.success()){
            WxAppUser wxAppUser;
            List<WxAppUser> list=mapper.selectList(new EntityWrapper().eq("openid", sessionVo.getOpenid()));
            if(list.size()==0){
                wxAppUser=new WxAppUser();
                wxAppUser.setOpenid(sessionVo.getOpenid());
                wxAppUser.setUnionid(sessionVo.getOpenid());
                mapper.insert(wxAppUser);
            }
            return Result.ok().addResult(wxappUtils.login(sessionVo));
        }
        return Result.fail(sessionVo.getErrcode(),sessionVo.getErrmsg());
    }

//    public UserInfo getUserInfo(){
//        UserInfo userInfo;
//        userInfo
//    }
}
