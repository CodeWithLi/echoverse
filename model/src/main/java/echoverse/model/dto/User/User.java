package echoverse.model.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String userId;
    private String phone;
    private String email;
    private String password;
    private String code;
    List<String> authorities;
}
