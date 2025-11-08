package tw.com.demo.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tw.com.demo.dto.base.BaseResponse;
import tw.com.demo.outbound.dto.ApiQrcodeDataRequest;
import tw.com.demo.outbound.dto.ApiQrcodeDataResponse;
import tw.com.demo.service.IssuerService;

/**
 * 測試相關 接口層
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "測試相關服務", description = "處理 測試相關 API 服務")
public class TestController {

    private final IssuerService issuerService;

    /**
     * (測試用)健康度檢查
     * 
     * @return
     */
    @GetMapping("/health")
    @Operation(
            summary = "(測試用)健康度檢查",
            description = "(測試用)健康度檢查")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "健康度檢查成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public Health health() {
        return Health.up().withDetail("version", "0.1.0").build();
    }

    /**
     * 取得 QR Code
     * 
     * @param request
     * @return
     */
    @PostMapping("/issuer/qrcode")
    @Operation(
            summary = "(測試用)取得 QR Code",
            description = "(測試用)取得 QR Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得 QR Code 成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<ApiQrcodeDataResponse> qrcodeData(@Valid @RequestBody ApiQrcodeDataRequest request) {
        return BaseResponse.success(issuerService.qrcodeData(request));
    }

}
