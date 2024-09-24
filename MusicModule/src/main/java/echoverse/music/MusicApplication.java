package echoverse.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("echoverse.music.mapper")
public class MusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class,args);
    }
}
