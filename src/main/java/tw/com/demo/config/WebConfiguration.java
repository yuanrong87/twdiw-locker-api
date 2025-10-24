package tw.com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;
import tw.com.demo.component.AppConfig;

@Configuration
@AllArgsConstructor
public class WebConfiguration {

    private final AppConfig appConfig;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(appConfig.getCorsAllowedOrigins())
                        .allowedMethods("GET", "POST")
                        .allowCredentials(true);
            }
        };
    }

}
