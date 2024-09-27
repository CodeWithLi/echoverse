package echoverse.user.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.common.utils.UuidUtils;
import echoverse.common.enumecho.LoginType;
import echoverse.common.exception.BaseException;
import echoverse.common.service.SmsServiceClient;
import echoverse.common.utils.AuthenticateGetUserUtils;
import echoverse.common.utils.JwtUtils;
import echoverse.common.utils.SmsCaffeineUtils;
import echoverse.common.utils.ThrowUtils;
import echoverse.model.dto.User.LoginRequest;
import echoverse.model.dto.User.RegisterRequest;
import echoverse.model.dto.User.User;
import echoverse.model.po.UserPo;
import echoverse.model.vo.User.LoginVo;
import echoverse.user.mapper.UserMapper;
import echoverse.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //注入缓存工具类
    @Resource
    private SmsCaffeineUtils smsCaffeineUtils;

    //注入jwt工具类
    @Resource
    private JwtUtils jwtUtils;

    //注入redisTemplate工具类
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SmsServiceClient smsServiceClient;

    //加密密码
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //用户登录
    @Override
    public LoginVo loginUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        if (StrUtil.equals(LoginType.PASSWORD.getType(),loginRequest.getLoginType(),true)){
            authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getPhone(),loginRequest.getPassword());
        }else if (StrUtil.equals(LoginType.CODE.getType(),loginRequest.getLoginType(),true)){
            authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getPhone()+"-"+loginRequest.getCode(),loginRequest.getCode());
        }else {
            throw new BaseException("错误登录");
        }
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new BaseException("密码错误");
        }

        //存入信息
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //获取User对象
        User user = AuthenticateGetUserUtils.getUser();
        String token=null;
        //判断Redis是否存在token，有则直接返回，没则生成
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey("userToken:"+user.getUserId()))){
            token =stringRedisTemplate.opsForValue().get("userToken:"+user.getUserId());
        }else {
            //封装Token
            token =jwtUtils.createUserIdJwt(user.getUserId(),user.getAuthorities());
            stringRedisTemplate.opsForValue().set("userToken:"+user.getUserId(),token,12, TimeUnit.HOURS);
        }
        return new LoginVo(token,user.getAuthorities());
    }

    //用户注册
    @Override
    public boolean registerUser(RegisterRequest request) {
        //获取验证码
        String sms = smsServiceClient.getSms(request.getPurpose(), request.getPhone());
        if (!StrUtil.equals(request.getCode(),sms,true)){
            throw new BaseException("验证码校验失败");
        }
        //判断用户是否存在
        int result= userMapper.isExitUser(request.getPhone());
        ThrowUtils.throwIf(result!=0,"用户已注册");
        //用户不存在，存入用户信息
        UserPo po = new UserPo();
        BeanUtils.copyProperties(request,po);
        insertUser(po);
        return true;
    }

    //添加用户
    private void insertUser(UserPo userPo){
        userPo.setUserId(IdUtil.simpleUUID());
        userPo.setPassword(bCryptPasswordEncoder.encode(userPo.getPassword()));
        userPo.setCreateTime(LocalDateTime.now());
        userPo.setStatus(0);
        userPo.setIsDelete(0);
        int result =  userMapper.insertUser(userPo);
        ThrowUtils.throwIf(result!=1,"添加用户失败");
        //添加角色
        insertRoleUser(userPo.getUserId());
    }

//    添加权限
    private void insertRoleUser(String userId){
        // TODO: 2024/9/26 后期将roleId封装成枚举类
        int roleId = 1;
        int result = userMapper.insertRoleUser(roleId,userId);
        ThrowUtils.throwIf(result!=1,"添加用户失败");
    }


    @Override
    public User getUserByPhone(String phone) {
        if (StringUtils.hasText(phone)){
            UserPo po = userMapper.getUserByPhone(phone);
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
