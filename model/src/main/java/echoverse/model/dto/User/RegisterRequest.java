package echoverse.model.dto.User;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号码格式错误")
    private String phone;
    @Pattern(regexp = "^[A-Za-z\\d]{7,20}$",message = "密码格式错误")
    private String password;
    @Pattern(regexp = "^[0-9]\\d{6}$",message = "验证码格式错误")
    private String code;
    private String purpose;
}
