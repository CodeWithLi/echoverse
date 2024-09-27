package echoverse.user.controller;

import echoverse.common.response.Result;
import echoverse.common.utils.SmsCaffeineUtils;
import echoverse.common.utils.SmsUtils;
import echoverse.common.utils.ThrowUtils;
import echoverse.model.dto.User.LoginRequest;
import echoverse.model.dto.User.RegisterRequest;
import echoverse.model.dto.User.User;
import echoverse.model.vo.User.LoginVo;
import echoverse.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginRequest loginRequest){
        LoginVo loginVo = userService.loginUser(loginRequest);
        return Result.success(loginVo);

    }

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest request) {
        boolean result = userService.registerUser(request);
        ThrowUtils.throwIf(!result, "注册失败");
        return Result.success("注册成功");
    }

    //根据手机号查询用户
    @GetMapping("/getUserByPhone")
    public User getUserByPhone(@RequestParam String phone){
        User user = userService.getUserByPhone(phone);
        return user;
    }

    //根据用户id查询用户权限
    @GetMapping("/authorities")
    List<String> getUserAuthorities(String userId){
        List<String> list= userService.getUserAuthorities(userId);
        return list;
    }

}
