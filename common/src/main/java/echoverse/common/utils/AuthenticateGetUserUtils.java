package echoverse.common.utils;

import echoverse.common.security.LoginByCodeUserDetail;
import echoverse.common.security.LoginByPassUserDetail;
import echoverse.model.dto.User.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticateGetUserUtils {

    public static User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       userDetails.getUsername();
       userDetails.getPassword();

        //根据username判断，验证码登录的话，username长度大于11，密码登录的话，username长度为11
        //密码
        if (userDetails.getUsername().length() == 11){
            LoginByPassUserDetail LoginByPassUserDetail= (LoginByPassUserDetail) userDetails;
            return LoginByPassUserDetail.getUser();
        } else if (userDetails.getUsername().length() > 11) {
            //验证码
            LoginByCodeUserDetail loginByCodeUserDetail = (LoginByCodeUserDetail) userDetails;
            return loginByCodeUserDetail.getUser();
        }
        return null;
    }

}
