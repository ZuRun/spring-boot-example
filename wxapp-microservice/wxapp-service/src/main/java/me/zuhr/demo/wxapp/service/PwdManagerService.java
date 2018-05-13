package me.zuhr.demo.wxapp.service;

import me.zuhr.demo.basis.utils.AesUtils;
import me.zuhr.demo.wxapp.base.AbstractWxAppService;
import me.zuhr.demo.wxapp.entity.PasswordInfo;
import me.zuhr.demo.wxapp.exception.WxAppException;
import me.zuhr.demo.wxapp.mapper.PwdInfoMapper;
import me.zuhr.demo.wxapp.utils.WxappUtils;
import me.zuhr.demo.wxapp.vo.PassWordInfoVo;
import me.zuhr.demo.wxapp.vo.SessionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author zurun
 * @date 2018/5/9 00:59:26
 */
@Service
public class PwdManagerService extends AbstractWxAppService {

    @Autowired
    WxappUtils wxappUtils;

    @Autowired
    PwdInfoMapper<PasswordInfo> pwdInfoMapper;

    /**
     * 新增并返回主键
     *
     * @param passWordInfoVo
     * @author ZuRun
     */
    public Integer add(PassWordInfoVo passWordInfoVo) {
        SessionVo sessionVo = getSessionVo();
        String userSecret = getSessionVo().getOpenid();


        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setName(passWordInfoVo.getName());
        passwordInfo.setAccount(passWordInfoVo.getAccount());
        passwordInfo.setOpenid(sessionVo.getOpenid());
        passwordInfo.setSalt(UUID.randomUUID().toString());
        passwordInfo.setCipherText(AesUtils.encrypt(passWordInfoVo.toJsonString(), userSecret));
        return pwdInfoMapper.insert(passwordInfo);
    }

    public List getSimpleList() {
        SessionVo sessionVo = getSessionVo();
        return pwdInfoMapper.getSimpleList(sessionVo.getOpenid());
    }


    public PassWordInfoVo getPassWordInfoVoById(Long id) {
        PasswordInfo passwordInfo = getPasswordInfoById(id);
        //TODO-zurun
        String userSecret = getSessionVo().getOpenid();
        PassWordInfoVo passWordInfoVo = passwordInfo.decryptionToVo(userSecret);
        return passWordInfoVo;
    }

    /**
     * 根据id获取原始记录,并检查是否合法(当前用户openid一样)
     *
     * @param id
     * @return
     */
    public PasswordInfo getPasswordInfoById(Long id) {
        SessionVo sessionVo = getSessionVo();
        PasswordInfo passwordInfo = pwdInfoMapper.selectById(id);
        if (passwordInfo == null) {
            throw new WxAppException("未找到记录");
        }
        if (!sessionVo.getOpenid().equals(passwordInfo.getOpenid())) {
            throw new WxAppException("未找到记录");
        }
        return passwordInfo;
    }
}
