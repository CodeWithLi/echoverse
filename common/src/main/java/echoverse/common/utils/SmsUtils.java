package echoverse.common.utils;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import echoverse.common.properties.UniSmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SmsUtils {

    @Autowired
    private UniSmsProperties uniSmsProperties;

    public UniResponse sendSms(String setPhone, String code){
        Uni.init(uniSmsProperties.getAccessKeyId());

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", code);
        templateData.put("ttl", uniSmsProperties.getTtl());

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(setPhone)
                .setSignature(uniSmsProperties.getSignature())
                .setTemplateId(uniSmsProperties.getTemplateId())
                .setTemplateData(templateData);

        UniResponse res=null;
        // 发送短信
        try {
            res = message.send();
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
        return res;
    }

    public void test(){
        log.info("smsUtils测试");
    }

    public String getSmsCode(String account,String purpose){
        return null;
    }
}