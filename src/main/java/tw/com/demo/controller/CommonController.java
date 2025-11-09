package tw.com.demo.controller;

import java.util.List;

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
import tw.com.demo.dto.GetGroupInfoRequest;
import tw.com.demo.dto.GetGroupInfoResponse;
import tw.com.demo.dto.base.BaseResponse;
import tw.com.demo.service.CommonService;

/**
 * 共用相關 接口層
 */
@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
@Tag(name = "共用相關服務", description = "處理 共用相關 API 服務")
public class CommonController {

    private final CommonService commonService;

    /**
     * 取得系統參數
     * 
     * @param request
     * @return
     */
    @PostMapping("/group/info")
    @Operation(
            summary = "取得系統參數",
            description = "取得系統參數")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "取得系統參數 成功"),
            @ApiResponse(responseCode = "401", description = "沒有權限"),
            @ApiResponse(responseCode = "404", description = "找不到路徑")
    })
    public BaseResponse<List<GetGroupInfoResponse>> getGroupInfo(@Valid @RequestBody GetGroupInfoRequest request) {
        return BaseResponse.success(commonService.getGroupInfo(request));
    }

}
