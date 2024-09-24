package echoverse.user.service.impl;

import echoverse.model.dto.User.User;
import echoverse.model.po.UserPo;
import echoverse.user.mapper.UserMapper;
import echoverse.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByEmail(String email) {
        if (StringUtils.hasText(email)){
            UserPo po = userMapper.getUserByEmail(email);
            User user=new User();
            BeanUtils.copyProperties(po,user);
            return user;
        }
        throw new RuntimeException("错误");
    }

    //根据用户id查询用户权限
    @Override
    public List<String> getUserAuthorities(String userId) {
        if (!StringUtils.hasText(userId)){
            throw new RuntimeException("用户信息错误");
        }
//        return userMapper.getUserAuthorities(userId);
        return null;
    }
}
