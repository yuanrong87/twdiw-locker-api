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
    
    @Value("${mqtt.broker}")
    private String broker;
    
    @Value("${mqtt.username}")
    private String username;
    
    @Value("${mqtt.password}")
    private String password;
    
    @Value("${mqtt.topic-control}")
    private String topicControl;
    
    @Value("${mqtt.topic-status}")
    private String topicStatus;

    @Value("${issuer.data.accessToken}")
    private String issuerAccessToken;
    
    @Value("${verifier.data.accessToken}")
    private String verifierAccessToken;

    @Value("${verifier.data.ref}")
    private String verifierDataRef;

}
