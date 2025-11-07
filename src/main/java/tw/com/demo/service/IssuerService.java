package tw.com.demo.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.exception.CustomException;
import tw.com.demo.outbound.IssuerAdapter;
import tw.com.demo.outbound.dto.ApiQrcodeDataRequest;
import tw.com.demo.outbound.dto.ApiQrcodeDataResponse;
import tw.com.demo.type.ReturnCodeType;

/**
 * 發行端相關 業務邏輯層
 */
@Service
@AllArgsConstructor
@Slf4j
public class IssuerService {

    private final IssuerAdapter issuerAdapter;

    /**
     * 產生 QR Code
     * 
     * @param request
     */
    public ApiQrcodeDataResponse qrcodeData(ApiQrcodeDataRequest request) {
        ApiQrcodeDataResponse response = issuerAdapter.qrcodeData(request);
        if (null == response) {
//            throw new CustomException(ReturnCodeType.ERROR_GET_VC_QR_CODE);
        }

        return response;
    }

}
