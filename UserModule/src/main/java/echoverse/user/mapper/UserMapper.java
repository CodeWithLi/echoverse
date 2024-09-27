package echoverse.user.mapper;

import echoverse.model.po.UserPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserMapper {


//    //根据用户id查询用户权限
//    List<String> getUserAuthorities(String userId);
//
//    String get();

    //根据邮箱查询用户
    UserPo getUserByPhone(String phone);

    //判断用户是否存在
    int isExitUser(String phone);

    //添加用户
    int insertUser(UserPo userPo);

    //添加用户到用户权限表
    int insertRoleUser(@Param("roleId") int roleId, @Param("userId") String userId);
}
