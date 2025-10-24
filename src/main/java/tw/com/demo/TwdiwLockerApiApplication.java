package tw.com.demo;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import jakarta.annotation.PostConstruct;

@EnableFeignClients(basePackages = "tw.com.demo.outbound")
@SpringBootApplication
public class TwdiwLockerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwdiwLockerApiApplication.class, args);
    }

    /**
     * 時區處理
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
    }

}
