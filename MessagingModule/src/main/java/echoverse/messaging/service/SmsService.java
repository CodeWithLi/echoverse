package echoverse.messaging.service;

public interface SmsService {

    //发送短信
    String sendSmsCode(String purpose,String phone);

    //获取短信
    String getSms(String purpose,String phone);
}
