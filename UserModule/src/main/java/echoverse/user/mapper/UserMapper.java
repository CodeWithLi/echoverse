package echoverse.user.mapper;

import echoverse.model.po.UserPo;

public interface UserMapper {


//    //根据用户id查询用户权限
//    List<String> getUserAuthorities(String userId);
//
//    String get();

    //根据邮箱查询用户
    UserPo getUserByEmail(String email);

}
