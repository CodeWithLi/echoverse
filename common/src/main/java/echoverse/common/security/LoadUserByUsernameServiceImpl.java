package echoverse.common.security;//package echoverse.common.service.security;

import cn.hutool.core.util.StrUtil;
import echoverse.common.enumecho.SmsPrefix;
import echoverse.common.exception.BaseException;
import echoverse.common.service.SmsServiceClient;
import echoverse.common.service.UserServiceClient;
import echoverse.common.utils.SmsCaffeineUtils;
import echoverse.model.dto.User.User;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadUserByUsernameServiceImpl implements UserDetailsService {

    @Resource
    private UserServiceClient userServiceClient;

    @Resource
    private SmsServiceClient smsServiceClient;

//    @Resource
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> permissions = null;

        String phone = "^1[3-9]\\d{9}$";

        if (username.matches(phone)){
            //邮箱登录
            User user = userServiceClient.getUserByPhone(username);

            isNullUser(user);

            //获取用户权限
            permissions = getAuthorities(user.getUserId());

            user.setAuthorities(permissions);

            return new LoginByPassUserDetail(user, permissions);
        }else if (username.matches("^(1[3-9]\\d{9}-(\\d{6}))$")){
            String[] split = username.split("-");
            String splitPhone = split[0];
            String code = split[1];
            String sms = smsServiceClient.getSms(SmsPrefix.LOGIN.getPrefix(), splitPhone);
            if (!StrUtil.equals(code,sms,true)){
                throw new BaseException("验证码错误");
            }
            User userByPhone = userServiceClient.getUserByPhone(splitPhone);
            isNullUser(userByPhone);
            BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();//加密code
            String encode = bCryptPasswordEncoder.encode(sms);
            userByPhone.setCode(encode);

            //获取用户权限
            permissions = getAuthorities(userByPhone.getUserId());

            userByPhone.setAuthorities(permissions);

            return new LoginByCodeUserDetail(userByPhone, permissions);
        }

        throw new BaseException("登录错误");
    }


    public static void isNullUser(User user){
        if (user==null){
            throw new BaseException("用户不存在");
        }else if (StringUtils.isBlank(user.getUserId())){
            throw new BaseException("用户信息错误");
        }
    }

    public List<String> getAuthorities(String userId){
        return userServiceClient.getUserAuthorities(userId);
    }
}
