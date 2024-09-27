package echoverse;

import echoverse.common.utils.SmsCaffeineUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests {
    @Resource
    private SmsCaffeineUtils smsCaffeineUtils;

    @Test
    public void test1(){
        smsCaffeineUtils.putSmsCache("1","1","1");
    }

    @Test
    public void  rtest2(){
        System.out.println(smsCaffeineUtils.getCache("1", "1"));
    }

    @Test
    public void tes1(){
        System.out.println(smsCaffeineUtils.getIfPresent("1", "1"));

    }
}
