package tw.com.demo.controller;

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
import tw.com.demo.dto.GetLockersInfoRequest;
import tw.com.demo.dto.GetLockersInfoResponse;
import tw.com.demo.dto.GetLockersQrcodeInfoRequest;
import tw.com.demo.dto.GetLockersQrcodeInfoResponse;
import tw.com.demo.dto.GetLockersQrcodeResponse;
import tw.com.demo.dto.HandleLockersOperationRequest;
import tw.com.demo.dto.HandleLockersPickupRequest;
import tw.com.demo.dto.HandleLockersPickupResponse;
import tw.com.demo.dto.base.BaseResponse;
import tw.com.demo.service.LockersService;

/**
 * 櫃子相關 接口層
 */
@RestController
@RequestMapping("/api/lockers")
@RequiredArgsConstructor
@Tag(name = "櫃子相關服務", description = "處理 櫃子相關 API 服務")
public class LockersController {

    private final LockersService lockersService;

    /**
     * 取得櫃子狀態
     * 
     * @param request
     * @return
     */
    @PostMapping("/info")
    @Operation(
            summary = "取得櫃子狀態",
            description = "取得櫃子狀態")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得櫃子狀態成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<GetLockersInfoResponse> getLockersInfo(@Valid @RequestBody GetLockersInfoRequest request) {
        return BaseResponse.success(lockersService.getLockersInfo(request));
    }

    /**
     * 寄物/配送
     * 
     * @param request
     * @return
     */
    @PostMapping("/operation")
    @Operation(
            summary = "寄物/配送",
            description = "寄物/配送")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "寄物/配送成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<GetLockersInfoResponse> handleLockersOperation(@Valid @RequestBody HandleLockersOperationRequest request) {
        lockersService.handleLockersOperation(request);
        return BaseResponse.success();
    }

    /**
     * 取得智慧取物 QR Code
     * 
     * @return
     */
    @PostMapping("/qrcode")
    @Operation(
            summary = "取得智慧取物 QR Code",
            description = "取得智慧取物 QR Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得智慧取物 QR Code 成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<GetLockersQrcodeResponse> getLockersQrcode() {
        return BaseResponse.success(lockersService.getLockersQrcode());
    }

    /**
     * 取得驗證資訊
     * 
     * @param request
     * @return
     */
    @PostMapping("/qrcode/info")
    @Operation(
            summary = "取得驗證資訊",
            description = "取得驗證資訊")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得驗證資訊 成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<GetLockersQrcodeInfoResponse> getLockersQrcodeInfo(@Valid @RequestBody GetLockersQrcodeInfoRequest request) {
        return BaseResponse.success(lockersService.getLockersQrcodeInfo(request));
    }

    /**
     * 取物
     * 
     * @param request
     * @return
     */
    @PostMapping("/pickup")
    @Operation(
            summary = "取物",
            description = "取物")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取物 成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<HandleLockersPickupResponse> handleLockersPickup(@Valid @RequestBody HandleLockersPickupRequest request) {
        return BaseResponse.success(lockersService.handleLockersPickup(request));
    }

}
