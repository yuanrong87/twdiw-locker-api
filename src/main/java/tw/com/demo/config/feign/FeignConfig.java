package tw.com.demo.config.feign;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
import tw.com.demo.component.AppConfig;

/**
 * Feign 配置
 */
@Configuration // 建議當需要配置多個 FeignConfig 時不要加 @Configuration，否則也些配置會吃到全域的設定
@AllArgsConstructor
public class FeignConfig {

    private final AppConfig appConfig;

    /**
     * Contract 用於自定義 FeignClient 和 RESTful API 之間的映射關係。
     * Feign 默認使用 Spring Cloud OpenFeign 套件提供的 SpringMvcContract 來執行這項任務，但是開發人員可以創建自己的 Contract 實現，以覆蓋默認的行為。
     *
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

    /**
     * Decoder 和 Encoder：用於定義自定義的解碼器和編碼器。
     * 可以使用 JacksonDecoder 和 JacksonEncoder 等內置的解碼器和編碼器。
     *
     * @param objectMapper
     * @return
     */
    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return new FeignJsonDecoder(objectMapper);
    }

    /**
     * Decoder 和 Encoder：用於定義自定義的解碼器和編碼器。
     * 可以使用 JacksonDecoder 和 JacksonEncoder 等內置的解碼器和編碼器。
     *
     * @param objectMapper
     * @return
     */
    @Bean
    public Encoder feignEncoder(ObjectMapper objectMapper) {
        return new FeignJsonEncoder(objectMapper);
    }

    /**
     * Retryer 用於定義當 FeignClient 請求失敗時的重試行為。
     * Feign 默認使用 Default 重試策略，但是開發人員可以創建自己的 Retryer 實現，以覆蓋默認的行為。
     *
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }

    /**
     * 設置 Feign 的日誌級別，可以設置為 NONE、BASIC、HEADERS 或 FULL。預設值為 NONE，即不啟用日誌。
     * <p>
     * NONE：不輸出日誌。
     * BASIC：只輸出請求方法和URL以及響應的狀態碼和响应时间。
     * HEADERS：輸出請求和響應的標頭。
     * FULL：輸出請求和響應的所有細節。
     *
     * @return
     */
    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    /**
     * RequestInterceptor 用於在發送請求之前修改 FeignClient 的請求頭、參數或者其他內容。
     * 例如，可以在 RequestInterceptor 中添加驗證憑證、添加自定義的 HTTP 標頭等。
     *
     * @return
     */
    @Bean
    public RequestInterceptor feignInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 根據 api 服務需求決定使用哪種驗證方式
                String name = template.feignTarget().name();
                // 驗證端服務
                if (StringUtils.startsWith(name, "verifier")) {
                    // Headers 加入 Access-Token
                    template.header("Access-Token", appConfig.getVerifierAccessToken());
                }
            }
        };
    }

}
