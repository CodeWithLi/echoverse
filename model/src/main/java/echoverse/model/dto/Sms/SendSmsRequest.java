package echoverse.model.dto.Sms;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsRequest {

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号码格式错误")
    private String phone;

    private String purpose;
}
