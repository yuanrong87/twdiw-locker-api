package tw.com.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Swagger3 配置
 * -> 訪問 URL: /swagger-ui/index.html
 */
@Configuration
@Profile("!prod")
public class SwaggerConfiguration {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧取物系統")
                        .description("智慧取物相關 API 服務")
                        .version("v01"));
    }

}
