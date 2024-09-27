package echoverse.common.utils;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import echoverse.common.exception.BaseException;
import org.springframework.stereotype.Component;


import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class SmsCaffeineUtils {

    // 创建 Caffeine 缓存，设置过期时间为 60 秒
    private final Cache<String, String> cache = Caffeine.newBuilder()
        .expireAfterWrite(300, TimeUnit.SECONDS) // 设置60秒过期
        .build();

    public String getIfPresent(String purpose,String phone){
        return cache.getIfPresent(purpose+phone);
    }

    public void putSmsCache(String purpose, String phone,String randomCode){
        cache.put(purpose+phone,randomCode);
        // 检查缓存是否成功添加
        String ifPresent = this.getIfPresent(purpose, phone);
        System.out.println( "this.getIfPresent(purpose, phone)+值为"+ifPresent);
        String retrievedCode = cache.getIfPresent(purpose+phone);
        if (retrievedCode != null) {
            System.out.println("验证码已成功存入缓存: " + retrievedCode);
        } else {
            System.out.println("验证码存入缓存失败");
        }
    }

    public String getCache(String purpose,String phone){
        String ifPresent = getIfPresent(purpose, phone);
        String code = cache.get(purpose+phone, s -> {
            throw new BaseException("未发送验证码");
        });
        return code;
    }

    public void removeCacheByPhone(String purpose,String phone){
        cache.invalidate(purpose+phone);
    }
}
