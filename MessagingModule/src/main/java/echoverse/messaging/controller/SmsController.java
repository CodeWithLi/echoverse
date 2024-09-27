package echoverse.messaging.controller;

import echoverse.common.response.Result;
import echoverse.common.utils.SmsCaffeineUtils;
import echoverse.common.utils.ThrowUtils;
import echoverse.messaging.service.SmsService;
import echoverse.model.dto.Sms.SendSmsRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短信接口
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    @Resource
    private SmsCaffeineUtils smsCaffeineUtils;

    @PostMapping("/sendSms")
    public Result sendSms(@RequestBody @Valid SendSmsRequest request) {
        String phone = request.getPhone();
        String purpose = request.getPurpose();
        String smsCode = smsService.sendSmsCode(purpose,phone);
        ThrowUtils.throwIf(smsCode == null, "验证码发送失败");
        return Result.success(smsCode);
    }

    @GetMapping("/getSms")
    public String getSms(@RequestParam(value = "purpose") String purpose,
                         @RequestParam(value = "phone") String phone){
        return smsService.getSms(purpose, phone);
    }
}


