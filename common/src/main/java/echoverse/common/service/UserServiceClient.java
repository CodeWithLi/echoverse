package echoverse.common.service;

import echoverse.model.dto.User.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user-module")
@Component
public interface UserServiceClient {

    @GetMapping("/user/test")
    String test();

    //根据邮箱登录
    @GetMapping("/user/getUserByPhone")
    User getUserByPhone(@RequestParam("phone") String phone);

    //根据用户id查询用户权限
    @GetMapping("/user/authorities")
    List<String> getUserAuthorities(@RequestParam("userId") String userId);
}
