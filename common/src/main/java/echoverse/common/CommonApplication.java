package echoverse.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableFeignClients("echoverse.common.service")
public class CommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class,args);
    }
}
