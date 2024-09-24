package echoverse.common.security;//package echoverse.common.service.security;

import echoverse.common.exception.BaseException;
import echoverse.common.service.UserServiceClient;
import echoverse.model.dto.User.User;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadUserByUsernameServiceImpl implements UserDetailsService {

    @Resource
    private UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> permissions = null;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


        if (username.matches(emailRegex)){
            //邮箱登录
            User user = userServiceClient.getUserByEmail(username);

            isNullUser(user);

            //获取用户权限
            permissions = getAuthorities(user.getUserId());

            user.setAuthorities(permissions);

            return new UserDetail(user, permissions);
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
