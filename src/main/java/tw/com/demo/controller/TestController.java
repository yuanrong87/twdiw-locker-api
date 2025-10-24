package tw.com.demo.controller;

import java.util.List;

import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.com.demo.dto.base.BaseResponse;
import tw.com.demo.entity.LockersEntity;
import tw.com.demo.entity.PackagesEntity;
import tw.com.demo.repository.LockersRepository;
import tw.com.demo.repository.PackagesRepository;

/**
 * 測試相關 接口層
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "測試相關服務", description = "處理 測試相關 API 服務")
public class TestController {

    private final PackagesRepository packagesRepository;

    private final LockersRepository lockersRepository;

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
     * 取得所有包裹
     * 
     * @return
     */
    @PostMapping("/all/packages")
    @Operation(
            summary = "(測試用)取得所有包裹",
            description = "(測試用)取得所有包裹")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "包裹查詢成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<List<PackagesEntity>> getAllPackages() {
        return BaseResponse.success(packagesRepository.findAll());
    }

    /**
     * 取得所有櫃子
     * 
     * @return
     */
    @PostMapping("/all/lockers")
    @Operation(
            summary = "(測試用)取得所有櫃子",
            description = "(測試用)取得所有櫃子")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "包裹櫃子成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<List<LockersEntity>> getAllLockers() {
        return BaseResponse.success(lockersRepository.findAll());
    }

}
