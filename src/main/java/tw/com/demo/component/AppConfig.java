package tw.com.demo.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * 讀取 yml 配置
 */
@Getter
@Component
public class AppConfig {

    @Value("${cors.allowedOrigins}")
    private String corsAllowedOrigins;

}
