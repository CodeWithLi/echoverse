package echoverse.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户注册时间
     */
    private LocalDateTime createTime;

    /**
     * 用户账号状态，0为未封禁，1以封禁
     */
    private Integer status;

    /**
     * 用户注销状态，0为未注销，1以注销
     */
    private Integer isDelete;


}
