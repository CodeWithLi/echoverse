package echoverse.common.security;

import com.alibaba.fastjson2.annotation.JSONField;
import echoverse.model.dto.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {
    private User user;

    //存储权限信息
    private List<String> permissions;

    public UserDetail(User user,List<String> permissions) {
        this.user = user;
        this.permissions=permissions;
    }

    public UserDetail(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    //存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //对应UserDetailsService方法loadUserByUsername(String username)中的username
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
