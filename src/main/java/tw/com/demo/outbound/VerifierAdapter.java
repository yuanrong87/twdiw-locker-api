package tw.com.demo.outbound;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.demo.config.feign.FeignConfig;
import tw.com.demo.outbound.dto.ApiOidvpQrcodeResponse;
import tw.com.demo.outbound.dto.ApiOidvpResultRequest;
import tw.com.demo.outbound.dto.ApiOidvpResultResponse;
import tw.com.demo.outbound.fallback.VerifierFallbackFactory;

/**
 * 呼叫外部服務類(驗證端服務)
 */
@Component
@FeignClient(name = "verifier", url = "${verifier.server.url.base}", configuration = FeignConfig.class, fallbackFactory = VerifierFallbackFactory.class)
public interface VerifierAdapter {

    /**
     * 產生授權 QR Code
     *
     * @param ref
     * @param transactionId
     * @return
     */
    @GetMapping(value = "/api/oidvp/qrcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiOidvpQrcodeResponse getVpQrcode(@RequestParam("ref") String ref,
            @RequestParam("transactionId") String transactionId);

    /**
     * 查詢 VP 展示驗證結果
     *
     * @param transactionId
     * @return
     */
    @PostMapping(value = "/api/oidvp/result", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiOidvpResultResponse getVpResult(ApiOidvpResultRequest request);

}
