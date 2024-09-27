package echoverse.user.service;

import echoverse.model.dto.User.LoginRequest;
import echoverse.model.dto.User.RegisterRequest;
import echoverse.model.dto.User.User;
import echoverse.model.vo.User.LoginVo;

import java.util.List;

public interface UserService {

    //用户登录
    LoginVo loginUser(LoginRequest loginRequest);

    //用户注册
    boolean registerUser(RegisterRequest request);

    //根据邮箱查询用户
    User getUserByPhone(String phone);

    //根据用户id查询用户权限
    List<String> getUserAuthorities(String userId);

}
