package echoverse.user.controller;

import echoverse.model.dto.User.User;
import echoverse.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "测试成功";
    }

    //根据邮箱查询用户
    @GetMapping("/getUserByEmail")
    public User getUserByEmail(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        return user;
    }

    //根据用户id查询用户权限
    @GetMapping("/user/authorities")
    List<String> getUserAuthorities(String userId){
        List<String> list= userService.getUserAuthorities(userId);
        return list;
    }
}
