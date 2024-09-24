package echoverse.user.service;

import echoverse.model.dto.User.User;

import java.util.List;

public interface UserService {

    //根据邮箱查询用户
    User getUserByEmail(String email);

    //根据用户id查询用户权限
    List<String> getUserAuthorities(String userId);
}
