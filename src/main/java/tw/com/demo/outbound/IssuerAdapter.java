package tw.com.demo.outbound;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import tw.com.demo.config.feign.FeignConfig;
import tw.com.demo.outbound.dto.ApiQrcodeDataRequest;
import tw.com.demo.outbound.dto.ApiQrcodeDataResponse;
import tw.com.demo.outbound.fallback.IssuerFallbackFactory;

/**
 * 呼叫外部服務類(發行端服務)
 */
@Component
@FeignClient(name = "issuer", url = "${issuer.server.url.base}", configuration = FeignConfig.class, fallbackFactory = IssuerFallbackFactory.class)
public interface IssuerAdapter {

    /**
     * 產生 QR Code
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/api/qrcode/data", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiQrcodeDataResponse qrcodeData(ApiQrcodeDataRequest request);

}
