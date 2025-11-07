package tw.com.demo.outbound.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.exception.CustomException;
import tw.com.demo.outbound.IssuerAdapter;
import tw.com.demo.outbound.dto.ApiQrcodeDataRequest;
import tw.com.demo.outbound.dto.ApiQrcodeDataResponse;
import tw.com.demo.type.ReturnCodeType;

/**
 * 發行端服務（錯誤處理）
 */
@Slf4j
@Component
public class IssuerFallbackFactory implements FallbackFactory<IssuerAdapter> {

    @Override
    public IssuerAdapter create(Throwable cause) {
        return new IssuerAdapter() {
            @Override
            public ApiQrcodeDataResponse qrcodeData(ApiQrcodeDataRequest request) {
                log.info("呼叫 qrcodeData 錯誤，原因 : {}", cause.getMessage());
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
