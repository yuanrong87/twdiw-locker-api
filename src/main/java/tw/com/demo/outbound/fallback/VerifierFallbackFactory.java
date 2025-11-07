package tw.com.demo.outbound.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.exception.CustomException;
import tw.com.demo.outbound.VerifierAdapter;
import tw.com.demo.outbound.dto.ApiOidvpQrcodeResponse;
import tw.com.demo.outbound.dto.ApiOidvpResultRequest;
import tw.com.demo.outbound.dto.ApiOidvpResultResponse;
import tw.com.demo.type.ReturnCodeType;

/**
 * 驗證端服務（錯誤處理）
 */
@Slf4j
@Component
public class VerifierFallbackFactory implements FallbackFactory<VerifierAdapter> {

    @Override
    public VerifierAdapter create(Throwable cause) {
        return new VerifierAdapter() {
            @Override
            public ApiOidvpQrcodeResponse getVpQrcode(String ref, String transactionId) {
                log.info("呼叫 getVpQrcode 錯誤，原因 : {}", cause.getMessage());
                if (cause instanceof FeignException feignException) {
                    // 讓 ExceptionHandler 處理
                    throw feignException;
                } else {
                    throw new CustomException(ReturnCodeType.ERROR_CALL_API.getCode(), cause.getMessage());
                }
            }

            @Override
            public ApiOidvpResultResponse getVpResult(ApiOidvpResultRequest request) {
                log.info("呼叫 getVpResult 錯誤，原因 : {}", cause.getMessage());
                if (cause instanceof FeignException feignException) {
                    // 讓 ExceptionHandler 處理
                    throw feignException;
                } else {
                    throw new CustomException(ReturnCodeType.ERROR_CALL_API.getCode(), cause.getMessage());
                }
            }
        };
    }

}
