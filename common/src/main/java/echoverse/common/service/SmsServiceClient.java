package echoverse.common.service;

import echoverse.model.dto.Sms.SendSmsRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "messaging-module")
@Component
public interface SmsServiceClient {

    @GetMapping("/sms/sendSms")
    String sendSms(@RequestBody @Valid SendSmsRequest request);

    @GetMapping("/sms/getSms")
    String getSms(@RequestParam(value = "purpose") String purpose,
                         @RequestParam(value = "phone") String phone);
}
