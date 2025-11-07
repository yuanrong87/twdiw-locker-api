package tw.com.demo.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.exception.CustomException;
import tw.com.demo.outbound.VerifierAdapter;
import tw.com.demo.outbound.dto.ApiOidvpQrcodeResponse;
import tw.com.demo.outbound.dto.ApiOidvpResultRequest;
import tw.com.demo.outbound.dto.ApiOidvpResultResponse;
import tw.com.demo.type.ReturnCodeType;

/**
 * 驗證端相關 業務邏輯層
 */
@Service
@AllArgsConstructor
@Slf4j
public class VerifierService {

    private final VerifierAdapter verifierAdapter;

    /**
     * 產生授權 QR Code
     * 
     * @param ref
     * @param transactionId
     * @return
     */
    public ApiOidvpQrcodeResponse getVpQrcode(String ref, String transactionId) {
        ApiOidvpQrcodeResponse response = verifierAdapter.getVpQrcode(ref, transactionId);
        if (null == response) {
            throw new CustomException(ReturnCodeType.ERROR_GET_VP_QR_CODE);
        }

        return response;
    }

    /**
     * 查詢 VP 展示驗證結果
     * 
     * @param transactionId
     * @return
     */
    public ApiOidvpResultResponse getVpResult(String transactionId) {
        ApiOidvpResultRequest request = new ApiOidvpResultRequest();
        request.setTransactionId(transactionId);

        ApiOidvpResultResponse response = verifierAdapter.getVpResult(request);
        if (null == response) {
            throw new CustomException(ReturnCodeType.ERROR_GET_VP_RESULT);
        }

        return response;
    }

}
