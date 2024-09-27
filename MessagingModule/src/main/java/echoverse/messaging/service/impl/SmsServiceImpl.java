package echoverse.messaging.service.impl;

import cn.hutool.core.util.StrUtil;
import echoverse.common.exception.BaseException;
import echoverse.common.utils.SmsCaffeineUtils;
import echoverse.common.utils.SmsUtils;
import echoverse.messaging.service.SmsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    //注入SmsUtils工具类
    @Autowired
    private SmsUtils smsUtils;

    //注入Caffeine工具类
    @Autowired
    private SmsCaffeineUtils smsCaffeineUtils;


    @PostConstruct
    public void init() {
        System.out.println("SmsUtils class: " + smsUtils.getClass().getName());
    }

    //发送短信
    @Override
    public String sendSmsCode(String purpose,String phone) {
        //判断缓存中是否存在验证码，存在返回异常
        if (smsCaffeineUtils.getIfPresent(purpose,phone) != null) {
            throw new BaseException("验证码未过期,请勿重复发送");
        }
        //生成随机的四位数
        String randomCode = String.valueOf(new Random().nextInt(100000, 999999));
        smsUtils.test();
    //调SmsUtils工具类发送短信
//        UniResponse uniResponse = smsUtils.sendSms(phone, randomCode);
//        if (uniResponse.status!=200){
//            throw new BaseException("验证码发送失败");
//        }
        //将短信存储到Caffeine当中
        smsCaffeineUtils.putSmsCache(purpose,phone,randomCode);
        smsCaffeineUtils.getIfPresent(purpose,phone);
        //返回验证码
        return randomCode;
    }

    //获取或者中的验证码
    //校验验证码
    public String getSms(String purpose,String phone){
        try {
            //获取缓存中的验证码
            String codeByCache = smsCaffeineUtils.getIfPresent(purpose,phone);
            Thread.currentThread ().sleep (1000);
            //删除缓存
            smsCaffeineUtils.removeCacheByPhone(purpose,phone);
            return codeByCache;
        }catch (Exception e){
            log.info("短信发送失败");
            throw new BaseException(e.getMessage());
        }
    }
}
